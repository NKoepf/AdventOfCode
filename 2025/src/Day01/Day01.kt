package Day01

import java.io.File
import kotlin.math.abs

fun main() {
    Day01.run()
}

class Day01 {
    companion object {
        fun run() {
            val input = File("2025/src/Day01/input.txt")
            val commands = input.readLines().map { line ->
                var num = line.replace("R", "").replace("L", "").toInt()
                if (line.contains("L")) num *= -1
                num
            }
            val mod = 100
            var currVal = 50
            var zeros = 0
            var rotations = 0

            commands.forEach { c ->
                val sum = currVal + c
                if (currVal > 0 && sum < 0 || currVal < 0 && sum > 0 || sum == 0) {
                    rotations++
                }
                rotations += abs((sum / mod))
                currVal = (currVal + c) % mod
                println(currVal)
                if (currVal == 0) zeros++
            }

            println("Part1: The lock landed $zeros times on zero")
            println("Part2: The lock rotated $rotations over zero")
        }

    }
}