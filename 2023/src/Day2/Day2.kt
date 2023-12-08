package Day2

import java.io.File
import kotlin.math.max

fun main() {
    val file = File("2023/src/Day2/testInput.txt")
    Util.printPart2()
    var sum = 0
    var power = 0

    file.forEachLine {
        println(it)
        val line = it.replace("Game ", "")
        val gameId = line.substringBefore(":").toInt()
        val dices = line.substringAfter(": ").split("[,;]".toRegex())
        val diceMap = mutableMapOf<String, Int>()

        dices.forEach { dice ->
            val key = dice.trim().substringAfter(" ")
            val value = dice.trim().substringBefore(" ").toInt()
            diceMap[key] = max(value, diceMap.getOrDefault(key, 0))
        }

        println("Draws: $dices")
        println("Max per color: ${diceMap}\n")
        power += (diceMap["red"]!! * diceMap["green"]!! * diceMap["blue"]!!)
        if (diceMap["red"]!! <= 12 && diceMap["green"]!! <= 13 && diceMap["blue"]!! <= 14) sum += gameId
    }

    println("Sum of all working round indices is $sum")
    println("The the sum of the power of working sets $power")
}