package aoc24.Day10


import Util.generate2DIntField
import java.io.File
import kotlin.system.measureTimeMillis

val foundPaths = mutableMapOf<Pair<Int, Int>, MutableSet<Pair<Int, Int>>>()
val distinctHikePaths = mutableMapOf<Pair<Int, Int>, Int>()

fun main() {
    val input = File("2024/src/aoc24/day10/input.txt")
    val field: MutableList<MutableList<Int>> = generate2DIntField(input)
    val t1 = measureTimeMillis {
        for (row in 0..field.lastIndex) {
            for (col in 0..field[0].lastIndex) {

                val height = field[row][col]
                if (height == 0) {
                    // we found starting point
                    val trailHead = row to col
                    foundPaths[trailHead] = mutableSetOf()
                    distinctHikePaths[trailHead] = 0
                    findWalkingPaths(field, trailHead, trailHead)
                }
            }
        }
    }
    println(
        "Part1: the sum of all trailhead values is ${
            foundPaths.map { it.value.size }.reduce { acc, next -> acc + next }
        }"
    )
    println("Part2: If the trails rating are considered, the sum of all ratings is  ${distinctHikePaths.values.reduce { sum, next -> sum + next }} ")
    println("both parts took $t1 ms") // 6ms
}

fun findWalkingPaths(field: MutableList<MutableList<Int>>, startPos: Pair<Int, Int>, trailhead: Pair<Int, Int>) {
    val curHeight = field[startPos.first][startPos.second]
    val x = startPos.second
    val y = startPos.first

    // we found peak
    if (curHeight == 9) {
        foundPaths[trailhead]!!.add(startPos)
        distinctHikePaths[trailhead] = distinctHikePaths[trailhead]!! + 1
        return
    }

    // top
    if (y - 1 >= 0 && field[y - 1][x] == curHeight + 1) {
        findWalkingPaths(field, y - 1 to x, trailhead)
    }

    // bottom
    if (y + 1 <= field.lastIndex && field[y + 1][x] == curHeight + 1) {
        findWalkingPaths(field, y + 1 to x, trailhead)
    }

    // right
    if (x + 1 <= field[0].lastIndex && field[y][x + 1] == curHeight + 1) {
        findWalkingPaths(field, y to x + 1, trailhead)
    }

    // right
    if (x - 1 >= 0 && field[y][x - 1] == curHeight + 1) {
        findWalkingPaths(field, y to x - 1, trailhead)
    }


}

