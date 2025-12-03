package Day03

import java.io.File

fun main() {
//    Day03.run()
    Day03.run2()
}

class Day03 {
    companion object {
        fun run2(){
            val banks = File("2025/src/Day03/testinput.txt").readLines()
            banks.forEach { bank ->
                val numbers: List<Int> = bank.map { it.toString().toInt() }
                // find highest number
                val highest = numbers.sorted().distinct().reversed().take(2)
                val indexOfHigher = numbers.indexOfFirst { it == highest.max() }

                var indexOfLower = -1
                if (indexOfHigher == numbers.lastIndex) {
                    // if highest is at the end, find first occurrence of lower
                    indexOfLower = numbers.indexOfFirst { it == highest.min() }
                } else {
                    // else look only after the higher index
                    val remainders = numbers.drop(indexOfHigher+1)
                    indexOfLower = remainders.indexOfFirst { it == remainders.max() } + indexOfHigher + 1
                }

                val higherValue = bank[indexOfHigher]
                val lowerValue = bank[indexOfLower]

                var result = 0
                if (indexOfHigher < indexOfLower) {
//                    println("$indexOfHigher, $indexOfLower")
                    result = (higherValue.toString() + lowerValue.toString()).toInt()
                } else {
//                    println("$indexOfLower, $indexOfHigher")
                    result = (lowerValue.toString() + higherValue.toString()).toInt()
                }
//                println(result)

            }

        }

        fun run() {
            val banks = File("2025/src/Day03/input.txt").readLines()
            var totalOfBanks = 0L

            banks.forEach { bank ->
                val numbers: List<Int> = bank.map { it.toString().toInt() }
                // find highest number
                val highest = numbers.sorted().distinct().reversed().take(2)
                val indexOfHigher = numbers.indexOfFirst { it == highest.max() }

                var indexOfLower = -1
                if (indexOfHigher == numbers.lastIndex) {
                    // if highest is at the end, find first occurrence of lower
                    indexOfLower = numbers.indexOfFirst { it == highest.min() }
                } else {
                    // else look only after the higher index
                    val remainders = numbers.drop(indexOfHigher+1)
                    indexOfLower = remainders.indexOfFirst { it == remainders.max() } + indexOfHigher + 1
                }

                val higherValue = bank[indexOfHigher]
                val lowerValue = bank[indexOfLower]

                var result = 0
                if (indexOfHigher < indexOfLower) {
//                    println("$indexOfHigher, $indexOfLower")
                    result = (higherValue.toString() + lowerValue.toString()).toInt()
                } else {
//                    println("$indexOfLower, $indexOfHigher")
                    result = (lowerValue.toString() + higherValue.toString()).toInt()
                }
//                println(result)
                totalOfBanks += result
            }

            println("Total: $totalOfBanks")
        }
    }
}