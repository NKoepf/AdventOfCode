package aoc24.day2

import java.io.File
import kotlin.math.abs

fun main() {
    val input = File("2024/src/aoc24/day2/input.txt")
    var safeLines = 0
    var safeDampenedLines = 0
    input.forEachLine { line ->
        if (isSafe(line)) safeLines++
        if (isSafeDampened(line)) safeDampenedLines++
    }

    println("Part1: there are $safeLines lines")
    println("Part2: with dampening enabled, there are $safeDampenedLines lines")
}

private fun isSafeDampened(line: String): Boolean {
    val nums = line.split(" ").map { it.toInt() }.toMutableList()
    val increase: Boolean = nums[0] < nums[1]

    nums.forEachIndexed { index, s ->
        if (index == nums.size - 1) {
            return@forEachIndexed
        }

        if (increase && s >= nums[index + 1]) {
            return checkSafeAround(nums, index)
        }

        if (!increase && s <= nums[index + 1]) {
            return checkSafeAround(nums, index)
        }

        val diff = abs(s - nums[index + 1])
        if (diff < 1 || diff > 3) {
            return checkSafeAround(nums, index)
        }
    }
    return true
}

private fun checkSafeAround(nums: List<Int>, index: Int): Boolean {
    return (isSafe(nums.filterIndexed { i, _ -> i != index }.joinToString(separator = " "))
            || isSafe(nums.filterIndexed { i, _ -> i != index + 1 }.joinToString(separator = " "))
            || isSafe(nums.filterIndexed { i, _ -> i != index - 1 }.joinToString(separator = " ")))
}

private fun isSafe(line: String): Boolean {
    val nums = line.split(" ").map { it.toInt() }
    val increase: Boolean = nums[0] < nums[1]
    nums.forEachIndexed { index, s ->
        if (index == nums.size - 1) {
            return@forEachIndexed
        }

        val diff = abs(s - nums[index + 1])
        if (increase && s >= nums[index + 1]) {
            return false
        }
        if (!increase && s <= nums[index + 1]) {
            return false
        }

        if (diff < 1 || diff > 3) {
            return false
        }
    }
    return true
}

