package aoc24.day5

import java.io.File
import kotlin.system.measureTimeMillis

fun main() {
    val input = File("2024/src/aoc24/day5/input.txt")

    var createRules = true
    val rules: MutableList<Pair<Int, Int>> = mutableListOf()
    val updates: MutableList<List<Int>> = mutableListOf()
    val wrongUpdate: MutableList<List<Int>> = mutableListOf()

    input.forEachLine { line ->
        if (line.isBlank()) {
            createRules = false
            return@forEachLine
        }
        if (createRules) {
            val parts = line.split("|").map { it.toInt() }
            rules.add(parts[0] to parts[1])
        } else {
            val parts = line.split(",").map { it.toInt() }
            updates.add(parts)
        }

    }
    var sum = 0
    var sumCorrected = 0
    val t1 = measureTimeMillis {
        updates.forEach { update ->
            val res = getMiddleNumberIfValid(update, rules)
            if (res == 0) wrongUpdate.add(update)
            else sum += res
        }
    }


    val t2 = measureTimeMillis {
        wrongUpdate.forEach { update ->
            sumCorrected += sortAndGetMiddle(update, rules)
        }
    }

    println("Part1: the sum of all correct middle number is: $sum")
    println("part2: After sorting of wrong updates, the sum of corrected update middles is: $sumCorrected")
}

fun getMiddleNumberIfValid(line: List<Int>, rules: List<Pair<Int, Int>>): Int {

    line.forEachIndexed { index, value ->
        //numbers that must come after 'value'
        val numbsAfter = rules.filter { it.first == value }.map { it.second }
        //numbers that must come before 'value'
        val numbsBefore = rules.filter { it.second == value }.map { it.first }

        //check for current index, if number before and after are valid
        if (!checkBefore(line, index, numbsBefore) || !checkAfter(line, index, numbsAfter)) {
            return 0
        }
    }
    return line[line.size / 2]

}


fun sortAndGetMiddle(line: List<Int>, rules: List<Pair<Int, Int>>): Int {

    val editLine = line.toMutableList()
    while (getMiddleNumberIfValid(editLine, rules) == 0) {
        editLine.forEachIndexed { index, value ->
            //numbers that must come after 'value'
            val numbsAfter = rules.filter { it.first == value }.map { it.second }
            //numbers that must come before 'value'
            val numbsBefore = rules.filter { it.second == value }.map { it.first }


            //check for current index, if number before and after are valid
            var swapIndex = checkAfterIndex(editLine, index, numbsBefore)
            if (swapIndex != index) {
                editLine[index] = editLine[swapIndex].also { editLine[swapIndex] = editLine[index] }
            }
            swapIndex = checkBeforeIndex(editLine, index, numbsAfter)
            if (swapIndex != index) {
                editLine[index] = editLine[swapIndex].also { editLine[swapIndex] = editLine[index] }
            }
        }
    }
    return editLine[editLine.size / 2]
}

fun checkBefore(line: List<Int>, currentIndex: Int, validNumbs: List<Int>): Boolean {
    line.subList(0, currentIndex).forEach {
        if (!validNumbs.contains(it)) {
            return false
        }
    }
    return true
}

fun checkAfter(line: List<Int>, currentIndex: Int, validNumbs: List<Int>): Boolean {
    line.subList(currentIndex + 1, line.size).forEach {
        if (!validNumbs.contains(it)) {
            return false
        }
    }
    return true
}

fun checkAfterIndex(line: List<Int>, currentIndex: Int, invalidNumbs: List<Int>): Int {
    line.subList(currentIndex + 1, line.size).forEachIndexed { index, it ->
        if (invalidNumbs.contains(it)) {
            return index + currentIndex + 1
        }
    }
    return currentIndex
}

fun checkBeforeIndex(line: List<Int>, currentIndex: Int, invalidNumbs: List<Int>): Int {
    line.subList(0, currentIndex).forEachIndexed { index, it ->
        if (invalidNumbs.contains(it)) {
            return index
        }
    }
    return currentIndex
}