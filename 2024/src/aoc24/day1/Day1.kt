package aoc24.day1

import java.io.File
import kotlin.math.abs

fun main() {
    val input = File("2024/src/aoc24/day1/testInput.txt")
    val left = mutableListOf<Int>()
    val right = mutableListOf<Int>()
    input.forEachLine { line ->
        val parts = line.split(" ")
        left.add(parts.first().toInt())
        right.add(parts.last().toInt())
    }
    calcDifference(left, right)
    calcSimilarity(left, right)
}

private fun calcDifference(left: MutableList<Int>, right: MutableList<Int>) {
    left.sort()
    right.sort()

    var distance: Long = 0
    left.forEachIndexed { index, value ->
        distance += abs(value - right[index])
    }

    println("The distance between both is ${distance}")
}

private fun calcSimilarity(left: MutableList<Int>, right: MutableList<Int>) {
    val occurrenceMap = right.groupBy { it }.map { it.key to it.value.size }.toMap()
    val similarity = left.fold(0) { sum, num -> sum + (num * occurrenceMap.getOrDefault(num, 0)) }
    println("The similarity of the two lists is $similarity")
}

