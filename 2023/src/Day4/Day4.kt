package Day4

import java.io.File

fun main() {
    Util.printPart2()
    val file = File("2023/src/Day4/Input.txt")
    var pointsOverall = 0
    var scratchCards = IntArray(213) { 1 }
    println(scratchCards.contentToString())

    var gameIndex = 0
    file.forEachLine {
        val parts = it.substringAfter(": ").trim().split(" | ")
        val wins = parts[0].split("[ ]{1,2}".toRegex())
        val ownNumbers = parts[1].split("[ ]{1,2}".toRegex())

        println(wins)
        println(ownNumbers)
        println()

        var pointOfCard = 0
        var numberOfWins = 0
        ownNumbers.forEach { num ->
            if (wins.contains(num)) {
                numberOfWins++
                if (pointOfCard == 0) pointOfCard = 1
                else {
                    pointOfCard *= 2
                }
            }
        }
        pointsOverall += pointOfCard

        for (instance in 1..scratchCards[gameIndex]) {
            for (i in 0..<numberOfWins) {
                scratchCards[gameIndex + 1 + i]++
            }
        }
        gameIndex++
        println(scratchCards.contentToString())
    }
    println("The overall winning points are $pointsOverall")
    println("The overall number of scratchcards is ${scratchCards.sum()}")
}