package aoc24.day4

import Util.generate2DCharField
import java.io.File

fun main() {

    val input = File("2024/src/aoc24/day4/input.txt")
    val field = generate2DCharField(file = input)

    val occurrences = checkForXmas(field)
    val occurrencesX = checkForCrossXmas(field)

    println("Part1: there are $occurrences xmas in the text")
    println("Part2: there are $occurrencesX X-mas in the text")

}

fun checkForXmas(field: MutableList<MutableList<Char>>): Int {
    val foundX: MutableSet<Pair<Int, Int>> = mutableSetOf()
    var occurrences = 0

    for (row in 0..field[0].lastIndex) {
        for (col in 0..field.lastIndex) {
            // if current position is x, we need to check all 8 directions for following M, A, S
            if (field[row][col] == 'X') {

                // check if we already looked at this postion
                if (foundX.contains(Pair(row, col))) {
                    continue
                }

                // check right
                if (col + 3 <= field[row].size
                    && field[row][col + 1] == 'M'
                    && field[row][col + 2] == 'A'
                    && field[row][col + 3] == 'S'
                ) {
                    foundX.add(Pair(row, col))
                    occurrences++
                }

                // check bottom
                if (row + 3 <= field.size
                    && field[row + 1][col] == 'M'
                    && field[row + 2][col] == 'A'
                    && field[row + 3][col] == 'S'
                ) {
                    foundX.add(Pair(row, col))
                    occurrences++
                }

                // check left
                if (col - 3 >= 0
                    && field[row][col - 1] == 'M'
                    && field[row][col - 2] == 'A'
                    && field[row][col - 3] == 'S'
                ) {
                    foundX.add(Pair(row, col))
                    occurrences++
                }

                // check top
                if (row - 3 >= 0
                    && field[row - 1][col] == 'M'
                    && field[row - 2][col] == 'A'
                    && field[row - 3][col] == 'S'
                ) {
                    foundX.add(Pair(row, col))
                    occurrences++
                }

                // diagonal bottom right
                if (row + 3 <= field.lastIndex && col + 3 <= field[row + 3].lastIndex
                    && field[row + 1][col + 1] == 'M'
                    && field[row + 2][col + 2] == 'A'
                    && field[row + 3][col + 3] == 'S'
                ) {
                    foundX.add(Pair(row, col))
                    occurrences++
                }

                // diagonal bottom left
                if (row + 3 <= field.lastIndex && col - 3 >= 0
                    && field[row + 1][col - 1] == 'M'
                    && field[row + 2][col - 2] == 'A'
                    && field[row + 3][col - 3] == 'S'
                ) {
                    foundX.add(Pair(row, col))
                    occurrences++
                }

                // diagonal top right
                if (row - 3 >= 0 && col + 3 <= field[row].size
                    && field[row - 1][col + 1] == 'M'
                    && field[row - 2][col + 2] == 'A'
                    && field[row - 3][col + 3] == 'S'
                ) {
                    foundX.add(Pair(row, col))
                    occurrences++
                }

                // diagonal top left
                if (row - 3 >= 0 && col - 3 >= 0
                    && field[row - 1][col - 1] == 'M'
                    && field[row - 2][col - 2] == 'A'
                    && field[row - 3][col - 3] == 'S'
                ) {
                    foundX.add(Pair(row, col))
                    occurrences++
                }


            }
        }
    }

    return occurrences
}

fun checkForCrossXmas(field: MutableList<MutableList<Char>>): Int {
    val foundCenters: MutableSet<Pair<Int, Int>> = mutableSetOf()
    var occurrences = 0

    for (row in 0..field[0].lastIndex) {
        for (col in 0..field.lastIndex) {
            // if current position is x, we need to check all 8 directions for following M, A, S
            if (field[row][col] == 'A') {

                // check if we already looked at this postion
                if (foundCenters.contains(Pair(row, col))) {
                    continue
                }

                // check for center position
                if (row + 1 <= field.lastIndex
                    && col + 1 <= field[row + 1].lastIndex
                    && col - 1 >= 0
                    && row - 1 >= 0
                ) {

                    // check bottom left to top right
                    if ((field[row - 1][col + 1] == 'S' && field[row + 1][col - 1] == 'M'
                                || field[row - 1][col + 1] == 'M' && field[row + 1][col - 1] == 'S') &&
                        // check bottom right to top left
                        (field[row + 1][col + 1] == 'S' && field[row - 1][col - 1] == 'M'
                                || field[row + 1][col + 1] == 'M' && field[row - 1][col - 1] == 'S')
                    ) {
                        foundCenters.add(Pair(row, col))
                        occurrences++
                    }
                }
            }
        }
    }
    return occurrences
}