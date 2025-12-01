package aoc24.Day21

import java.io.File

val numericPad = mutableMapOf<Char, Pair<Int, Int>>(
    '7' to Pair(0, 0),
    '8' to Pair(0, 1),
    '9' to Pair(0, 2),
    '4' to Pair(1, 0),
    '5' to Pair(1, 2),
    '6' to Pair(1, 3),
    '1' to Pair(2, 0),
    '2' to Pair(2, 2),
    '3' to Pair(2, 3),
    '0' to Pair(3, 2),
    'A' to Pair(3, 3),
)

val controlPad = mutableMapOf<Char, Pair<Int, Int>>(
    '^' to Pair(0, 1),
    'A' to Pair(0, 2),
    '<' to Pair(1, 0),
    'v' to Pair(1, 2),
    '>' to Pair(1, 3)
)

fun main() {
    val input = File("2024/src/aoc24/day21/testinput.txt")
    input.forEachLine { line ->

    }

}

fun moveControlPad(from: Char, to: Char) {

}

fun moveNumPad(from: Char, to: Char) {
    val fromPos = numericPad[from]!!
    val toPos = numericPad[to]!!


}
