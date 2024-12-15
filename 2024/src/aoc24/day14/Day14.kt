package aoc24.day14

import java.io.File

val maxX = 101
val maxY = 103
val seconds = 100

val xMid = maxX / 2
val yMid = maxY / 2

fun main() {
    val input = File("2024/src/aoc24/day14/input.txt")
    val positions = mutableListOf<Pair<Int, Int>>()
    val robots = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()

    var q1 = 0L
    var q2 = 0L
    var q3 = 0L
    var q4 = 0L

    input.forEachLine { line ->
        val parts = line.split(" ")
        val posParts = parts[0].substring(2).split(",")
        val pos = (posParts[0].toInt() to posParts[1].toInt())

        val veloParts = parts[1].substring(2).split(",")
        val velo = (veloParts[0].toInt() to veloParts[1].toInt())

        var xFinal = (((pos.first + velo.first * seconds) % maxX) + maxX) % maxX
        var yFinal = (((pos.second + velo.second * seconds) % maxY) + maxY) % maxY

        positions.add(Pair(xFinal, yFinal))

        robots.add(Pair(pos, velo))
    }

    positions.forEach { pair ->
        if (pair.first < xMid && pair.second < yMid) {
            q1++
        } else if (pair.first > xMid && pair.second < yMid) {
            q2++
        } else if (pair.first < xMid && pair.second > yMid) {
            q3++
        } else if (pair.first > xMid && pair.second > yMid) {
            q4++
        }
    }

    println("There are in q1: $q1, q2: $q2, q3: $q3, q4: $q4, so the total security is: ${q1 * q2 * q3 * q4} ")
    lookForPattern(robots)
}

fun lookForPattern(robots: MutableList<Pair<Pair<Int, Int>, Pair<Int, Int>>>) {
    var loops = 0

    while (true) {

        val distinct = robots.map { it.first }.distinct()
        if (distinct.size == robots.size) {
            break
        }

        for (i: Int in 0..robots.lastIndex) {
            var pos = robots[i].first
            var velo = robots[i].second
            val x = ((((pos.first + velo.first) % maxX) + maxX) % maxX)
            val y = ((((pos.second + velo.second) % maxY) + maxY) % maxY)
            robots[i] = ((x to y) to robots[i].second)
        }
        loops++
    }

    println("Part2: Pattern found after $loops loops")
}

fun moveRobot(robot: Pair<Pair<Int, Int>, Pair<Int, Int>>, moves: Int): Pair<Int, Int> {
    var movesToGo = moves
    var pos = robot.first
    val velo = robot.second
    val visited = mutableSetOf<Pair<Int, Int>>()

    for (i in 0 until movesToGo) {
        if (visited.contains(pos)) {
            // already been here, we are in loop -> target count of moves / current index i will skip many loops
            println("loop detected at $i")
            return moveRobot(pos to velo, (movesToGo - (movesToGo / i * i)))
        }
        visited.add(pos)
        var newX = (pos.first + velo.first)
        if (newX > maxX) {
            newX = newX % maxX
        } else if (newX < 0) {
            newX = maxX + newX
        }

        var newY = (pos.second + velo.second)
        if (newY > maxY) {
            newY = newY % maxY
        } else if (newY < 0) {
            newY = maxY - 1 + newY
        }

        pos = (newX to newY)
//        println(pos)
    }

    return pos
}