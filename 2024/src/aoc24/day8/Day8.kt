package aoc24.day8

import Util.generate2DCharField
import java.io.File

fun main() {
    val input = File("2024/src/aoc24/day8/input.txt")
    val field: MutableList<MutableList<Char>> = generate2DCharField(input)

    val antennas: MutableMap<Char, MutableList<Pair<Int, Int>>> = mutableMapOf()
    field.forEachIndexed { rowIndex, row ->
        row.forEachIndexed { columnIndex, value ->
            if (value != '.') {
                val list = antennas[value]
                if (list == null) {
                    antennas[value] = mutableListOf()
                }
                antennas[value]!!.add(Pair(rowIndex, columnIndex))
            }
        }
    }

    findInterference(antennas, field)
    findInterferenceExtended(antennas, field)
}

fun findInterferenceExtended(
    antennas: MutableMap<Char, MutableList<Pair<Int, Int>>>,
    field: MutableList<MutableList<Char>>
) {
    val interferencePositions = mutableSetOf<Pair<Int, Int>>()
    antennas.forEach { (_, positions) ->

        for (i in 0..positions.lastIndex) {
            for (j in i + 1..positions.lastIndex) {


                val a1 = positions[i]
                val a2 = positions[j]
                val yDiff = a1.first - a2.first
                val xDiff = a1.second - a2.second

                var inField = true
                var factor = 1
                while (inField) {
                    val i1 = Pair(a1.first + yDiff * factor, a1.second + xDiff * factor)
                    if (i1.first >= 0 && i1.first <= field.lastIndex && i1.second >= 0 && i1.second <= field[0].lastIndex) {
                        interferencePositions.add(i1)
                        factor++
                    } else {
                        inField = false
                    }

                }

                inField = true
                factor = 1
                while (inField) {
                    val i2 = Pair(a2.first - yDiff * factor, a2.second - xDiff * factor)
                    if (i2.first >= 0 && i2.first <= field.lastIndex && i2.second >= 0 && i2.second <= field[0].lastIndex) {
                        interferencePositions.add(i2)
                        factor++
                    } else {
                        inField = false
                    }

                }

                interferencePositions.add(a1)
                interferencePositions.add(a2)
            }
        }
    }
    println("Part2: With the new information, there are ${interferencePositions.size} interference positions ")
}

fun findInterference(
    antennas: MutableMap<Char, MutableList<Pair<Int, Int>>>,
    field: MutableList<MutableList<Char>>
) {
    val interferencePositions = mutableSetOf<Pair<Int, Int>>()
    antennas.forEach { (_, positions) ->

        for (i in 0..positions.lastIndex) {
            for (j in i + 1..positions.lastIndex) {

                val a1 = positions[i]
                val a2 = positions[j]
                val yDiff = a1.first - a2.first
                val xDiff = a1.second - a2.second

                val i1 = Pair(a1.first + yDiff, a1.second + xDiff)
                val i2 = Pair(a2.first - yDiff, a2.second - xDiff)
                if (i1.first >= 0 && i1.first <= field.lastIndex && i1.second >= 0 && i1.second <= field[0].lastIndex) {
                    interferencePositions.add(i1)
                }
                if (i2.first >= 0 && i2.first <= field.lastIndex && i2.second >= 0 && i2.second <= field[0].lastIndex) {
                    interferencePositions.add(i2)
                }
            }
        }
    }
    println("Part1: There are ${interferencePositions.size} interference positions ")
}