package Day7

import java.io.File

val cardValue = mapOf(
    '2' to 2,
    '3' to 3,
    '4' to 4,
    '5' to 5,
    '6' to 6,
    '7' to 7,
    '8' to 8,
    '9' to 9,
    'T' to 10,
    'J' to 11,
    'Q' to 12,
    'K' to 13,
    'A' to 14,
)
val cardValue2 = mapOf(
    'J' to 1,
    '2' to 2,
    '3' to 3,
    '4' to 4,
    '5' to 5,
    '6' to 6,
    '7' to 7,
    '8' to 8,
    '9' to 9,
    'T' to 10,
    'Q' to 11,
    'K' to 12,
    'A' to 13,
)

fun main() {
    part2()
}

fun part1() {
    val hands: MutableList<Pair<String, Int>> = mutableListOf()
    File("2023/src/Day7/input.txt").forEachLine {
        val parts = it.split(" ")
        hands.add(Pair(parts[0], parts[1].toInt()))
    }

    var sorted = hands.sortedWith { hand1, hand2 ->
        val h1map = mutableMapOf<Char, Int>()
        val h2map = mutableMapOf<Char, Int>()

        hand1.first.chars().forEach { c ->
            h1map.putIfAbsent(c.toChar(), 0)
            h1map[c.toChar()] = h1map[c.toChar()]!! + 1
        }
        hand2.first.chars().forEach { c ->
            h2map.putIfAbsent(c.toChar(), 0)
            h2map[c.toChar()] = h2map[c.toChar()]!! + 1
        }

        val t1 = getType(h1map)
        val t2 = getType(h2map)

        if (t1 > t2) {
            return@sortedWith 1
        }
        if (t2 == t1) {
            return@sortedWith getByHigherSecond(hand1.first, hand2.first, cardValue)
        } else {
            return@sortedWith -1
        }

    }
    println(sorted)
    var sum = 0
    var idx = 1
    for (i in sorted.size - 1 downTo 0) {
        val v = sorted[i].second * (idx)
        println("${sorted[i]}: * ${idx} $v")
        idx++
        sum += v
    }
    println("sum is $sum")
}

fun part2() {
    val hands: MutableList<Pair<String, Int>> = mutableListOf()
    File("2023/src/Day7/input.txt").forEachLine {
        val parts = it.split(" ")
        hands.add(Pair(parts[0], parts[1].toInt()))
    }

    var sorted = hands.sortedWith { hand1, hand2 ->
        val h1map = mutableMapOf<Char, Int>()
        val h2map = mutableMapOf<Char, Int>()

        hand1.first.chars().forEach { c ->
            h1map.putIfAbsent(c.toChar(), 0)
            h1map[c.toChar()] = h1map[c.toChar()]!! + 1
        }
        hand2.first.chars().forEach { c ->
            h2map.putIfAbsent(c.toChar(), 0)
            h2map[c.toChar()] = h2map[c.toChar()]!! + 1
        }

        val t1 = getJokerizedType(h1map)
        val t2 = getJokerizedType(h2map)
        println("$hand1 $t1")
        println("$hand2 $t2")

        if (t1 > t2) {
            return@sortedWith 1
        }
        if (t2 == t1) {
            return@sortedWith getByHigherSecond(hand1.first, hand2.first, cardValue2)
        } else {
            return@sortedWith -1
        }

    }
    println(sorted)
    var sum = 0
    var idx = 1
    for (i in sorted.size - 1 downTo 0) {
        val v = sorted[i].second * (idx)
        println("${sorted[i]}: * ${idx} $v")
        idx++
        sum += v
    }
    println("sum is $sum")
}

fun getType(map: Map<Char, Int>): Int {
    if (map.size == 1) return 1
    if (map.size == 2) {
        return if (map.any { e -> e.value == 4 }) 2
        else 3
    }
    if (map.size == 3) {
        if (map.any { e -> e.value == 3 }) return 4
        if (map.any { e -> e.value == 1 }) return 5
    }
    return if (map.size == 4) 6
    else 7
}

fun getJokerizedType(map: Map<Char, Int>): Int {

    val jokerCount = map.getOrDefault('J', 0)

    if (map.size == 1) return 1 // 5 of kind -> no impact
    if (map.size == 2) { // FH || 4 of Kind -> impact on 4 o.k.
        if (map.any { e -> e.value == 4 }) {
            if (jokerCount == 1) return 1 // if there is one joker, the 4 ok will be 5 ok
            if (jokerCount == 4) return 1 //
            return 2 // no upgrade -> normal 4ok
        } else if (jokerCount == 2 || jokerCount == 3) return 1 //FH with either 2 or 3 jokers will be 5ok
        else return 3 // FH
    }
    if (map.size == 3) {// ABBJJ ABJJJ AABJJ AAABJ JAABB

        if (map.any { e -> e.value == 3 }) { // AAABJ ABJJJ
            if (jokerCount == 1 || jokerCount == 3) return 2 // upgrade from 3ok to 4ok
            return 4 // 3ok
        }

        if (map.any { e -> e.value == 2 }) { //AABJJ AABBJ JJAAB AABBC
            if (jokerCount == 2) return 2 // two joker -> 4ok
            if (jokerCount == 1) return 3 // one joker -> FH
            else return 5
        }
    }

    if (map.size == 4) { // ABCCJ ABCJJ
        if (jokerCount == 1 || jokerCount == 2) return 4 // one/2 joker -> 3ok
        else return 6 // 0 joker -> pair
    }
    if (map.size == 5) { // ABCDE ABCDJ
        if (jokerCount == 1) return 6 // one joker -> one pair
        else return 7 // no joker -> high card
    } else return 0
}

fun getByHigherSecond(hand1: String, hand2: String, cardValue: Map<Char, Int>): Int {
    println("high $hand1 $hand2")
    for (i in 0..4) {
        if (cardValue[hand1[i]]!! > cardValue[hand2[i]]!!) {
            return -1
        }
        if (cardValue[hand1[i]]!! < cardValue[hand2[i]]!!) {
            return 1
        }
    }
    println("wtf? h1 $hand1 $hand2")
    return 0
}