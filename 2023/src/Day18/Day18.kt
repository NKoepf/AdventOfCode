package Day18

import java.io.File

fun main() {
    val file = File("2023/src/Day18/input.txt")
    Util.printPart1()
    part1(file)
    Util.printPart2()
    part2(file)
}

private fun part2(file: File) {
    val corners: MutableList<Pos> = mutableListOf()
    var posX = 0L
    var posY = 0L
    var circum = 0L

    file.readLines().map {
        it.substringAfterLast(" ")
            .replace("(#", "")
            .replace(")", "")
    }
        .forEach {
            val value = it.substring(0, 5).toLong(radix = 16)
            val dir = it.last()
            if (dir == '0') {
                posX += value
            } else if (dir == '1') {
                posY += value
            } else if (dir == '3') {
                posY -= value
            } else if (dir == '2') {
                posX -= value
            }
            circum += value
            corners.add(Pos(posX, posY))
        }
    println(
        "With the extended size, there will fit ${
            pickArea(
                shoeLaceItUp(corners),
                circum
            )
        } cubic meters in the lagoon"
    )
}

private fun part1(file: File) {
    val corners: MutableList<Pos> = mutableListOf()
    var posX = 0L
    var posY = 0L
    var circum = 0L

    file.readLines().map { it.substringBeforeLast(" ").split(" ") }.forEach {
        if (it[0] == "R") {
            posX += it[1].toLong()
        } else if (it[0] == "D") {
            posY += it[1].toLong()
        } else if (it[0] == "U") {
            posY -= it[1].toLong()
        } else if (it[0] == "L") {
            posX -= it[1].toLong()
        }
        circum += it[1].toLong()
        corners.add(Pos(posX, posY))
    }
    println("There will fit ${pickArea(shoeLaceItUp(corners), circum)} cubic meters in the lagoon")
}

private fun pickArea(area: Long, perimeter: Long): Long =
    area + perimeter / 2L + 1L

private fun shoeLaceItUp(corners: List<Pos>): Long =
    corners.zipWithNext { a, b ->
        a.x * b.y - a.y * b.x
    }.sum() / 2

private data class Pos(
    val x: Long,
    val y: Long
)
