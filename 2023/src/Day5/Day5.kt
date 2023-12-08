package Day5

import Util.printLongField
import java.io.File

fun main() {
    part1()
    part2()
}

fun part1() {
    Util.printPart1()
    val file = File("2023/src/Day5/input.txt")
    var seeds: MutableList<MutableList<Long>> = mutableListOf()
    var inMap = false

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
    Util.printPart2()
    val input = File("2023/src/Day5/input.txt").readText()
    val sections = input.split("\n\n").drop(1)
    var seedInputs = input.lines().first().substringAfter(": ").split(" ").map { s -> s.toLong() }
    var seedRanges = mutableListOf<Pair<Long, Long>>()

    for (i in seedInputs.indices step 2) {
        seedRanges.add(Pair(seedInputs[i], seedInputs[i] + seedInputs[i + 1]))
    }

    val alman = createNewAlmanac(sections)
    var found = false
    var distance: Long = 1
    var dotCount = 0

    while (!found) {
        val seed = traverseNewAlmanac(alman, distance)
        if (distance % 100000L == 0L) {
            print(".")
            if (dotCount > 100) {
                print("\n")
                dotCount = 0
            } else {
                dotCount++
            }
        }
//         println("possible seed: $seed")
        run outer@{
            seedRanges.forEach { r ->
                if (r.first <= seed && seed <= r.second) {
                    found = true
                    println("\nclosest distance for seed is $distance")
                    return@outer
                }
            }
        }

        distance++
    }
}

fun traverseNewAlmanac(almanac: List<List<Mapping>>, start: Long): Long {
    var nextInput = start
    for (i in almanac.size - 1 downTo 0) {
        val firstOrNull =
            almanac[i].firstOrNull { mapping -> mapping.source <= nextInput && nextInput < mapping.source + mapping.distance }
        if (firstOrNull != null) {
            //println("found mapping for $nextInput in entry $firstOrNull")
            //println("mapped $nextInput to ${firstOrNull.target + (nextInput - firstOrNull.source)}")
            nextInput = firstOrNull.target + (nextInput - firstOrNull.source)

        }
    }
    return nextInput
}

data class Mapping(
    val source: Long,
    val target: Long,
    val distance: Long
)

fun createNewAlmanac(sections: List<String>): List<List<Mapping>> {
    var almanac: MutableList<List<Mapping>> = mutableListOf()

    sections.forEach { sec ->
        var lines = sec.lines().drop(1)
        var entries: MutableList<Mapping> = mutableListOf()
        lines.forEach { l ->
            val nums = l.split(" ").map { it.toLong() }
            entries.add(Mapping(nums[0], nums[1], nums[2]))
        }
        almanac.add(entries)
    }
    return almanac
}
