package Day13

import Util.Field
import java.io.File

var verticalSum = 0
var horizontalSum = 0

fun main() {
    val file = File("2023/src/Day13/input.txt")
    Util.printPart1()
    part1(file)
    Util.printPart2()
    part2(file)
}

fun part1(file: File) {
    val parts = file.readText().split("\n\n")
    verticalSum = 0
    horizontalSum = 0

    parts.forEach { part ->
        val field = Util.genField(part)
        findReflections(field, 0)
    }
    println("there are $horizontalSum horizontal reflections and $verticalSum vertical reflections. Sum ${verticalSum + 100 * horizontalSum}")
}

fun part2(file: File) {
    val parts = file.readText().split("\n\n")
    verticalSum = 0
    horizontalSum = 0

    parts.forEach { part ->
        val field = Util.genField(part)
        val hoRef = findReflection(field.rows, 0).first
        val veRef = findReflection(field.cols, 0).first
        findSmudgedReflections(field, 1, if (hoRef != null) hoRef else -1, if (veRef != null) veRef else -1)
    }
    println("After tolerating one smudge, there are $horizontalSum horizontal reflections and $verticalSum vertical reflections. Sum ${verticalSum + 100 * horizontalSum}")
}


fun findReflections(field: Field, tolerance: Int) {
    val hoRef = findReflection(field.rows, tolerance)
    if (hoRef.first != null) {
        horizontalSum += hoRef.first!!
    } else {
        val veRef = findReflection(field.cols, tolerance)
        if (veRef.first != null) verticalSum += veRef.first!!

    }
}


fun findSmudgedReflections(field: Field, tolerance: Int, origHo: Int, origVert: Int) {
    val hoRef = findSmudgedReflection(field.rows, tolerance, origHo)
    if (hoRef != null) {
        horizontalSum += hoRef
    } else {
        val veRef = findSmudgedReflection(field.cols, tolerance, origVert)
        if (veRef != null) verticalSum += veRef
    }
}

fun findReflection(strings: List<String>, tolerance: Int): Pair<Int?, Int> {

    for (i in 1..<strings.size) {
        var diff = getDiffCount(strings[i], strings[i - 1])

        if (tolerance - diff >= 0)
            check@ for (j in 1..i) {
                val prev = i - 1 - j
                val next = i + j
                if (next == strings.size || prev == -1) return Pair(i, tolerance - diff)
                diff += getDiffCount(strings[next], strings[prev])

                if (tolerance - diff < 0) break@check
            }
    }

    return Pair(null, tolerance)
}

fun findSmudgedReflection(strings: List<String>, tolerance: Int, except: Int): Int? {

    for (i in 1..<strings.size) {
        if (i != except) {
            var diff = getDiffCount(strings[i], strings[i - 1])
            if (tolerance - diff >= 0)
                check@ for (j in 1..i) {
                    val prev = i - 1 - j
                    val next = i + j
                    if (next == strings.size || prev == -1) return i
                    diff += getDiffCount(strings[next], strings[prev])

                    if (tolerance - diff < 0) break@check
                }
        }
    }

    return null
}

fun getDiffCount(line1: String, line2: String): Int {
    var diff = 0
    for (i in line1.indices) {
        if (line1[i] != line2[i]) {
            diff++
        }
    }
    return diff
}
