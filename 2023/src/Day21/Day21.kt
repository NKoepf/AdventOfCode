package Day21

import java.io.File
import java.util.*

fun main() {
    val file = File("2023/src/Day21/input.txt")
    part1(file)
}

private fun part1(file: File) {
    val stepsToTake = 131
    val genGarden = genGarden(file)
    val start = genGarden.second
    val garden = genGarden.first

    var reachedTiles = 0
    var positions: Queue<Pair<Int, Int>> = LinkedList()
    positions.add(start)

    for (i in 1..stepsToTake) {
        val nextPositions = mutableListOf<Pair<Int, Int>>()

        while (positions.peek() != null) {
            val pos = positions.poll()
            reachedTiles++
            garden[pos.second][pos.first].visited = true
            val neighbors = getReachableNeighbors(garden, pos)
            nextPositions.addAll(neighbors)
        }
        positions.addAll(nextPositions.distinct())

    }
    printField(garden)
    println("Done with ${positions.size} possibly reached garden plots")
}

private fun printField(garden: MutableList<MutableList<GardenTile>>) {
    garden.forEach { row ->
        row.forEach { col ->
            if (col.visited) print("O") else if (col.plot) print(".") else print("#")
        }
        println()
    }
}

private fun getReachableNeighbors(
    garden: MutableList<MutableList<GardenTile>>,
    pos: Pair<Int, Int>
): MutableList<Pair<Int, Int>> {
    val neighs: MutableList<Pair<Int, Int>> = mutableListOf()

    if (pos.second - 1 >= 0 && garden[pos.second - 1][pos.first].plot) { //top
        neighs.add(pos.first to pos.second - 1)
    }
    if (pos.second + 1 < garden.size && garden[pos.second + 1][pos.first].plot) { //bot
        neighs.add(pos.first to pos.second + 1)
    }
    if (pos.first - 1 >= 0 && garden[pos.second][pos.first - 1].plot) { //left
        neighs.add(pos.first - 1 to pos.second)
    }
    if (pos.first + 1 < garden[0].size && garden[pos.second][pos.first + 1].plot) { //right
        neighs.add(pos.first + 1 to pos.second)
    }
    return neighs
}

private fun genGarden(file: File): Pair<MutableList<MutableList<GardenTile>>, Pair<Int, Int>> {
    val field: MutableList<MutableList<GardenTile>> = mutableListOf()
    var lineIndex = 0
    var start: Pair<Int, Int> = 0 to 0

    file.forEachLine { line ->
        field.add(mutableListOf())
        line.forEachIndexed { col, char ->
            field[lineIndex].add(GardenTile(col, lineIndex, if (char == '#') false else true))
            if (char == 'S') {
                start = (col to lineIndex)
            }
        }
        lineIndex++
    }
    return (field to start)
}

private data class GardenTile(
    val x: Int,
    val y: Int,
    val plot: Boolean,
    var visited: Boolean = false
)
