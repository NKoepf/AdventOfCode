package aoc24.Day13

import java.io.File
import kotlin.system.measureTimeMillis

val costA = 3
val costB = 1
var visitingCost = mutableMapOf<Pair<Long, Long>, Long>()


fun main() {
    val input = File("2024/src/aoc24/day13/input.txt")

    var blockPart = 0
    val blocks = mutableListOf(ClawBlock())
    val blocksPart2 = mutableListOf(ClawBlock())

    input.forEachLine { line ->
        if (line == "") {
            blockPart = 0
            blocks.add(ClawBlock())
            blocksPart2.add(ClawBlock())
            return@forEachLine
        }

        if (blockPart == 0) {
            val parts = line.replace(",", "").split(" ")
            blocks.last().aX = parts[2].substring(2).toLong()
            blocks.last().aY = parts[3].substring(2).toLong()

            blocksPart2.last().aX = parts[2].substring(2).toLong()
            blocksPart2.last().aY = parts[3].substring(2).toLong()
            blockPart++
        } else if (blockPart == 1) {
            val parts = line.replace(",", "").split(" ")
            blocks.last().bX = parts[2].substring(2).toLong()
            blocks.last().bY = parts[3].substring(2).toLong()

            blocksPart2.last().bX = parts[2].substring(2).toLong()
            blocksPart2.last().bY = parts[3].substring(2).toLong()
            blockPart++
        } else if (blockPart == 2) {
            val parts = line.replace(",", "").split(" ")

            blocks.last().prizeX = parts[1].substring(2).toLong()
            blocks.last().prizeY = parts[2].substring(2).toLong()

            blocksPart2.last().prizeX = parts[1].substring(2).toLong() + 10000000000000
            blocksPart2.last().prizeY = parts[2].substring(2).toLong() + 10000000000000
            blockPart++
        }
    }

    var totalTokenCost = 0L

    val t1 = measureTimeMillis {
        //  Part 1 Backtracking stuff
        blocks.forEach { block ->
            visitingCost = mutableMapOf<Pair<Long, Long>, Long>()
            calcCost(block, block.prizeX to block.prizeY, 0L)

            if (visitingCost.contains(0L to 0L)) {
                totalTokenCost += visitingCost[0L to 0L]!!
            }
        }
    }

    println("Part1: The minimum required tokens to win all claws is $totalTokenCost - Backtracking [$t1 ms]")

    totalTokenCost = 0L

    val t2 = measureTimeMillis {
        blocksPart2.forEach { block ->

            var b = (block.prizeX * block.aY - block.prizeY * block.aX) / (block.bX * block.aY - block.bY * block.aX)
            var a = (block.prizeX * block.bY - block.prizeY * block.bX) / (block.bY * block.aX - block.bX * block.aY)

            if (
                block.aX * a + block.bX * b == block.prizeX &&
                block.aY * a + block.bY * b == block.prizeY
            ) {
                totalTokenCost += a.toLong() * costA + b.toLong() * costB
            }
        }
    }
    println("Part2: Total cost for new targets is $totalTokenCost - Mathematically [$t2 ms]")

}

fun calcCost(block: ClawBlock, currentPos: Pair<Long, Long>, cost: Long): Boolean {

    // if we already reached this position, for cheaper cost -> return false
    if (visitingCost.getOrPut(currentPos.first to currentPos.second) { Long.MAX_VALUE } > cost) {
        visitingCost[currentPos.first to currentPos.second] = cost
        if (currentPos.first == 0L && currentPos.second == 0L) {
            // we found starting point -> right solution return cost till here
            return true
        }
        if (currentPos.first < 0 || currentPos.second < 0) {
            return false
        }

        // check other fields
        // try button b
        if (calcCost(
                block,
                ((currentPos.first - block.bX) to (currentPos.second - block.bY)),
                cost + costB
            )
        ) return true
        if (calcCost(
                block,
                ((currentPos.first - block.aX) to (currentPos.second - block.aY)),
                cost + costA
            )
        ) return true
        return false
    } else {
        return false
    }


//    if(currentPos.first < 0 || currentPos.second < 0) {
//        // we are out of bounds -> wrong solution
//        println(currentPos)
//        return 0L
//    }

//    var newCost = 0L
//    // push cheaper b Button
//    newCost = calcCost(block, ((currentPos.first - block.bX) to (currentPos.second - block.bY)) , cost + costB)
//
//    if(newCost == 0L){
//        // pressing b lead to wrong solution -> try a
//        newCost = calcCost(block, ((currentPos.first - block.aX) to (currentPos.second - block.aY)) , cost + costA)
//    }
//
//    return newCost

}


data class ClawBlock(
    var aX: Long = 0,
    var aY: Long = 0,
    var bX: Long = 0,
    var bY: Long = 0,
    var prizeX: Long = 0,
    var prizeY: Long = 0
)