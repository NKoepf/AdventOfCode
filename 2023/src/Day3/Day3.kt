package Day3

import Util.generate2DCharField
import Util.printField
import java.io.File

fun main() {
    part2()
}

fun part2() {
    val file = File("2023/src/Day3/input.txt")
    val field: MutableList<MutableList<Char>> = generate2DCharField(file)
    printField(field)
    var sum = 0

    field.forEachIndexed { i, row ->
        var numString = ""
        var adjacentToSymbol = false

        row.forEachIndexed { j, col ->

            val char = field[i][j]
            if (char == '*') {
                val gears = findAdjacentValues(field, i, j).distinct()
                if (gears.size == 2) {
                    sum += gears[0] * gears[1]
                }
                println(findAdjacentValues(field, i, j).distinct())

//                numString = numString.plus(char)
//                if (checkAdjacentFields(field, i, j)) {
//                    adjacentToSymbol = true
//
//                    if (j == field[i].size - 1) { // special case if number is at end of line
//                        if (!numString.equals("") && adjacentToSymbol) {
//                            sum += numString.toInt()
//                        }
//                        numString = ""
//                        adjacentToSymbol = false
//                    }
//                }
//
//            } else {
//                if (!numString.equals("") && adjacentToSymbol) {
//                    sum += numString.toInt()
//                    println("new sum: $sum")
//                }
//                numString = ""
//                adjacentToSymbol = false
            }
        }
    }
    println(sum)
}

fun part1() {
    val file = File("2023/src/Day3/input.txt")
    val field: MutableList<MutableList<Char>> = generate2DCharField(file)
    printField(field)
    var sum = 0

    field.forEachIndexed { i, row ->
        var numString = ""
        var adjacentToSymbol = false

        row.forEachIndexed { j, col ->

            val char = field[i][j]
            if (char.isDigit()) {

                numString = numString.plus(char)
                if (checkAdjacentFields(field, i, j)) {
                    adjacentToSymbol = true

                    if (j == field[i].size - 1) { // special case if number is at end of line
                        if (!numString.equals("") && adjacentToSymbol) {
                            sum += numString.toInt()
                        }
                        numString = ""
                        adjacentToSymbol = false
                    }
                }

            } else {
                if (!numString.equals("") && adjacentToSymbol) {
                    sum += numString.toInt()
                    println("new sum: $sum")
                }
                numString = ""
                adjacentToSymbol = false
            }
        }
    }
    println(sum)
}

fun checkAdjacentFields(field: MutableList<MutableList<Char>>, x: Int, y: Int): Boolean {
    if (x - 1 >= 0 && y - 1 >= 0) { //top left
        if (isSymbol(field[x - 1][y - 1])) return true
    }
    if (x - 1 >= 0 && y + 1 <= field[x].size - 1) { // top right
        if (isSymbol(field[x - 1][y + 1])) return true
    }
    if (x - 1 >= 0) { // top
        if (isSymbol(field[x - 1][y])) return true
    }
    if (y + 1 <= field.size - 1) { // right
        if (isSymbol(field[x][y + 1])) return true
    }
    if (y - 1 >= 0) { // left
        if (isSymbol(field[x][y - 1])) return true
    }
    if (x + 1 <= field.size - 1 && y - 1 >= 0) { //bot left
        if (isSymbol(field[x + 1][y - 1])) return true
    }
    if (x + 1 <= field.size - 1 && +y + 1 <= field[x].size - 1) { // bot right
        if (isSymbol(field[x + 1][y + 1])) return true
    }
    if (x + 1 <= field.size - 1) { // bot
        if (isSymbol(field[x + 1][y])) return true
    }
    return false
}

fun findAdjacentValues(field: MutableList<MutableList<Char>>, x: Int, y: Int): List<Int> {
    val adjacentNumbers = mutableListOf<Int>()
    if (x - 1 >= 0 && y - 1 >= 0) { //top left
        val num = findFullNumber(field, x - 1, y - 1)
        if (num != 0) adjacentNumbers.add(num)
    }
    if (x - 1 >= 0 && y + 1 <= field[x].size - 1) { // top right
        val num = findFullNumber(field, x - 1, y + 1)
        if (num != 0) adjacentNumbers.add(num)
    }
    if (x - 1 >= 0) { // top
        val num = findFullNumber(field, x - 1, y)
        if (num != 0) adjacentNumbers.add(num)
    }
    if (y + 1 <= field.size - 1) { // right
        val num = findFullNumber(field, x, y + 1)
        if (num != 0) adjacentNumbers.add(num)
    }
    if (y - 1 >= 0) { // left
        val num = findFullNumber(field, x, y - 1)
        if (num != 0) adjacentNumbers.add(num)
    }
    if (x + 1 <= field.size - 1 && y - 1 >= 0) { //bot left
        val num = findFullNumber(field, x + 1, y - 1)
        if (num != 0) adjacentNumbers.add(num)
    }
    if (x + 1 <= field.size - 1 && +y + 1 <= field[x].size - 1) { // bot right
        val num = findFullNumber(field, x + 1, y + 1)
        if (num != 0) adjacentNumbers.add(num)
    }
    if (x + 1 <= field.size - 1) { // bot
        val num = findFullNumber(field, x + 1, y)
        if (num != 0) adjacentNumbers.add(num)
    }
    return adjacentNumbers
}
//..1234

fun findFullNumber(field: MutableList<MutableList<Char>>, x: Int, y: Int): Int {
    if (field[x][y].isDigit()) {
        var yIndex = y
        while (yIndex >= 0 && field[x][yIndex].isDigit()) {
            yIndex--
        }
        yIndex++
        var number = ""
        while (yIndex <= field[x].size - 1 && field[x][yIndex].isDigit()) {
            number = number.plus(field[x][yIndex])
            yIndex++
        }
        return number.toInt()
    } else {
        return 0
    }
}

fun isSymbol(c: Char): Boolean {
    return c.toString().contains("[^0-9.]".toRegex())
}