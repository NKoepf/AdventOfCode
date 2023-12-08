package Day8

import java.io.File

fun main() {
    part1()
    part2()
}

fun part1() {
    Util.printPart1()
    var lines = File("2023/src/Day8/input.txt").readLines()
    val instructions = lines[0].replace("L", "0").replace("R", "1").map { s -> s.toString().toInt() }

    println(instructions)
    lines = lines.drop(2)

    var mappings: MutableMap<String, Pair<String, String>> = mutableMapOf()
    lines.forEach {
        val parts = it.replace("= ", "")
            .replace("(", "")
            .replace(",", "")
            .replace(")", "")
            .split(" ")
        mappings.put(parts[0], Pair(parts[1], parts[2]))
    }
    println(mappings)
    var reachedZZZ = false
    var idx = 0
    var pos = "AAA"
    var steps = 0

    while (!reachedZZZ) {
        if (instructions[idx] == 0) {
            pos = mappings[pos]!!.first
        } else {
            pos = mappings[pos]!!.second
        }
        if (pos.equals("ZZZ")) reachedZZZ = true


        steps++
        if (idx < instructions.size - 1) idx++
        else idx = 0
    }

    println("needed $steps steps")
}

fun part2() {
    Util.printPart2()
    var lines = File("2023/src/Day8/input.txt").readLines()
    val instructions = lines[0].map { s -> s.toString() }

    println(instructions)
    lines = lines.drop(2)

    var mappings: MutableMap<String, Pair<String, String>> = mutableMapOf()
    var startPositions: MutableList<String> = mutableListOf()

    lines.forEach {
        val parts = it.replace("= ", "")
            .replace("(", "")
            .replace(",", "")
            .replace(")", "")
            .split(" ")
        mappings.put(parts[0], Pair(parts[1], parts[2]))
        if (parts[0].endsWith("A")) startPositions.add(parts[0])
    }

    println(mappings)
    println(startPositions)

    val counts = startPositions.map { start ->
        var idx = 0
        var current = start
        var count = 0L
        var searching = true
        while (searching) {
            if (instructions[idx] == "L") {
                current = mappings[current]!!.first
            } else {
                current = mappings[current]!!.second
            }

            count++

            if (current.endsWith("Z")) {
                println("$start $current $count")
                searching = false
            }

            if (idx < instructions.size - 1) idx++
            else idx = 0
        }

        count
    }
    println("counts $counts")
    val res = counts.reduce { acc, i -> Util.findLCM(acc, i) }

    println("needed $counts steps")
    println("combined needed $res")
}