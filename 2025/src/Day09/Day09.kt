package Day09

import java.io.File
import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    Day09.run()
}

class Day09 {
    companion object Companion {
        val greenTiles = mutableSetOf<Pair<Int, Int>>()
        var edges = mutableListOf<Pair<Int, Int>>()

        fun run() {
            val file = File("2025/src/Day09/input.txt")
            var redTiles = LinkedList<Pair<Int, Int>>()
            var redCopy = LinkedList<Pair<Int, Int>>()

            val lines = file.readLines()
            var height = 0
            var width = 0

            lines.forEach { l ->
                val coords = l.split(",").map { it.toInt() }
                if (coords[0] > height) {
                    height = coords[0]
                }
                if (coords[1] > width) {
                    width = coords[1]
                }
                redTiles.add(Pair(coords[0], coords[1]))
                redCopy.add(Pair(coords[0], coords[1]))
            }

            //generate edges
            redCopy.addLast(redCopy.first())

            redCopy.forEachIndexed { i, tile ->
                if (i != redCopy.lastIndex) {
                    val otherTile = redCopy[i + 1]
                    for (row in min(tile.second, otherTile.second)..max(tile.second, otherTile.second)) {
                        for (col in min(tile.first, otherTile.first)..max(tile.first, otherTile.first)) {
                            edges.add(row to col)
                        }
                    }
                }
            }
            edges = edges.distinct().toMutableList()

//            val topLeft = edges.sortedWith(compareBy<Pair<Int, Int>> { it.second }.thenBy { it.first }).first()
//            var start = topLeft.first + 1 to topLeft.second + 1
//            var lastEdge = false
//            all@ for (row in 0..width) {
//                lastEdge = false
//                for (col in 0..height) {
//
//                    if (lastEdge && !edges.contains(row to col)) {
//                        // found inside
//                        println("found inside $row, $col")
//                        start = Pair(row, col)
//                        break@all
//                    }
//
//                    if (edges.contains(row to col)) {
//                        lastEdge = true
//                    } else {
//                        lastEdge = false
//                    }
//                }
//            }

            // flood fill all inside 
//            checkAround(start)

            var maxSize = 0L
            var maxP2Size = 0L
            val rectangles = mutableListOf<Triple<Pair<Int, Int>, Pair<Int, Int>, Long>>()

            do {
                val tile = redTiles.pop()

                redTiles.forEach { otherTile ->
                    val area =
                        (abs(tile.first - otherTile.first) + 1).toLong() * (abs(tile.second - otherTile.second) + 1).toLong()
                    rectangles.add(Triple(tile, otherTile, area))

                    if (area > maxSize) {
                        maxSize = area
                    }
                }
            } while (redTiles.isNotEmpty())
            println("Part 1: max area between red tiles: $maxSize")

            // part2 check for all rectangles if completely inside
            val sorted: List<Triple<Pair<Int, Int>, Pair<Int, Int>, Long>> = rectangles.sortedBy { it.third }.reversed()
            var index = 0


            do {
                val rect = sorted[index++]
                var found = true
                found = rectangleInside(rect)
                if (found) {
                    maxP2Size = rect.third
                    break
                }
            } while (!found)

            println("Part 2: max rect inside edges: $maxP2Size")
        }

        // other variant: check with offset of 1 if the inner outline of the rectangle ever touches the edges. If so, it is invalid
        fun rectangleInside(rect: Triple<Pair<Int, Int>, Pair<Int, Int>, Long>): Boolean {
            val r = rect.first to rect.second

            // check top side
            var y = min(r.first.second, r.second.second) + 1
            if (edges.filter { it.first == y }.any {
                    it.second > min(r.first.first, r.second.first) && it.second < max(
                        r.first.first,
                        r.second.first
                    )
                }) {
                return false
            }

            // check bot side
            y = max(r.first.second, r.second.second) - 1
            if (edges.filter { it.first == y }.any {
                    it.second > min(r.first.first, r.second.first) && it.second < max(
                        r.first.first,
                        r.second.first
                    )
                }) {
                return false
            }

            // check left side
            var x = min(r.first.first, r.second.first) + 1
            if (edges.filter { it.second == x }.any {
                    it.first > min(r.first.second, r.second.second) && it.first < max(
                        r.first.second,
                        r.second.second
                    )
                }) {
                return false
            }

            // check right side
            x = max(r.first.first, r.second.first) - 1
            if (edges.filter { it.second == x }.any {
                    it.first > min(r.first.second, r.second.second) && it.first < max(
                        r.first.second,
                        r.second.second
                    )
                }) {
                return false
            }
            println("found one! with: ${rect.third} ")
            return true
        }
    }
}