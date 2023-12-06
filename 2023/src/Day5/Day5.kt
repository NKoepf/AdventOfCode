package Day5

import Util.printLongField
import java.io.File

fun main() {
    part2()
}

fun part1() {
    val file = File("2023/src/Day5/input.txt")
    var seeds: MutableList<MutableList<Long>> = mutableListOf()
    var inMap = false
    // create list in seeds list for every seed

    //for each map, check if every seed or last element in inner seeds-list is in the range of the given values. if so, add the mapped value to the list and continue
    var mapIndex = -1
    file.forEachLine {
        if (it.startsWith("seeds:")) {
            val s = it.substringAfter(": ").split(" ")
            s.forEach { seed -> seeds.add(mutableListOf(seed.toLong())) }
            printLongField(seeds)
        } else if (it.isBlank()) {
            printLongField(seeds)
            inMap = false
            println()
            mapIndex++
            seeds.forEach { seed ->
                // is value within range?
                if (seed.size < mapIndex + 1) {
                    seed.add(seed[mapIndex - 1])
                }
            }

            return@forEachLine
        } else if (it.endsWith("map:")) {
            inMap = true
            println(it)
        } else if (inMap == true) {

            val values = it.split(" ")
            val steps = values[2].toLong()
            val destStart = values[0].toLong()
            val sourceStart = values[1].toLong()

            seeds.forEach { seed ->
                // is value within range?
                if (seed[mapIndex] >= sourceStart && seed[mapIndex] <= sourceStart + steps) {
                    // is in range
                    val diff = seed[mapIndex] - sourceStart
                    seed.add(destStart + diff)
                }
            }
        }
    }
    printLongField(seeds)
    val lowestLocation = seeds.map { seed -> seed.last() }.sorted().first()
    println("nearest location $lowestLocation")
}

fun part2() {
    val file = File("2023/src/Day5/testInput.txt")
    var seeds: MutableList<MutableList<Long>> = mutableListOf()
    var inMap = false
    // create list in seeds list for every seed

    //for each map, check if every seed or last element in inner seeds-list is in the range of the given values. if so, add the mapped value to the list and continue
    var mapIndex = -1
    file.forEachLine {

        if (it.startsWith("seeds:")) {
            val s = it.substringAfter(": ").split(" ")
            val numberOfPairs = s.size / 2
            var index = 0
            var foundNums: Set<Long> = mutableSetOf()

            for (i in 0..<numberOfPairs) {
                for (k in s[index].toLong()..<s[index].toLong() + s[index + 1].toLong()) {
                    println(k)
                    if (!foundNums.contains(k)) {
                        seeds.add(mutableListOf(k))
                    } else {
                        println("already contained")
                    }
                }
                index += 2
            }

            printLongField(seeds)
        } else if (it.isBlank()) {
            printLongField(seeds)
            inMap = false
            println()
            mapIndex++
            seeds.forEach { seed ->
                // is value within range?
                if (seed.size < mapIndex + 1) {
                    seed.add(seed[mapIndex - 1])
                }
            }

            return@forEachLine
        } else if (it.endsWith("map:")) {
            inMap = true
            println(it)
        } else if (inMap == true) {

            val values = it.split(" ")
            val steps = values[2].toLong()
            val destStart = values[0].toLong()
            val sourceStart = values[1].toLong()

            seeds.forEach { seed ->
                // is value within range?
                if (seed[mapIndex] >= sourceStart && seed[mapIndex] < sourceStart + steps) {
                    // is in range
                    val diff = seed[mapIndex] - sourceStart
                    seed.add(destStart + diff)
                }
            }
        }
    }
    printLongField(seeds)
    val lowestLocation = seeds.map { seed -> seed.last() }.sorted().first()
    println("nearest location $lowestLocation")
}