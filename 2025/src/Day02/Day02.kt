package Day02

import java.io.File

fun main() {
    Day02.run()
}

class Day02 {
    companion object {
        fun run() {
            val input = File("2025/src/Day02/input.txt").readText()
            var p1Sum = 0L
            var p2Sum = 0L
            val invalids = mutableSetOf<Long>()
            val ranges = input.split(",")

            ranges.forEach { range ->
                val limits = range.split("-")

                for (number in limits[0].toLong()..limits[1].toLong()) {
                    val str = number.toString()
                    // part 1
                    if (str.length % 2 == 0) {
                        val part1 = str.take(str.length / 2)
                        val part2 = str.takeLast(str.length / 2)

                        if (part1 == part2) {
                            p1Sum += number
                            p2Sum += number
                            invalids.add(number)
                        }
                    }
                    // part 2
                    var seq = ""
                    for (strIndex in 0..str.length) {
                        val currChar = str[strIndex]
                        seq += currChar
                        val regex = Regex(seq)
                        val matches = regex.findAll(str).map { it.value }.toList()
                        if (seq.length > str.length / 2) break
                        if (invalids.contains(number)) break
                        if (matches.size * seq.length == str.length) {
                            p2Sum += number
                            break
                        }
                    }
                }
            }
            println("Part1: The sum of all valid codes is $p1Sum")
            println("Part2: The sum of all valid p2 codes is $p2Sum")
        }
    }
}