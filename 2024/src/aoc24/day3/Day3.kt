package aoc24.day3

import java.io.File

fun main() {
    val input = File("2024/src/aoc24/day3/input.txt")
    var finalLine = ""
    input.forEachLine { line ->
        finalLine += line
    }

    val sumOfProducts = getMultiplications(finalLine)
    val sumOfCleanedProducts = getMultiplications(sanitizeInput(finalLine))

    println("Part1: the sum of all valid multiplications is $sumOfProducts")
    println("Part2: the sum of all valid multiplications, after cleaning up, is $sumOfCleanedProducts")
}

private fun getMultiplications(line: String): Long {
    val matcher = "mul\\(\\d{1,3},\\d{1,3}\\)".toRegex()
    val multis = matcher.findAll(line).map { it.value }.toList()
        .map { it.replace("mul(", "") }
        .map { it.replace(")", "") }
        .map { it.split(",") }

    var sum: Long = 0
    multis.forEach { multiPair ->
        sum += multiPair.first().toInt() * multiPair.last().toInt()
    }
    return sum
}

private fun sanitizeInput(line: String): String {
    val matcher = "don't\\(\\)(.*?)(?=(do\\(\\)|\$))".toRegex()
    val cleanLine = line.replace(matcher, "")
    return cleanLine
}