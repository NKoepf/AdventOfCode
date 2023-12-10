package Day10

import java.io.File

fun main() {
    part1and2()
}

var direction: DirectionEnum = DirectionEnum.NONE

enum class DirectionEnum {
    NONE,
    RIGHT,
    LEFT,
    UP,
    DOWN
}

fun part1and2() {
    Util.printPart1()
    val field = Util.generate2DCharField(File("2023/src/Day10/input.txt"))
    var startPoint: Tile? = null

    field.forEachIndexed { rowIdx, row ->
        row.forEachIndexed { colIdx, char -> if (char == 'S') startPoint = Tile('S', colIdx, rowIdx) }
    }
    val pipeSegments: MutableList<Tile> = mutableListOf()

    determineStartDirection(pipeSegments, startPoint!!, field)
    getClosedLoop(pipeSegments, field)

    pipeSegments.forEach { seg ->

        field[seg.yPos][seg.xPos] = 'X'
    }
    Util.printField(field)
    println("segment with longest distance is ${pipeSegments.size / 2} steps away.")

    Util.printPart2()
    checkForEnclosedTiles(field, pipeSegments)

}

data class Tile(
    var value: Char,
    val xPos: Int,
    val yPos: Int,
)

fun move(field: MutableList<MutableList<Char>>, currentTile: Tile): Tile {
    if (currentTile.value == '|') {
        if (direction == DirectionEnum.UP) {
            return moveUp(field, currentTile)
        } else {
            return moveDown(field, currentTile)
        }
    }
    if (currentTile.value == '-') {
        if (direction == DirectionEnum.RIGHT) {
            return moveRight(field, currentTile)
        } else {
            return moveLeft(field, currentTile)
        }
    }
    if (currentTile.value == 'L') {
        if (direction == DirectionEnum.LEFT) {
            direction = DirectionEnum.UP
            return moveUp(field, currentTile)
        } else {
            direction = DirectionEnum.RIGHT
            return moveRight(field, currentTile)
        }
    }
    if (currentTile.value == 'J') {
        if (direction == DirectionEnum.RIGHT) {
            direction = DirectionEnum.UP
            return moveUp(field, currentTile)
        } else {
            direction = DirectionEnum.LEFT
            return moveLeft(field, currentTile)
        }
    }
    if (currentTile.value == '7') {
        if (direction == DirectionEnum.RIGHT) {
            direction = DirectionEnum.DOWN
            return moveDown(field, currentTile)
        } else {
            direction = DirectionEnum.LEFT
            return moveLeft(field, currentTile)
        }
    }
    if (currentTile.value == 'F') {
        if (direction == DirectionEnum.UP) {
            direction = DirectionEnum.RIGHT
            return moveRight(field, currentTile)
        } else {
            direction = DirectionEnum.DOWN
            return moveDown(field, currentTile)
        }
    } else {
        println("wtf?? $currentTile")
        throw RuntimeException()
    }
}

fun moveDown(field: MutableList<MutableList<Char>>, currentTile: Tile): Tile {
    return Tile(field[currentTile.yPos + 1][currentTile.xPos], currentTile.xPos, currentTile.yPos + 1)
}

fun moveUp(field: MutableList<MutableList<Char>>, currentTile: Tile): Tile {
    return Tile(field[currentTile.yPos - 1][currentTile.xPos], currentTile.xPos, currentTile.yPos - 1)
}

fun moveRight(field: MutableList<MutableList<Char>>, currentTile: Tile): Tile {
    return Tile(field[currentTile.yPos][currentTile.xPos + 1], currentTile.xPos + 1, currentTile.yPos)
}

fun moveLeft(field: MutableList<MutableList<Char>>, currentTile: Tile): Tile {
    return Tile(field[currentTile.yPos][currentTile.xPos - 1], currentTile.xPos - 1, currentTile.yPos)
}

fun determineStartDirection(pipeSegments: MutableList<Tile>, startPoint: Tile, field: MutableList<MutableList<Char>>) {
    if (arrayOf('-', 'J', '7').contains(field[startPoint!!.yPos][startPoint!!.xPos + 1])) { // start right
        direction = DirectionEnum.RIGHT
        pipeSegments.add(
            Tile(
                field[startPoint!!.yPos][startPoint!!.xPos + 1],
                startPoint!!.xPos + 1,
                startPoint!!.yPos
            )
        )
    } else if (arrayOf('-', 'F', 'L').contains(field[startPoint!!.yPos][startPoint!!.xPos - 1])) { // start left
        direction = DirectionEnum.LEFT
        pipeSegments.add(
            Tile(
                field[startPoint!!.yPos][startPoint!!.xPos - 1],
                startPoint!!.xPos - 1,
                startPoint!!.yPos
            )
        )
    } else if (arrayOf('|', 'L', 'J').contains(field[startPoint!!.yPos + 1][startPoint!!.xPos])) { // start down
        direction = DirectionEnum.DOWN
        pipeSegments.add(
            Tile(
                field[startPoint!!.yPos + 1][startPoint!!.xPos],
                startPoint!!.xPos,
                startPoint!!.yPos + 1
            )
        )
    } else { // start up
        direction = DirectionEnum.UP
        pipeSegments.add(
            Tile(
                field[startPoint!!.yPos - 1][startPoint!!.xPos],
                startPoint!!.xPos,
                startPoint!!.yPos - 1
            )
        )
    }
}

fun getClosedLoop(pipeSegments: MutableList<Tile>, field: MutableList<MutableList<Char>>) {
    var currentSegment = pipeSegments.first()
    var closedLoop = false
    while (!closedLoop) {
        val nextSegment = move(field, currentSegment)
        if (nextSegment.value == 'S') {
            pipeSegments.add(nextSegment)
            closedLoop = true
        } else {
            pipeSegments.add(nextSegment)
            currentSegment = pipeSegments.last()
        }
    }
}

fun checkForEnclosedTiles(field: MutableList<MutableList<Char>>, pipeSegments: MutableList<Tile>) {
    var newField: MutableList<MutableList<Int>> = mutableListOf()

    field.forEach { row ->
        var l: MutableList<Int> = mutableListOf()
        newField.add(l)
        row.forEach { col ->
            l.add(-1)
        }
    }
    pipeSegments.add(0, pipeSegments.removeLast())
    pipeSegments.forEachIndexed { i, pipe ->
        newField[pipe.yPos][pipe.xPos] = i
    }

    var found = 0
    newField.forEachIndexed { rIdx, row ->
        var winding = 0
        row.forEachIndexed { cIdx, col ->
            val v = newField[rIdx][cIdx]
            if (v == -1 && winding != 0) found++
            if (v != -1) {
                if (rIdx + 1 < newField.size && newField[rIdx + 1][cIdx] != -1) { // under current field is still field and also pipe
                    if (newField[rIdx + 1][cIdx] == (v + 1) % pipeSegments.size)
                        winding += 1
                    else if ((newField[rIdx + 1][cIdx] + 1) % pipeSegments.size == v)
                        winding -= 1
                }
            }

        }
    }
    println("There are $found fields enclosed by pipes")

}