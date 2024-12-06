package aoc24.day6

import Util.generate2DCharFieldWithStartPos
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import java.io.File
import kotlin.system.measureTimeMillis

val orientations = listOf('^', '>', 'v', '<')
fun main() {
    val input = File("2024/src/aoc24/day6/input.txt")
    val res = generate2DCharFieldWithStartPos(input, '^')
    val field = res.second

    var visited: MutableSet<Pair<Int, Int>> = mutableSetOf()
    val t1 = measureTimeMillis {
        visited = moveGuard(field, res.first)
    }
    println("Part1: There were ${visited.size} positions visited by the guard [$t1 ms]")


    var loopsFound = 0
    val t2 = measureTimeMillis {
        runBlocking {
            val jobs = visited.drop(1).map {
                async(Dispatchers.Default) {
                    if (moveGuardWithExtraObstacleIsLoop(
                            generate2DCharFieldWithStartPos(input, '^').second,
                            res.first,
                            it
                        )
                    ) {
                        loopsFound++
                    }
                }
            }
            jobs.awaitAll()
        }
    }
    println("Part2: found $loopsFound loops [$t2 ms]")
}


fun moveGuard(field: MutableList<MutableList<Char>>, startPos: Pair<Int, Int>): MutableSet<Pair<Int, Int>> {
    val visitedPositions: MutableSet<Pair<Int, Int>> = mutableSetOf()

    var guardPos: Pair<Char, Pair<Int, Int>> = orientations.first() to startPos
    var inField: Boolean = true

    while (inField) {
        when (guardPos.first) {

            // up
            orientations.first() -> {
                if (guardPos.second.first - 1 < 0) {
                    inField = false
                    break
                }

                visitedPositions.add(guardPos.second)
                field[guardPos.second.first][guardPos.second.second] = guardPos.first

                val uPos = field[guardPos.second.first - 1][guardPos.second.second]
                // reached obstacle, turn right
                if (uPos == '#') {
                    guardPos = rotate(guardPos.first) to guardPos.second
                } else {
                    guardPos = guardPos.first to (guardPos.second.first - 1 to guardPos.second.second)
                }
                field[guardPos.second.first][guardPos.second.second] = guardPos.first
            }

            // right
            orientations[1] -> {
                if (guardPos.second.second + 1 > field.first().lastIndex) {
                    inField = false
                    break
                }

                visitedPositions.add(guardPos.second)
                field[guardPos.second.first][guardPos.second.second] = guardPos.first

                val rPos = field[guardPos.second.first][guardPos.second.second + 1]
                // reached obstacle, turn right
                if (rPos == '#') {
                    guardPos = rotate(guardPos.first) to guardPos.second
                } else {
                    guardPos = guardPos.first to (guardPos.second.first to guardPos.second.second + 1)
                }
                field[guardPos.second.first][guardPos.second.second] = guardPos.first
            }

            // down
            orientations[2] -> {
                if (guardPos.second.first + 1 > field.lastIndex) {
                    inField = false
                    break
                }

                visitedPositions.add(guardPos.second)
                field[guardPos.second.first][guardPos.second.second] = guardPos.first

                val dPos = field[guardPos.second.first + 1][guardPos.second.second]
                // reached obstacle, turn right
                if (dPos == '#') {
                    guardPos = rotate(guardPos.first) to guardPos.second
                } else {
                    guardPos = guardPos.first to (guardPos.second.first + 1 to guardPos.second.second)
                }
                field[guardPos.second.first][guardPos.second.second] = guardPos.first
            }

            // left
            orientations[3] -> {
                if (guardPos.second.second - 1 < 0) {
                    inField = false
                    break
                }

                // mark current pos
                visitedPositions.add(guardPos.second)
                field[guardPos.second.first][guardPos.second.second] = guardPos.first

                val rPos = field[guardPos.second.first][guardPos.second.second - 1]
                // reached obstacle, turn right
                if (rPos == '#') {
                    guardPos = rotate(guardPos.first) to guardPos.second
                } else {
                    guardPos = guardPos.first to (guardPos.second.first to guardPos.second.second - 1)
                }
                field[guardPos.second.first][guardPos.second.second] = guardPos.first
            }
        }
    }

    visitedPositions.add(guardPos.second)
    return visitedPositions
}

fun moveGuardWithExtraObstacleIsLoop(
    field: MutableList<MutableList<Char>>,
    startPos: Pair<Int, Int>,
    extraObstacle: Pair<Int, Int>
): Boolean {

    val visitedPositions: MutableSet<Pair<Char, Pair<Int, Int>>> = mutableSetOf()
    field[extraObstacle.first][extraObstacle.second] = '#'
    var guardPos: Pair<Char, Pair<Int, Int>> = orientations.first() to startPos
    var inField = true

    while (inField) {
        if (visitedPositions.contains(guardPos)) {
            return true
        }
        when (guardPos.first) {
            // up
            orientations.first() -> {
                if (guardPos.second.first - 1 < 0) {
                    inField = false
                    break
                }

                visitedPositions.add(guardPos)
                field[guardPos.second.first][guardPos.second.second] = guardPos.first

                val uPos = field[guardPos.second.first - 1][guardPos.second.second]
                // reached obstacle, turn right
                if (uPos == '#') {
                    guardPos = rotate(guardPos.first) to guardPos.second
                } else {
                    guardPos = guardPos.first to (guardPos.second.first - 1 to guardPos.second.second)
                }
                field[guardPos.second.first][guardPos.second.second] = guardPos.first
            }

            // right
            orientations[1] -> {
                if (guardPos.second.second + 1 > field.first().lastIndex) {
                    inField = false
                    break
                }

                visitedPositions.add(guardPos)
                field[guardPos.second.first][guardPos.second.second] = guardPos.first

                val rPos = field[guardPos.second.first][guardPos.second.second + 1]
                // reached obstacle, turn right
                if (rPos == '#') {
                    guardPos = rotate(guardPos.first) to guardPos.second
                } else {
                    guardPos = guardPos.first to (guardPos.second.first to guardPos.second.second + 1)
                }
                field[guardPos.second.first][guardPos.second.second] = guardPos.first
            }

            // down
            orientations[2] -> {
                if (guardPos.second.first + 1 > field.lastIndex) {
                    inField = false
                    break
                }

                visitedPositions.add(guardPos)
                field[guardPos.second.first][guardPos.second.second] = guardPos.first

                val dPos = field[guardPos.second.first + 1][guardPos.second.second]
                // reached obstacle, turn right
                if (dPos == '#') {
                    guardPos = rotate(guardPos.first) to guardPos.second
                } else {
                    guardPos = guardPos.first to (guardPos.second.first + 1 to guardPos.second.second)
                }
                field[guardPos.second.first][guardPos.second.second] = guardPos.first
            }

            // left
            orientations[3] -> {
                if (guardPos.second.second - 1 < 0) {
                    inField = false
                    break
                }

                visitedPositions.add(guardPos)
                field[guardPos.second.first][guardPos.second.second] = guardPos.first

                val rPos = field[guardPos.second.first][guardPos.second.second - 1]
                // reached obstacle, turn right
                if (rPos == '#') {
                    guardPos = rotate(guardPos.first) to guardPos.second
                } else {
                    guardPos = guardPos.first to (guardPos.second.first to guardPos.second.second - 1)
                }
                field[guardPos.second.first][guardPos.second.second] = guardPos.first
            }
        }
    }

    return false
}

fun rotate(ori: Char): Char {
    if (ori == orientations.last()) {
        return orientations.first()
    }
    return orientations[orientations.indexOf(ori) + 1]
}