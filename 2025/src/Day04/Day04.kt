package Day04

import Util.generate2DCharField
import Util.printPart1
import Util.printPart2
import java.io.File

fun main() {
    Day04.run(false)
}

class Day04 {
    companion object {
        fun run(print: Boolean = false) {
            val field: MutableList<MutableList<Char>> = generate2DCharField(File("2025/src/Day04/input.txt"))
            var totalSum = 0L // result part 1
            var initialSum = 0L
            val removedFields = mutableListOf<Pair<Int, Int>>()

            var iterationSum = 0L
            do {
                iterationSum = 0
                field.forEachIndexed { rowIndex, row -> // each row
                    if (print) println(row)
                    row.forEachIndexed { colIndex, cellValue ->
                        if (cellValue == '@') {
                            val neighbors = calcNeighbors(field, (rowIndex to colIndex))
                            if (neighbors < 4) {
                                removedFields.add(rowIndex to colIndex)
                                iterationSum++
                            }
                        }
                    }
                }

                if (print) println()
                // set removed cells in field
                removedFields.forEach { field[it.first][it.second] = 'X' }
                removedFields.clear()

                if (initialSum == 0L) initialSum = iterationSum

                totalSum += iterationSum
            } while (iterationSum > 0)

            printPart1()
            println("The first iteration was able to remove $initialSum roles of paper")
            printPart2()
            println("Over all iterations it was possible to remove $totalSum roles of paper")
        }

        fun calcNeighbors(field: MutableList<MutableList<Char>>, point: Pair<Int, Int>): Int {
            var neighborCount = 0

            //top
            if (point.first > 0 && field[point.first - 1][point.second] == '@') {
                neighborCount++
            }

            //top-right
            if (point.first > 0 && point.second < field.first().size - 1 && field[point.first - 1][point.second + 1] == '@') {
                neighborCount++
            }

            //top-left
            if (point.second > 0 && point.first > 0 && field[point.first - 1][point.second - 1] == '@') {
                neighborCount++
            }

            //bottom
            if (point.first < field.size - 1 && field[point.first + 1][point.second] == '@') {
                neighborCount++
            }

            //right
            if (point.second < field.first().size - 1 && field[point.first][point.second + 1] == '@') {
                neighborCount++
            }

            //left
            if (point.second > 0 && field[point.first][point.second - 1] == '@') {
                neighborCount++
            }

            //bottom-right
            if (point.second < field.first().size - 1 && point.first < field.size - 1 && field[point.first + 1][point.second + 1] == '@') {
                neighborCount++
            }

            //bottom-left
            if (point.second > 0 && point.first < field.size - 1 && field[point.first + 1][point.second - 1] == '@') {
                neighborCount++
            }

            return neighborCount
        }
    }


}