package aoc24.day7

import java.io.File
import kotlin.system.measureTimeMillis

fun main() {
    val input = File("2024/src/aoc24/day7/input.txt")
    var sumOfTwoOps = 0L
    var sumOfThreeOps = 0L
    val failedLines = mutableListOf<String>()
    val t1 = measureTimeMillis {
        input.forEachLine { line ->
            val split = line.split(":")
            var result = split[0].toLong()
            var factors = split[1].trim().split(" ").map { it.toLong() }

            if (calcTermTwoOperators(factors, result)) {
                sumOfTwoOps += result
            } else {
                failedLines.add(line)
            }
        }
    }

    val t2 = measureTimeMillis {
        failedLines.forEach { line ->
            val split = line.split(":")
            var result = split[0].toLong()
            var factors = split[1].trim().split(" ").map { it.toLong() }
            sumOfThreeOps += if (calcTermThreeOperators(factors, result)) result else 0
        }
    }

    println("Part1: The sum with two possible operators $sumOfTwoOps [$t1 ms]")
    println("Part2: The sum with third additional possible operators all in all  ${sumOfThreeOps + sumOfTwoOps} [$t2 ms]")

}

fun calcTermThreeOperators(factors: List<Long>, result: Long): Boolean {
    // we want to approach this backwards and try to reach the zero

    if (factors.isEmpty()) return false
    // check for addition by subtraction
    if (result - factors.last() == 0L) {
        return true
    }

    if (result - factors.last() > 0L && calcTermThreeOperators(factors.dropLast(1), result - factors.last())) {
        return true
    }

    // check for possible multiplication by dividing
    val tmp = result % factors.last()
    if (tmp == 0L) {
        if (result / factors.last() == 1L) {
            return true
        }
        if (calcTermThreeOperators(factors.dropLast(1), result / factors.last())) return true
    }

    // check for concat
    if (result.toString().endsWith(factors.last().toString())) {
        val newRes = result.toString().removeSuffix(factors.last().toString()).toLong()
        if (calcTermThreeOperators(factors.dropLast(1), newRes)) return true
    }
    return false

}

fun calcTermTwoOperators(factors: List<Long>, result: Long): Boolean {
    // we want to approach this backwards and try to reach the zero

    if (factors.isEmpty()) return false
    // check for addition by subtraction
    if (result - factors.last() == 0L) {
        return true
    }

    if (result - factors.last() > 0L && calcTermTwoOperators(factors.dropLast(1), result - factors.last())) {
        return true
    }

    // check for possible multiplication by dividing
    val tmp = result % factors.last()
    if (tmp == 0L) {
        if (result / factors.last() == 1L) {
            return true
        }
        if (calcTermTwoOperators(factors.dropLast(1), result / factors.last())) return true
    } else {
        return false
    }
    return false
}