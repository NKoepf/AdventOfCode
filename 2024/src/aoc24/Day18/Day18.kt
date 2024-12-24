package aoc24.Day18

import Util.printField
import java.io.File


val fieldSize = 70
val maxBytes = 1024

var visited = mutableMapOf<Pair<Int, Int>, Int>()
val walls = mutableSetOf<Pair<Int, Int>>()

val up = -1 to 0
val down = 1 to 0
val right = 0 to 1
val left = 0 to -1

fun main() {
    val field: MutableList<MutableList<Char>> = mutableListOf()
    for (i in 0..fieldSize) {
        field.add(mutableListOf())
        for (j in 0..fieldSize) {
            field[i].add('.')
        }
    }
    val input = File("2024/src/aoc24/day18/input.txt")
    var index = 1

    var additionalBytes = mutableListOf<Pair<Int, Int>>()
    input.forEachLine { line ->
        val coords = line.split(",").map { it.toInt() }
        val c = coords.last() to coords.first()
        if (index <= maxBytes) {
            field[c.first][c.second] = '#'
            walls.add(c)
        } else {
            additionalBytes.add(c)
        }
        index++
    }

    printField(field)
    move(field, 0 to 0, 1)
    println("Part 1: minimum required steps to reach the end are ${visited[fieldSize to fieldSize]!! - 1}")

    // -PART 2-
    run loop@{
        additionalBytes.forEachIndexed { i, byte ->
            walls.add(byte)
            visited = mutableMapOf<Pair<Int, Int>, Int>()
            move(field, 0 to 0, 1)

            if (!visited.contains(fieldSize to fieldSize)) {
                println(
                    "Part 2: The first byte to block the path is ${
                        (byte.second to byte.first).toString().replace(" ", "")
                    }"
                )
                return@loop
            } else {
                println("$byte still works ($i)")
            }
        }
    }
}


fun move(field: MutableList<MutableList<Char>>, pos: Pair<Int, Int>, cost: Int) {
    if (visited.containsKey(pos)) {
        if (visited[pos]!! > cost) {
            visited[pos] = cost
        } else { // we already reached this position for cheaper
            return
        }
    } else {
        visited[pos] = cost
    }

    if (pos == fieldSize to fieldSize) {
        // reached target -> return
        return
    }

    // check up
    if (checkInBounds(pos + up) && !walls.contains(pos + up)) {
        move(field, pos + up, cost + 1)
    }
    // check right
    if (checkInBounds(pos + right) && !walls.contains(pos + right)) {
        move(field, pos + right, cost + 1)
    }
    // check down
    if (checkInBounds(pos + down) && !walls.contains(pos + down)) {
        move(field, pos + down, cost + 1)
    }
    // check left
    if (checkInBounds(pos + left) && !walls.contains(pos + left)) {
        move(field, pos + left, cost + 1)
    }

}

fun checkInBounds(pos: Pair<Int, Int>): Boolean {
    return !(pos.first > fieldSize
            || pos.first < 0
            || pos.second > fieldSize
            || pos.second < 0)
}

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
    return first + other.first to second + other.second
}

operator fun Pair<Int, Int>.times(factor: Int): Pair<Int, Int> {
    return first * factor to second * factor
}