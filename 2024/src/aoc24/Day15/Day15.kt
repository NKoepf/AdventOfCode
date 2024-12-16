package aoc24.Day15

import Util.generate2DCharFieldWithStartPos
import java.io.File

var commands: List<Char> = emptyList()
var obstacles: MutableSet<Pair<Int, Int>> = mutableSetOf()
var walls: MutableSet<Pair<Int, Int>> = mutableSetOf()

var doubleBoxes: MutableSet<Pair<Pair<Int, Int>, Pair<Int, Int>>> = mutableSetOf()
var doubleWalls: MutableSet<Pair<Pair<Int, Int>, Pair<Int, Int>>> = mutableSetOf()

fun main() {
    val input = File("2024/src/aoc24/day15/input.txt")
    val field: Pair<Pair<Int, Int>, MutableList<MutableList<Char>>> = generate2DCharFieldWithStartPos(input, '@')
    val lines = input.readLines()

    field.second.forEachIndexed { rowI, row ->
        row.forEachIndexed { colI, col ->
            val c = field.second[rowI][colI]
            if (c == '#') {
                walls.add(rowI to colI)
                doubleWalls.add((rowI to colI * 2) to (rowI to colI * 2 + 1))
            } else if (c == 'O') {
                obstacles.add(rowI to colI)
                doubleBoxes.add((rowI to colI * 2) to (rowI to colI * 2 + 1))
            }
        }
    }

    commands = lines.subList(lines.indexOf("") + 1, lines.size).first().toList()
    var startPos = field.first

    // Part1
    /*commands.forEachIndexed { i, c ->

        when (c) {
            '^' -> {
                //up
                if (!walls.contains(startPos.first - 1 to startPos.second)) {
                    if (!obstacles.contains(startPos.first - 1 to startPos.second)) {
                        // no wall or obstacle, we move the position
                        startPos = startPos.first - 1 to startPos.second
                    } else {
                        // we found obstacle to be moved
                        var obstaclesToMove = emptyList<Pair<Int, Int>>().toMutableList()
                        var i = 1
                        while (obstacles.contains(startPos.first - i to startPos.second)) {
                            obstaclesToMove.add(startPos.first - i to startPos.second)
                            i++
                        }
                        if (walls.contains(startPos.first - i to startPos.second)) {
                            // there is nothing we could move, as after the obstacle there is a wall
                            return@forEachIndexed
                        } else {
                            // after the obstacles, there is an empty space, so we need to move all selected obstacles
                            obstaclesToMove.forEach { o ->
                                obstacles.remove(o)
                            }
                            // after removing the old ones, add the moved ones
                            obstaclesToMove.forEach { o ->
                                obstacles.add(o.first - 1 to o.second)
                            }
                            startPos = startPos.first - 1 to startPos.second
                        }
                    }
                }
            }

            '>' -> {
                //right
                if (!walls.contains(startPos.first to startPos.second + 1)) {
                    if (!obstacles.contains(startPos.first to startPos.second + 1)) {
                        // no wall or obstacle, we move the position
                        startPos = startPos.first to startPos.second + 1
                    } else {
                        // we found obstacle to be moved
                        var obstaclesToMove = emptyList<Pair<Int, Int>>().toMutableList()
                        var i = 1
                        while (obstacles.contains(startPos.first to startPos.second + i)) {
                            obstaclesToMove.add(startPos.first to startPos.second + i)
                            i++
                        }
                        if (walls.contains(startPos.first to startPos.second + i)) {
                            // there is nothing we could move, as after the obstacle there is a wall
                            return@forEachIndexed
                        } else {
                            // after the obstacles, there is an empty space, so we need to move all selected obstacles
                            obstaclesToMove.forEach { o ->
                                obstacles.remove(o)
                            }
                            // after removing the old ones, add the moved ones
                            obstaclesToMove.forEach { o ->
                                obstacles.add(o.first to o.second + 1)
                            }
                            startPos = startPos.first to startPos.second + 1
                        }
                    }
                }
            }

            'v' -> {
                //down
                if (!walls.contains(startPos.first + 1 to startPos.second)) {
                    if (!obstacles.contains(startPos.first + 1 to startPos.second)) {
                        // no wall or obstacle, we move the position
                        startPos = startPos.first + 1 to startPos.second
                    } else {
                        // we found obstacle to be moved
                        var obstaclesToMove = emptyList<Pair<Int, Int>>().toMutableList()
                        var i = 1
                        while (obstacles.contains(startPos.first + i to startPos.second)) {
                            obstaclesToMove.add(startPos.first + i to startPos.second)
                            i++
                        }
                        if (walls.contains(startPos.first + i to startPos.second)) {
                            // there is nothing we could move, as after the obstacle there is a wall
                            return@forEachIndexed
                        } else {
                            // after the obstacles, there is an empty space, so we need to move all selected obstacles
                            obstaclesToMove.forEach { o ->
                                obstacles.remove(o)
                            }
                            // after removing the old ones, add the moved ones
                            obstaclesToMove.forEach { o ->
                                obstacles.add(o.first + 1 to o.second)
                            }
                            startPos = startPos.first + 1 to startPos.second
                        }
                    }
                }
            }

            '<' -> {
                //left
//right
                if (!walls.contains(startPos.first to startPos.second - 1)) {
                    if (!obstacles.contains(startPos.first to startPos.second - 1)) {
                        // no wall or obstacle, we move the position
                        startPos = startPos.first to startPos.second - 1
                    } else {
                        // we found obstacle to be moved
                        var obstaclesToMove = emptyList<Pair<Int, Int>>().toMutableList()
                        var i = 1
                        while (obstacles.contains(startPos.first to startPos.second - i)) {
                            obstaclesToMove.add(startPos.first to startPos.second - i)
                            i++
                        }
                        if (walls.contains(startPos.first to startPos.second - i)) {
                            // there is nothing we could move, as after the obstacle there is a wall
                            return@forEachIndexed
                        } else {
                            // after the obstacles, there is an empty space, so we need to move all selected obstacles
                            obstaclesToMove.forEach { o ->
                                obstacles.remove(o)
                            }
                            // after removing the old ones, add the moved ones
                            obstaclesToMove.forEach { o ->
                                obstacles.add(o.first to o.second - 1)
                            }
                            startPos = startPos.first to startPos.second - 1
                        }
                    }
                }
            }
        }
    }*/

    var sum = 0L
    obstacles.forEach { o ->
        sum += 100 * o.first + o.second
    }
    println("Part1: $obstacles \nsum: $sum")

    // Part 2
//    startPos = field.first.first to field.first.second * 2
//    commands.forEachIndexed { i, c ->
//        when (c) {
//            '^' -> {
//                //up
//                if (!contained(doubleWalls, startPos.first - 1 to startPos.second)) {
//                    if (!contained(doubleBoxes, startPos.first - 1 to startPos.second)) {
//                        // no wall or obstacle, we move the position
//                        startPos = startPos.first - 1 to startPos.second
//                    } else {
//                        // we found obstacle to be moved
//                        var doubleBoxesToMove = emptyList<Pair<Int, Int>>().toMutableList()
//                        var i = 1
//                        while (contained(doubleBoxes, startPos.first - i to startPos.second)) {
//                            doubleBoxesToMove.add(startPos.first - i to startPos.second)
//                            i++
//                        }
//                        if (contained(doubleWalls, startPos.first - i to startPos.second)) {
//                            // there is nothing we could move, as after the obstacle there is a wall
//                            return@forEachIndexed
//                        } else {
//                            // after the doubleBoxes, there is an empty space, so we need to move all selected doubleBoxes
//                            doubleBoxesToMove.forEach { o ->
//                                doubleBoxes.remove(o)
//                            }
//                            // after removing the old ones, add the moved ones
//                            doubleBoxesToMove.forEach { o ->
//                                doubleBoxes.add(o.first - 1 to o.second)
//                            }
//                            startPos = startPos.first - 1 to startPos.second
//                        }
//                    }
//                }
//            }
//
//            '>' -> {
//                //right
//                if (!doubleWalls.contains(startPos.first to startPos.second + 1)) {
//                    if (!doubleBoxes.contains(startPos.first to startPos.second + 1)) {
//                        // no wall or obstacle, we move the position
//                        startPos = startPos.first to startPos.second + 1
//                    } else {
//                        // we found obstacle to be moved
//                        var doubleBoxesToMove = emptyList<Pair<Int, Int>>().toMutableList()
//                        var i = 1
//                        while (doubleBoxes.contains(startPos.first to startPos.second + i)) {
//                            doubleBoxesToMove.add(startPos.first to startPos.second + i)
//                            i++
//                        }
//                        if (doubleWalls.contains(startPos.first to startPos.second + i)) {
//                            // there is nothing we could move, as after the obstacle there is a wall
//                            return@forEachIndexed
//                        } else {
//                            // after the doubleBoxes, there is an empty space, so we need to move all selected doubleBoxes
//                            doubleBoxesToMove.forEach { o ->
//                                doubleBoxes.remove(o)
//                            }
//                            // after removing the old ones, add the moved ones
//                            doubleBoxesToMove.forEach { o ->
//                                doubleBoxes.add(o.first to o.second + 1)
//                            }
//                            startPos = startPos.first to startPos.second + 1
//                        }
//                    }
//                }
//            }
//
//            'v' -> {
//                //down
//                if (!doubleWalls.contains(startPos.first + 1 to startPos.second)) {
//                    if (!doubleBoxes.contains(startPos.first + 1 to startPos.second)) {
//                        // no wall or obstacle, we move the position
//                        startPos = startPos.first + 1 to startPos.second
//                    } else {
//                        // we found obstacle to be moved
//                        var doubleBoxesToMove = emptyList<Pair<Int, Int>>().toMutableList()
//                        var i = 1
//                        while (doubleBoxes.contains(startPos.first + i to startPos.second)) {
//                            doubleBoxesToMove.add(startPos.first + i to startPos.second)
//                            i++
//                        }
//                        if (doubleWalls.contains(startPos.first + i to startPos.second)) {
//                            // there is nothing we could move, as after the obstacle there is a wall
//                            return@forEachIndexed
//                        } else {
//                            // after the doubleBoxes, there is an empty space, so we need to move all selected doubleBoxes
//                            doubleBoxesToMove.forEach { o ->
//                                doubleBoxes.remove(o)
//                            }
//                            // after removing the old ones, add the moved ones
//                            doubleBoxesToMove.forEach { o ->
//                                doubleBoxes.add(o.first + 1 to o.second)
//                            }
//                            startPos = startPos.first + 1 to startPos.second
//                        }
//                    }
//                }
//            }
//
//            '<' -> {
//                //left
//                if (!doubleWalls.contains(startPos.first to startPos.second - 1)) {
//                    if (!doubleBoxes.contains(startPos.first to startPos.second - 1)) {
//                        // no wall or obstacle, we move the position
//                        startPos = startPos.first to startPos.second - 1
//                    } else {
//                        // we found obstacle to be moved
//                        var doubleBoxesToMove = emptyList<Pair<Int, Int>>().toMutableList()
//                        var i = 1
//                        while (doubleBoxes.contains(startPos.first to startPos.second - i)) {
//                            doubleBoxesToMove.add(startPos.first to startPos.second - i)
//                            i++
//                        }
//                        if (doubleWalls.contains(startPos.first to startPos.second - i)) {
//                            // there is nothing we could move, as after the obstacle there is a wall
//                            return@forEachIndexed
//                        } else {
//                            // after the doubleBoxes, there is an empty space, so we need to move all selected doubleBoxes
//                            doubleBoxesToMove.forEach { o ->
//                                doubleBoxes.remove(o)
//                            }
//                            // after removing the old ones, add the moved ones
//                            doubleBoxesToMove.forEach { o ->
//                                doubleBoxes.add(o.first to o.second - 1)
//                            }
//                            startPos = startPos.first to startPos.second - 1
//                        }
//                    }
//                }
//            }
//        }
//    }

    sum = 0L
    doubleBoxes.forEach { o ->
        sum += 100 * o.first.first + o.first.second
    }
}

fun contained(set: MutableSet<Pair<Pair<Int, Int>, Pair<Int, Int>>>, position: Pair<Int, Int>): Boolean {
    return set.map { it.first }.contains(position) || set.map { it.second }.contains(position)
}