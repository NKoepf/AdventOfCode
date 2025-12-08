package Day05

import java.io.File

fun main() {
    Day05.run()
}

class Day05 {
    companion object {
        var freshnes = mutableListOf<Pair<Long, Long>>()
        var ranges = mutableListOf<Pair<Long, Long>>()

        fun run() {
            val file = File("2025/src/Day05/input.txt")
            var rangesDone = false
            var freshItems = 0L

            file.forEachLine { line ->
                if (line.isEmpty()) {
                    rangesDone = true
                    freshnes.sortBy { it.first }
                    println("sorted")
                    mergeAndGetAllFreshIds()
                } else {
                    if (!rangesDone) {
                        addRange(line)
                    } else {
                        if (checkForFresh(line.toLong())) {
                            freshItems++
                        }
                    }
                }
            }
            println("Part 1: there are $freshItems fresh items")
        }

        fun addRange(line: String) {
            val parts = line.split("-")
            val range = parts[0].toLong() to parts[1].toLong()
            ranges.add(range)
        }

        fun mergeAndGetAllFreshIds() {
            val mergedRanges = mutableListOf<Pair<Long, Long>>()
            ranges.sortBy { it.first }

            mergedRanges.add(ranges.first())

            ranges.drop(1).forEach { range ->

                val last = mergedRanges.last()
                // compare range first with last mergedRanges second
                if (range.first > last.second) {
                    // new new range starts after the last range ends -> just take it
                    mergedRanges.add(range)
                } else if (range.first <= last.second && range.second <= last.second) {
                    // new range is completely inside of old range -> skip
//                    println("range $range is inside $last")
                } else if (range.first <= last.second && range.second >= last.second) {
                    // new range is overlapping wit old range -> merge them
                    mergedRanges.removeLast()
                    mergedRanges.add(last.first to range.second)
                } else {
                    println("this should not happen")
                }

            }
            println("done merging ranges")
            var sum = 0L
            mergedRanges.forEach { r ->
                sum += r.second - r.first + 1
            }
            println("Part 2: sum of all fresh ids is $sum")
            freshnes = mergedRanges
        }

        fun checkForFresh(id: Long): Boolean {
            var ranges = freshnes.filter { it.first <= id }
            if (ranges.isEmpty()) return false

            ranges = ranges.filter { it.second >= id }
            if (ranges.isEmpty()) return false
            return true
        }
    }
}