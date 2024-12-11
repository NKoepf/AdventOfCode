package aoc24.day11

import java.io.File
import kotlin.system.measureTimeMillis

fun main() {
    val input = File("2024/src/aoc24/day11/input.txt")
    val numbs = input.readLines().first().split(" ").map { it.toInt() }

    var resPart1 = 0L
    var resPart2 = 0L
    val t1 = measureTimeMillis {
        var numbMap: MutableMap<Long, Long> = mutableMapOf()
        numbs.forEach { numbMap[it.toLong()] = 1 }

        println(numbMap)
        for (i in 0..<25) {
            numbMap = blink(numbMap)
        }
        resPart1 = numbMap.values.reduce { sum, next -> sum + next }
    }

    val t2 = measureTimeMillis {
        var numbMap: MutableMap<Long, Long> = mutableMapOf()
        numbs.forEach { numbMap[it.toLong()] = 1 }

        println(numbMap)
        for (i in 0..<75) {
            numbMap = blink(numbMap)
        }
        resPart2 = numbMap.values.reduce { sum, next -> sum + next }
    }

    println("Part1: After 25 blinks there are $resPart1 Stones [$t1 ms]")
    println("Part2: After 75 blinks there are $resPart2 Stones [$t2 ms]")
}

fun blink(numbsMap: MutableMap<Long, Long>): MutableMap<Long, Long> {
    val tmp = mutableMapOf<Long, Long>()
    numbsMap.forEach { (key, value) ->
        val str = key.toString()
        if (key == 0L) {
            tmp[1] = tmp.getOrPut(1) { 0 } + value
        } else if (str.length % 2 == 0) {
            val right = str.substring(str.length / 2).toLong()
            tmp[right] = tmp.getOrPut(right) { 0 } + value

            val left = str.substring(0, str.length / 2).toLong()
            tmp[left] = tmp.getOrPut(left) { 0 } + value
        } else {
            tmp[key * 2024] = value
        }
    }
    return tmp
}