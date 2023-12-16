package Day16

import Day10.DirectionEnum
import Day10.Tile
import java.io.File

var visited: MutableMap<Tile, MutableList<DirectionEnum>> = mutableMapOf()
fun main() {
    val file = File("2023/src/Day16/input.txt")
    Util.printPart1()
    part1(file)

    Util.printPart2()
    part2(file)
}

private fun part1(file: File) {
    visited = mutableMapOf()
    val field = Util.generate2DCharField(file)
    Util.printField(field)
    val start = Tile(field[0][0], 0, 0)
    move(field, start, DirectionEnum.RIGHT)
//    println(visited)
    printVisitedField(field)
    println("There were ${visited.keys.size} tiles powered")
}

private fun part2(file: File) {
    val field = Util.generate2DCharField(file)
    //Util.printField(field)
    var maxEnergized = 0
    field.first().forEachIndexed { i, c ->
        visited = mutableMapOf()
        move(field, Tile(c, i, 0), DirectionEnum.DOWN)
        if (visited.keys.size > maxEnergized) maxEnergized = visited.keys.size
    }
    field.last().forEachIndexed { i, c ->
        visited = mutableMapOf()
        move(field, Tile(c, i, field.size - 1), DirectionEnum.UP)
        if (visited.keys.size > maxEnergized) maxEnergized = visited.keys.size
    }

    for (i in 0..<field.size) {
        visited = mutableMapOf()
        move(field, Tile(field[i][0], 0, i), DirectionEnum.RIGHT)
        if (visited.keys.size > maxEnergized) maxEnergized = visited.keys.size
    }
    for (i in 0..<field.size) {
        visited = mutableMapOf()
        move(field, Tile(field[i][field[0].size - 1], field[0].size - 1, i), DirectionEnum.LEFT)
        if (visited.keys.size > maxEnergized) maxEnergized = visited.keys.size
    }
    println("The most powered tiles are $maxEnergized")
}

private fun move(field: MutableList<MutableList<Char>>, tile: Tile, direction: DirectionEnum) {
    visited.putIfAbsent(tile, mutableListOf())
    if (visited[tile]!!.contains(direction)) {
        return
    } else {
        visited[tile]!!.add(direction)
    }

    // move to next tile
    if (tile.value == '/' && direction == DirectionEnum.RIGHT) {
        moveUp(field, tile)
    } else if (tile.value == '/' && direction == DirectionEnum.DOWN) {
        moveLeft(field, tile)
    } else if (tile.value == '/' && direction == DirectionEnum.LEFT) {
        moveDown(field, tile)
    } else if (tile.value == '/' && direction == DirectionEnum.UP) {
        moveRight(field, tile)
    } else if (tile.value == '\\' && direction == DirectionEnum.DOWN) {
        moveRight(field, tile)
    } else if (tile.value == '\\' && direction == DirectionEnum.LEFT) {
        moveUp(field, tile)
    } else if (tile.value == '\\' && direction == DirectionEnum.UP) {
        moveLeft(field, tile)
    } else if (tile.value == '\\' && direction == DirectionEnum.RIGHT) {
        moveDown(field, tile)
    } else if ((tile.value == '|' && direction == DirectionEnum.LEFT) || (tile.value == '|' && direction == DirectionEnum.RIGHT)) {
        moveUp(field, tile)
        moveDown(field, tile)
    } else if ((tile.value == '-' && direction == DirectionEnum.UP) || (tile.value == '-' && direction == DirectionEnum.DOWN)) {
        moveLeft(field, tile)
        moveRight(field, tile)
    } else {
        keepMoving(field, tile, direction)
    }

}

private fun moveRight(field: MutableList<MutableList<Char>>, tile: Tile) {
    if (tile.xPos + 1 < field[0].size) {
        move(field, Tile(field[tile.yPos][tile.xPos + 1], tile.xPos + 1, tile.yPos), DirectionEnum.RIGHT)
    } else { // field above is outside so abort here
        return
    }
}

private fun moveDown(field: MutableList<MutableList<Char>>, tile: Tile) {
    if (tile.yPos + 1 < field.size) {
        move(field, Tile(field[tile.yPos + 1][tile.xPos], tile.xPos, tile.yPos + 1), DirectionEnum.DOWN)
    } else { // field above is outside so abort here
        return
    }
}

private fun moveLeft(field: MutableList<MutableList<Char>>, tile: Tile) {
    if (tile.xPos - 1 >= 0) {
        move(field, Tile(field[tile.yPos][tile.xPos - 1], tile.xPos - 1, tile.yPos), DirectionEnum.LEFT)
    } else { // field above is outside so abort here
        return
    }
}

private fun moveUp(field: MutableList<MutableList<Char>>, tile: Tile) {
    if (tile.yPos - 1 >= 0) {
        move(field, Tile(field[tile.yPos - 1][tile.xPos], tile.xPos, tile.yPos - 1), DirectionEnum.UP)
    } else { // field above is outside so abort here
        return
    }
}

private fun keepMoving(field: MutableList<MutableList<Char>>, tile: Tile, direction: DirectionEnum) {
    if (direction == DirectionEnum.UP) moveUp(field, tile)
    if (direction == DirectionEnum.DOWN) moveDown(field, tile)
    if (direction == DirectionEnum.LEFT) moveLeft(field, tile)
    if (direction == DirectionEnum.RIGHT) moveRight(field, tile)
}

private fun printVisitedField(field: MutableList<MutableList<Char>>) {
    var visitedField = field
    visited.keys.forEach { tile ->
        visitedField[tile.yPos][tile.xPos] = 'X'
    }

    Util.printField(visitedField)
}
