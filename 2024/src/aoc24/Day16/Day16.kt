package aoc24.Day16

import java.io.File

var field: MutableList<MutableList<Char>> = mutableListOf()
fun main() {
    val input = File("2024/src/aoc24/day16/input.txt")
    val r = ReindeerGames(input.readLines().toList())
    println(r.runRudolphRun())
}

class ReindeerGames(lines: List<String>) {
    private val width = lines[0].length
    private val height = lines.size
    private val walls = mutableSetOf<Pair<Int, Int>>()
    private val startPos: Pair<Int, Int>
    private val endPos: Pair<Int, Int>

    init {
        var theStartPos = 0 to 0
        var theEndPos = 0 to 0
        lines.indices.forEach { y ->
            lines[y].indices.forEach { x ->
                if (lines[y][x] == '#') {
                    walls.add(y to x)
                }
                if (lines[y][x] == 'S') {
                    theStartPos = y to x
                }
                if (lines[y][x] == 'E') {
                    theEndPos = y to x
                }
            }
        }

        startPos = theStartPos
        endPos = theEndPos
    }

    fun runRudolphRun(): Pair<Int, Int> {
        val deerList = mutableListOf(
            Deer(startPos, 0 to 1, 0, mutableSetOf()),
            Deer(startPos, -1 to 0, 1000, mutableSetOf())
        )

        val scores = mutableMapOf<Pair<Int, Int>, Int>()
        val deerAtEnd = mutableListOf<Deer>()

        while (deerList.isNotEmpty()) {
            val deer = deerList.removeAt(0)
            while (true) {
                deer.forward()
                if (walls.contains(deer.pos)) break
                if (deer.visited.contains(deer.pos)) break
                if ((scores[deer.pos] ?: Int.MAX_VALUE) < deer.score - 1000) break
                if (!walls.contains(deer.pos + deer.direction.turnRight()))
                    deerList.add(
                        Deer(
                            deer.pos,
                            deer.direction.turnRight(),
                            deer.score + 1000,
                            deer.visited.toMutableSet()
                        )
                    )
                if (!walls.contains(deer.pos + deer.direction.turnLeft()))
                    deerList.add(
                        Deer(
                            deer.pos,
                            deer.direction.turnLeft(),
                            deer.score + 1000,
                            deer.visited.toMutableSet()
                        )
                    )
                if ((scores[deer.pos] ?: Int.MAX_VALUE) > deer.score) {
                    scores[deer.pos] = deer.score
                }
                if (deer.pos == endPos) {
                    deerAtEnd.add(deer)
                    break
                }
            }
            if (deer.pos == endPos) {
//                Thread.sleep(250)
//                printVisited(deer.visited)
            }
        }
        val part1 = scores[endPos]!!
        val part2 = deerAtEnd.asSequence().filter { it.score == scores[endPos] }.map { it.visited }.flatten().toSet()
            .count() + 1
        return part1 to part2
    }

    private class Deer(
        var pos: Pair<Int, Int>,
        val direction: Pair<Int, Int>,
        var score: Int,
        val visited: MutableSet<Pair<Int, Int>>
    ) {
        fun forward() {
            visited.add(pos)
            pos += direction
            score += 1
        }
    }

    private fun printVisited(visited: MutableSet<Pair<Int, Int>>) {
        repeat(height) { y ->
            repeat(width) { x ->
                if (visited.contains(y to x)) print("#")
                else if (walls.contains(y to x)) print("|")
                else print(" ")
            }
            println()
        }
    }

    private operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>): Pair<Int, Int> {
        return first - other.first to second - other.second
    }

    private fun Pair<Int, Int>.turnRight() = when (this) {
        0 to 1 -> 1 to 0
        1 to 0 -> 0 to -1
        0 to -1 -> -1 to 0
        else -> 0 to 1
    }

    private fun Pair<Int, Int>.turnLeft() = this.turnRight().turnRight().turnRight()
}


private operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
    return first + other.first to second + other.second
}