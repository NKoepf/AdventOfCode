package Day6

import java.io.File

fun main() {
    part1()
    part2()
}

fun part1() {
    Util.printPart1()
    val inputs = mutableListOf<List<Long>>()
    File("2023/src/Day6/input.txt").forEachLine { line ->
        inputs.add(line.split(":[ ]{1,}".toRegex())[1].split("[ ]{1,}".toRegex()).map { s -> s.toLong() }.toList())
    }

    var totalProduct = 1

    // for each race
    for (i in 0..<inputs[0].size) {
        val timeLimit = inputs[0][i]
        val distanceToBeat = inputs[1][i]
        var winCount = checkSpeedTimeCombs(timeLimit, distanceToBeat)
        winCount = winCount * 2
        if (timeLimit % 2 == 0L) winCount--

        if (totalProduct == 0) totalProduct = winCount else totalProduct *= winCount
        println("TL: $timeLimit DtB: $distanceToBeat Wins: $winCount")
    }
    println("Total sum of all wins $totalProduct")
}

fun part2() {
    Util.printPart2()
    val lines = File("2023/src/Day6/input.txt").readLines()
    val distanceToBeat = lines[1].substringAfter(":").replace(" ", "").toLong()
    val timeLimit = lines[0].substringAfter(":").replace(" ", "").toLong()

    var winCount = checkSpeedTimeCombs(timeLimit, distanceToBeat)
    winCount *= 2
    if (timeLimit % 2 == 0L) winCount--

    println("TL: $timeLimit DtB: $distanceToBeat Wins: $winCount")
}

fun checkSpeedTimeCombs(timeLimit: Long, distanceToBeat: Long): Int {
    val startTime = timeLimit / 2
    var winCount = 0
    var lastResult = distanceToBeat + 1
    var idx = 0
    while (lastResult > distanceToBeat) {
        val speed: Long = (startTime - idx)
        val time: Long = (timeLimit - startTime + idx)
        val traveledDistance: Long = speed * time
        if (traveledDistance > distanceToBeat) {
            winCount++
        }
        lastResult = traveledDistance
        idx++
    }
    return winCount
}