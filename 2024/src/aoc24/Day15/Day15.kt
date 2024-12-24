package aoc24.Day15

import Util.generate2DCharFieldWithStartPos
import Util.printField
import java.io.File

var commands: List<Char> = emptyList()
var obstacles: MutableSet<Pair<Int, Int>> = mutableSetOf()
var walls: MutableSet<Pair<Int, Int>> = mutableSetOf()

var doubleBoxes: MutableSet<Pair<Pair<Int, Int>, Pair<Int, Int>>> = mutableSetOf()
var doubleWalls: MutableSet<Pair<Pair<Int, Int>, Pair<Int, Int>>> = mutableSetOf()

var extendedField: MutableList<MutableList<Char>> = mutableListOf()

val up = -1 to 0
val down = 1 to 0
val right = 0 to 1
val left = 0 to -1

fun main() {
    Day15()
}

class Day15 {
    val input = File("2024/src/aoc24/day15/input.txt")

    init {
        var (startPos, field: MutableList<MutableList<Char>>) = generate2DCharFieldWithStartPos(input, '@')
        val lines = input.readLines()

        var index = 0

        field.forEachIndexed { rowI, row ->
            extendedField.add(mutableListOf())
            row.forEachIndexed { colI, col ->
                val c = field[rowI][colI]
                if (c == '#') {
                    walls.add(rowI to colI)
                    doubleWalls.add((rowI to colI * 2) to (rowI to colI * 2 + 1))
                    extendedField[index].addAll(listOf('#', '#'))
                } else if (c == 'O') {
                    obstacles.add(rowI to colI)
                    doubleBoxes.add((rowI to colI * 2) to (rowI to colI * 2 + 1))
                    extendedField[index].addAll(listOf('[', ']'))
                } else if (c == '.') {
                    extendedField[index].addAll(listOf('.', '.'))
                } else if (c == '@') {
                    extendedField[index].addAll(listOf('@', '.'))
                }
            }
            index++
        }

        commands = lines.subList(lines.indexOf("") + 1, lines.size).first().toList()
        var sum = 0L
        obstacles.forEach { o ->
            sum += 100 * o.first + o.second
        }
        println("Part1: $obstacles \nsum: $sum")

        // Part 2
        println("Before Robot movements")
        printField(extendedField)

        startPos = startPos.first to startPos.second * 2
        commands.forEachIndexed { i, c ->
            var obsToMove = mutableListOf<Pair<Int, Int>>()
            when (c) {
                '<' -> {
                    //left
                    startPos = moveHorizontally(startPos, left)
                }

                '>' -> {
                    startPos = moveHorizontally(startPos, right)
                }

                '^' -> {
                    startPos = moveVertically(startPos, up)
                }

                'v' -> {
                    startPos = moveVertically(startPos, down)
                }
            }

        }
        sum = 0L
        doubleBoxes.forEach { o ->
            sum += 100 * o.first.first + o.first.second
        }

        println("After Robot movements")
        printField(extendedField)
        println("Part2: $sum")
    }
}

fun moveVertically(pos: Pair<Int, Int>, direction: Pair<Int, Int>): Pair<Int, Int> {
    var startPos = pos
    var obsToMove = mutableListOf<Pair<Int, Int>>()
    var onWall = false
    var frontLine = mutableListOf(pos)

    while (frontLine.isNotEmpty()) {
        val el = frontLine.removeAt(0)

        if (contained(doubleWalls, el + direction)) {
            obsToMove = mutableListOf()
            onWall = true
            break
        } else if (contained(doubleBoxes, el + direction)) {
            val next = getIfContained(doubleBoxes, el + direction)
            if (next != null && !frontLine.contains(next.first)) {
                frontLine.add(next.first)
                obsToMove.add(next.first)
            }
            if (next != null && !frontLine.contains(next.second)) {
                obsToMove.add(next.second)
                frontLine.add(next.second)
            }
        }
    }

    if (obsToMove.isNotEmpty()) {
        // there is stuff, that can be moved -> move it
        val toMove = doubleBoxes.filter { obsToMove.contains(it.first) }
        toMove.forEach {
            doubleBoxes.remove(it)
            extendedField[it.first.first][it.first.second] = '.'
            extendedField[it.second.first][it.second.second] = '.'
        }
        toMove.forEach {
            doubleBoxes.add((it.first + direction) to (it.second + direction))
            extendedField[(it.first + direction).first][(it.first + direction).second] = '['
            extendedField[(it.second + direction).first][(it.second + direction).second] = ']'
        }

        extendedField[startPos.first][startPos.second] = '.'
        startPos = startPos + direction
        extendedField[startPos.first][startPos.second] = '@'
    } else if (!onWall) {
        // no wall or obstacle -> just move
        extendedField[startPos.first][startPos.second] = '.'
        startPos = startPos + direction
        extendedField[startPos.first][startPos.second] = '@'
    }
    return startPos
}

fun moveHorizontally(pos: Pair<Int, Int>, direction: Pair<Int, Int>): Pair<Int, Int> {
    var startPos = pos
    var obsToMove = mutableListOf<Pair<Int, Int>>()
    var hitWall = false

    if (contained(doubleBoxes, (startPos + direction))) {
        var i = 1
        while (true) {
            if (contained(doubleWalls, startPos + direction * i)) {
                obsToMove = mutableListOf()
                hitWall = true
                break
            } else if (contained(doubleBoxes, startPos + direction * i)) {
                obsToMove.add(startPos + direction * i)
                i++
            } else {
                break
            }
        }
    } else if (contained(doubleWalls, (startPos + direction))) {
        hitWall = true
    }
    if (obsToMove.isNotEmpty()) {
        // there is stuff, that can be moved -> move it
        val toMove = doubleBoxes.filter { obsToMove.contains(it.first) }
        toMove.forEach {
            doubleBoxes.remove(it)
            extendedField[it.first.first][it.first.second] = '.'
            extendedField[it.second.first][it.second.second] = '.'
        }
        toMove.forEach {
            doubleBoxes.add((it.first + direction) to (it.second + direction))
            extendedField[(it.first + direction).first][(it.first + direction).second] = '['
            extendedField[(it.second + direction).first][(it.second + direction).second] = ']'
        }

        extendedField[startPos.first][startPos.second] = '.'
        startPos = startPos + direction
        extendedField[startPos.first][startPos.second] = '@'

    } else if (!hitWall) {
        // no wall or obstacle -> just move
        extendedField[startPos.first][startPos.second] = '.'
        startPos = startPos + direction
        extendedField[startPos.first][startPos.second] = '@'
    }

    return startPos
}

fun contained(set: MutableSet<Pair<Pair<Int, Int>, Pair<Int, Int>>>, position: Pair<Int, Int>): Boolean {
    return set.map { it.first }.contains(position) || set.map { it.second }.contains(position)
}

fun getIfContained(
    set: MutableSet<Pair<Pair<Int, Int>, Pair<Int, Int>>>,
    position: Pair<Int, Int>
): Pair<Pair<Int, Int>, Pair<Int, Int>>? {
    if (contained(set, position)) {
        return set.find { it.first == position || it.second == position }
    } else return null
}

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> {
    return first + other.first to second + other.second
}

operator fun Pair<Int, Int>.times(factor: Int): Pair<Int, Int> {
    return first * factor to second * factor
}
