package Day08

import java.io.File
import java.util.*

fun main() {
    Day08.run()
}

class Day08 {
    companion object Companion {

        fun run() {
            val file = File("2025/src/Day08/input.txt")
            val triples = LinkedList<Triple<Long, Long, Long>>()
            file.forEachLine { line ->
                val parts = line.split(",")
                triples.add(Triple(parts[0].toLong(), parts[1].toLong(), parts[2].toLong()))
            }

            var circuits: MutableList<Pair<Triple<Long, Long, Long>, Int>> = mutableListOf() // map triple to networkId

            var distances: MutableMap<Pair<Triple<Long, Long, Long>, Triple<Long, Long, Long>>, Long> = mutableMapOf()
            var allTriples: MutableList<Triple<Long, Long, Long>> = mutableListOf()
            do {
                val currentJunction = triples.pop()
                allTriples.add(currentJunction)
                triples.forEach { t ->
                    val d = (currentJunction.first - t.first) * (currentJunction.first - t.first) +
                            (currentJunction.second - t.second) * (currentJunction.second - t.second) +
                            (currentJunction.third - t.third) * (currentJunction.third - t.third)
                    distances[currentJunction to t] = d
                }
            } while (triples.isNotEmpty())


            val sorted = LinkedList(distances.toList().sortedBy { it.second })
            var networkId = 0

            var last: Pair<Triple<Long, Long, Long>, Triple<Long, Long, Long>> =
                Triple(0L, 0L, 0L) to Triple(0L, 0L, 0L)
            var sumOfLastConnection = 0L
            var iterations = 1
            var sumOfBiggest = 0

            while (true) {
                if (iterations == 1000) {
                    val groups = circuits.groupBy { it.second }
                    sumOfBiggest =
                        groups.values.sortedBy { it.size }.reversed().take(3).map { it.size }.reduce { a, b -> a * b }
                }

                if (circuits.size >= allTriples.size - 1 && circuits.map { it.second }
                        .all { it == circuits[0].second }) {
                    println("found first to connect all with $last")
                    sumOfLastConnection = last.first.first * last.second.first
                    break
                }

                val shortest = sorted.pop()
                last = shortest.first
                val circuitOfFirst = circuits.find { it.first == shortest.first.first }
                val circuitOfSecond = circuits.find { it.first == shortest.first.second }

                // check if there is a circuit containing both -> skip
                if (circuitOfFirst != null && circuitOfSecond != null && circuitOfFirst.second == circuitOfSecond.second) {
                    iterations++
                    continue
                }

                // if they are both in circuits but in different ones, merge them
                if (circuitOfFirst != null && circuitOfSecond != null && circuitOfFirst.second != circuitOfSecond.second) {
                    val otherCircuit = circuitOfSecond.second
                    circuits.forEach { it ->
                        if (it.second == otherCircuit) circuits[circuits.indexOf(it)] =
                            it.first to circuitOfFirst.second
                    }
                    iterations++
                    continue
                }

                // check if there is a circuit containing on of both -> add second one also to circuit
                if (circuitOfFirst != null || circuitOfSecond != null) {
                    if (circuitOfFirst != null) {
                        circuits.add(shortest.first.second to circuitOfFirst.second) // we add con2 of the comparison with circuit id of con1
                    } else {
                        circuits.add(shortest.first.first to circuitOfSecond!!.second) // we add con2 of the comparison with circuit id of con1
                    }
                    iterations++
                    continue
                }

                // else: There is no circuit containing any of both -> create circuit for the connection
                circuits.add(shortest.first.first to networkId)
                circuits.add(shortest.first.second to networkId)
                networkId++
                iterations++
            }

            println("Part 1: done creating the circuits. Sum of 3 biggest is $sumOfBiggest")
            println("Part 2: done creating the circuits. sum of last connecting elements is $sumOfLastConnection")
        }
    }
}
