package Day9

import java.io.File

fun main() {
    part1()
    part2()
}

fun part1() {
    Util.printPart1()
    var sum = 0

    File("2023/src/Day9/input.txt").forEachLine { line ->
        val allSequences: MutableList<MutableList<Int>> = mutableListOf()
        val values = line.split(" ").map { it.toInt() }.toMutableList()
        allSequences.add(values)

        var foundZeroList = false
        // create sequences
        while (!foundZeroList) {
            val prevList = allSequences.last()
            val nextList: MutableList<Int> = mutableListOf()

            for (i in 0..<prevList.size - 1) {
                nextList.add(prevList[i + 1] - prevList[i])
            }

            allSequences.add(nextList)
            if (nextList.all { it == 0 }) foundZeroList = true
        }

        println(allSequences)
        allSequences.reverse()
        for (i in 0..<allSequences.size) {
            if (i == 0) {
                allSequences[i].add(0)
            } else {
                allSequences[i].add(allSequences[i].last() + allSequences[i - 1].last())
            }
            println(allSequences[i])
        }
        sum += allSequences.last().last()

        println()
    }

    println("The sum of all extrapolated values is $sum")
}

fun part2() {
    Util.printPart2()
    var sum = 0

    File("2023/src/Day9/input.txt").forEachLine { line ->
        val allSequences: MutableList<MutableList<Int>> = mutableListOf()
        val values = line.split(" ").map { it.toInt() }.toMutableList()
        allSequences.add(values)

        var foundZeroList = false
        // create sequences
        while (!foundZeroList) {
            val prevList = allSequences.last()
            val nextList: MutableList<Int> = mutableListOf()

            for (i in 0..<prevList.size - 1) {
                nextList.add(prevList[i + 1] - prevList[i])
            }

            allSequences.add(nextList)
            if (nextList.all { it == 0 }) foundZeroList = true
        }

        println(allSequences)
        allSequences.reverse()
        for (i in 0..<allSequences.size) {
            if (i == 0) {
                allSequences[i].add(0, 0)
            } else {
                allSequences[i].add(0, allSequences[i].first() - allSequences[i - 1].first())
            }
            println(allSequences[i])
        }
        sum += allSequences.last().first()

        println()
    }

    println("The sum of all extrapolated values is $sum")
}