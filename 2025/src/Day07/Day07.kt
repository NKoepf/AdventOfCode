package Day07

import java.io.File

fun main() {
    Day07.run()
}

class Day07 {
    companion object Companion {

        fun run() {
            val file = File("2025/src/Day07/input.txt")

            var lines = file.readLines()
            val beamIndexes = mutableListOf<Long>()
            var splittersFound = 0L

            lines.first().forEach { c ->
                if (c == 'S') {
                    beamIndexes.add(1)
                } else {
                    beamIndexes.add(0)
                }
            }
            lines = lines.drop(1)
            lines.forEach { line ->
                line.forEachIndexed { i, c ->
                    if (c == '^' && beamIndexes[i] > 0) {
                        // take current beam value of current location and add it to left and right position + remove from current index
                        val curr = beamIndexes[i]
                        beamIndexes[i - 1] = beamIndexes[i - 1] + curr
                        beamIndexes[i + 1] = beamIndexes[i + 1] + curr
                        beamIndexes[i] = 0
                        splittersFound++
                    }
                }
            }
            println("part1: There are $splittersFound active splitters")
            println("part2: There are ${beamIndexes.reduce { a, b -> a + b }} possible paths")
        }
    }
}