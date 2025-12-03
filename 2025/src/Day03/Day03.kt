package Day03

import java.io.File

fun main() {
    println("Part 1: Solution: ${Day03.run(2)}")
    println("Part 2: Solution: ${Day03.run(12)}")
}

class Day03 {
    companion object {
        fun run(n: Int): Long {
            return File("2025/src/Day03/testinput.txt").useLines { lines ->
                lines.sumOf { batteryPack ->
                    getHighestJoltage(batteryPack, n)
                }
            }
        }

        fun getHighestJoltage(batteryPack: String, n: Int): Long {
            val ans = StringBuilder()
            var l = 0
            var r = batteryPack.length - n + 1
            repeat(n) {
                val window = batteryPack.substring(l, r)
                val maxIdx: Int = window.indices.maxBy { window[it].code }
                ans.append(window[maxIdx])
                l += maxIdx + 1
                r++
            }
            return ans.toString().toLong()
        }
    }
}