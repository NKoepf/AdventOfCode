package Day15

import java.io.File

fun main() {
    val file = File("2023/src/Day15/input.txt")
    Util.printPart1()
    part1(file)
    Util.printPart2()
    part2(file)
}

fun part1(file: File) {
    val content = file.readText().split(",")
    println(content)
    var sum = 0
    content.forEach { sum += hashString(it) }
    println("sum of all hashed values is $sum")
}

fun part2(file: File) {
    val content = file.readText().split(",")
    println(content)
    val boxes: LinkedHashMap<Int, MutableList<String>> = LinkedHashMap()
    var sum = 0

    content.forEach { part ->
        val sanitizedString = part.replace("=", " ").replace("-", " ")
        val hashIndex = hashString(sanitizedString.substringBefore(" "))
        boxes.putIfAbsent(hashIndex, mutableListOf())

        if (part.contains("=")) {
            var replaced = false
            boxes[hashIndex]!!.forEachIndexed loop@{ i, it ->
                if (it.startsWith(sanitizedString.substringBefore(" "))) {
                    boxes[hashIndex]!!.set(i, part.replace("[=+-]".toRegex(), " "))
                    replaced = true
                    return@loop
                }
            }
            if (!replaced) boxes[hashIndex]!!.add(part.replace("[=+-]".toRegex(), " "))
        } else {
            boxes[hashIndex]!!.remove(boxes[hashIndex]!!.find { it.startsWith(sanitizedString.substringBefore(" ")) })
        }

    }

    boxes.forEach { box ->
        val base = 1 + box.key
        box.value.forEachIndexed { idx, v ->
            sum += base * (idx + 1) * v.substringAfter(" ").toInt()
        }
    }

    println(boxes.toString())
    println("sum is $sum")
}

fun hashString(input: String): Int {
    var v = 0
    input.forEach { v = ((v + it.code) * 17) % 256 }
    return v
}