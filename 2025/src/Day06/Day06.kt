package Day06

import java.io.File

fun main() {
    Day06.run()
}

class Day06 {
    companion object {
        fun run() {
            val file = File("2025/src/Day06/input.txt")

            var overallSum = 0L
            var part2Sum = 0L
            val terms = mutableListOf<MutableList<Long>>()
            var init = true

            var linesPart2 = file.readLines()
            // get indexes of operands -> always first element in expression
            val indexes = mutableListOf<Int>()
            linesPart2.last().forEachIndexed { i, c ->
                if (c != ' ') {
                    indexes.add(i)
                }
            }
            indexes.add(linesPart2.first().length + 1)
            val columns = mutableListOf<MutableList<String>>()
            var start = 0
            indexes.drop(1).forEach { i ->
                columns.add(mutableListOf())
                linesPart2.dropLast(1).forEach { line ->
                    columns.last().add(line.substring(start, i - 1))
                }
                start = i
            }

            val operands = linesPart2.last().split(" ").filter { it != " " }.filter { it != "" }
            columns.forEachIndexed { i, col ->
                part2Sum += calcColumn(col, operands[i])
            }


            file.forEachLine { line ->
                val parts = line.trim().split(" ").filter { it != "" }

                if (init) {
                    parts.forEach { terms.add(mutableListOf()) }
                    init = false
                }

                parts.forEachIndexed { i, element ->
                    if (element == "+" || element == "*") {
                        if (element == "+") {
                            overallSum += terms[i].reduce { pre, next -> pre + next }
                        } else {
                            overallSum += terms[i].reduce { pre, next -> pre * next }
                        }
                    } else {
                        val num = element.toLong()
                        terms[i].add(num)
                    }
                }
            }
            println("Part 1: Sum of all terms $overallSum")
            println("Part 2: Sum of all terms $part2Sum")
        }

        fun calcColumn(column: List<String>, operator: String): Long {
            var index = column.first().lastIndex
            var sum = 0L
            var first = true
            do {
                val values = column.map { it[index] }
                var str = ""
                values.forEach { c -> if (c != ' ') str += c }
                val num = str.toLong()
                if (first) {
                    sum = num
                    first = false
                } else {
                    if (operator == "*") {
                        sum *= num
                    } else {
                        sum += num
                    }
                }
                index--

            } while (index >= 0)
            return sum
        }
    }
}