package Day14

import java.io.File

fun main() {
    val file = File("2023/src/Day14/input.txt")
    Util.printPart1()
    part1(file)
    Util.printPart2()
    part2(file)
}

fun part1(file: File) {
    val field = Util.genField(file.readText())
    val lines = tiltField(field.cols, true)
    calcWeightOfField(lines)
    println("The sum of all weights is ${calcWeightOfField(lines)}")
}

fun part2(file: File) {
    val field = Util.genField(file.readText())
    var lines = field.cols
    Util.printLists(field.rows)

    val patterns = mutableSetOf(field.cols)
    var repeated = false
    var loopCount = 0

    while (!repeated) {
        lines = tiltInCircle(lines)
        if (patterns.contains(lines)) {
            repeated = true
        } else {
            patterns.add(lines)
            loopCount++
        }
    }

    println("found pattern again after $loopCount iterations")
    for (i in 0..1000_000_000 % loopCount) {
        lines = tiltInCircle(lines)
    }

    Util.printLists(Util.transpose(lines))
    println("The sum of all weights is ${calcWeightOfField(lines)}")
}

fun calcWeightOfField(input: List<String>): Int {
    var sum = 0
    input.forEach { line ->
        line.forEachIndexed { index, c ->
            if (c == 'O') {
                sum += (line.length - index)
            }
        }
    }
    return sum
}

fun tiltInCircle(input: List<String>): List<String> {
    var lines = input
    lines = tiltField(lines, true) // tilt north
    lines = Util.transpose(lines) // switch cols to rows

    lines = tiltField(lines, true) // tilt west
    lines = Util.transpose(lines) // switch rows to cols

    lines = tiltField(lines, false) // tilt south
    lines = Util.transpose(lines) // switch cols to rows

    lines = tiltField(lines, false) // tilt east
    lines = Util.transpose(lines) // switch rows to cols

    return lines
}

fun tiltField(lines: List<String>, left: Boolean): List<String> {
    val shifted = mutableListOf<String>()
    lines.forEach { line ->
        var shiftedLine = ""
        if (left) shiftedLine = moveLeft(line)
        else shiftedLine = moveRight(line)
        shifted.add(shiftedLine.trim())
    }
    return shifted
}

private fun moveLeft(line: String): String {
    val parts = line.split("#")
    val sortedLine = mutableListOf<String>()
    parts.forEachIndexed { i, part ->
        if (part.isNotEmpty()) {
            val sorted = part.toList().sorted().reversed().map { c -> c.toString() }
            sortedLine.addAll(sorted)
        }
        if (i != parts.size - 1) sortedLine.add("#")
    }
    return sortedLine.joinToString().replace(", ", "")
}

private fun moveRight(line: String): String {
    val parts = line.split("#")
    val sortedLine = mutableListOf<String>()
    parts.forEachIndexed { i, part ->
        if (part.isNotEmpty()) {
            val sorted = part.toList().sorted().map { c -> c.toString() }
            sortedLine.addAll(sorted)
        }
        if (i != parts.size - 1) sortedLine.add("#")
    }
    return sortedLine.joinToString().replace(", ", "")
}