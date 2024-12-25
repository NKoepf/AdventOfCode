package aoc24.Day20

import Util.generate2DCharFieldWithStartPos
import Util.printField
import java.io.File

var visited = mutableMapOf<Pair<Int, Int>, Int>()
val walls = mutableSetOf<Pair<Int, Int>>()
var target: Pair<Int, Int> = 0 to 0
val cheatDistance = 100
var cheatsFound = 0

var manhattanDistance = 20
var cheatsPart2Found = 0

val up = -1 to 0
val down = 1 to 0
val right = 0 to 1
val left = 0 to -1

var field: MutableList<MutableList<Char>> = mutableListOf()

fun main() {
    val input = File("2024/src/aoc24/day20/input.txt")
    val (startPos, f) = generate2DCharFieldWithStartPos(input, 'S')
    field = f
    printField(field)

    field.forEachIndexed { rowI, row ->
        row.forEachIndexed { colI, c ->
            if (c == '#') {
                walls.add(Pair(rowI, colI))
            } else if (c == 'E') {
                target = rowI to colI
            }
        }
    }

    walkPath(startPos)
//    findCheatsPart1()
    findCheatsPart2()
    println()
}

fun walkPath(startPos: Pair<Int, Int>) {
    visited = mutableMapOf<Pair<Int, Int>, Int>()
    move(field, startPos, 0)
}

fun findCheatsPart1() {
    var cheatFound = mutableMapOf<Int, Int>()

    visited.forEach { location ->
        val pos = location.key
        // we want to check for the current position, if there are neighbors behind a wall, with thickness 1

        //up
        checkDirection(pos, up, location.value, cheatFound)
        //right
        checkDirection(pos, right, location.value, cheatFound)
        //down
        checkDirection(pos, down, location.value, cheatFound)
        //left
        checkDirection(pos, left, location.value, cheatFound)
    }

    println("Part 1: We found $cheatsFound possible cheats for a saving of $cheatDistance and above")
}

fun findCheatsPart2() {
    val cheatFound: MutableMap<Int, Int> = mutableMapOf()
    visited.forEach { location ->
        // get all possible positions in manhattan distance
        val possibles = findManhattanDistances(location.key, manhattanDistance)

        // we check for every manhattan position if it is a visited spot and if it will be a shortcut
        possibles.forEach { manPos ->
//            if(visited.containsKey(manPos) &&  visited[manPos]!! > location.value + cheatDistance) {
            if (visited.containsKey(manPos.first) && visited[manPos.first]!! - location.value >= cheatDistance + manPos.second) {
                val distance = visited[manPos.first]!! - location.value - manPos.second
                val v = cheatFound.getOrPut(distance) { 0 }
                println("d of ${location.key} to $manPos $distance")
                cheatFound[distance] = v + 1
                cheatsPart2Found++
            }
        }
    }

    println("Part 2: We found $cheatsPart2Found possible cheats for a saving of $cheatDistance and above")
}

fun findManhattanDistances(startPos: Pair<Int, Int>, maxDistance: Int): List<Pair<Pair<Int, Int>, Int>> {
    val possibles = mutableSetOf<Pair<Pair<Int, Int>, Int>>()

    for (distance in 1..maxDistance) {
        for (x in 0..distance) {
            val y = distance - x
            possibles.add(startPos.first + y to startPos.second + x to x + y)
            possibles.add(startPos.first - y to startPos.second + x to x + y)
            possibles.add(startPos.first + y to startPos.second - x to x + y)
            possibles.add(startPos.first - y to startPos.second - x to x + y)
        }
    }

    return possibles.toList()
}

fun checkDirection(
    pos: Pair<Int, Int>,
    direction: Pair<Int, Int>,
    value: Int,
    cheatFound: MutableMap<Int, Int>
) {
    if (checkInBounds(pos + direction * 2)
        && walls.contains(pos + direction)
        && visited.contains(pos + direction * 2)
        && visited[pos + direction * 2]!! - value > cheatDistance
    ) {
        val distance = visited[pos + direction * 2]!! - value - 2
        val v = cheatFound.getOrPut(distance) { 0 }
        cheatFound[distance] = v + 1
        cheatsFound++
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

    if (pos == target) {
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
    return !(pos.first > field.lastIndex
            || pos.first < 0
            || pos.second > field[0].lastIndex
            || pos.second < 0)
}

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
    return first + other.first to second + other.second
}

operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>): Pair<Int, Int> {
    return first - other.first to second - other.second
}

operator fun Pair<Int, Int>.times(factor: Int): Pair<Int, Int> {
    return first * factor to second * factor
}