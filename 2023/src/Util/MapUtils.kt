package Util

import java.io.File

fun generate2DCharField(file: File): MutableList<MutableList<Char>> {
    val field: MutableList<MutableList<Char>> = mutableListOf()
    var lineIndex = 0

    file.forEachLine { line ->
        field.add(mutableListOf())
        line.forEach { char ->
            field[lineIndex].add(char)
        }
        lineIndex++
    }
    return field
}

fun generate2DIntField(file: File): MutableList<MutableList<Int>> {
    val field: MutableList<MutableList<Int>> = mutableListOf()
    var lineIndex = 0

    file.forEachLine { line ->
        field.add(mutableListOf())
        line.forEach { char ->
            field[lineIndex].add(char.digitToInt())
        }
        lineIndex++
    }
    return field
}

fun generate2DCharFieldWithStartPos(file: File, startChar: Char): Pair<Pair<Int, Int>, MutableList<MutableList<Char>>> {
    val field: MutableList<MutableList<Char>> = mutableListOf()
    var lineIndex = 0
    var startPos: Pair<Int, Int> = 0 to 0

    file.forEachLine { line ->
        field.add(mutableListOf())
        line.forEachIndexed { col, char ->
            field[lineIndex].add(char)
            if (char == startChar) {
                startPos = (lineIndex to col)
            }
        }
        lineIndex++
    }
    return (startPos to field)
}

fun printField(field: MutableList<MutableList<Char>>) {
    field.forEach { line ->
        println(line)
    }
}

fun printStringField(field: MutableList<MutableList<String>>) {
    field.forEach { line ->
        println(line)
    }
}

fun printIntField(field: MutableList<MutableList<Int>>) {
    field.forEach { line ->
        println(line)
    }
}

fun printLongField(field: MutableList<MutableList<Long>>) {
    field.forEach { line ->
        println(line)
    }
}

fun <T> printLists(lines: List<T>) {
    lines.forEach {
        println(it.toString())
    }
    println()
}

fun transpose(strings: List<String>): List<String> {
    val transposed = mutableListOf<String>()
    for (i in 0..<strings[0].length) {
        var newLine = ""
        for (j in strings.indices) {
            newLine = newLine.plus(strings[j][i])
        }
        transposed.add(newLine)
    }
    return transposed
}

fun genField(field: String): Field {
    val rows = field.split("\n")
    val cols: MutableList<String> = mutableListOf()
    for (i in 0..<rows[0].length) {
        var col = ""
        for (element in rows) {
            col += element[i]
        }
        cols.add(col)
    }
    return Field(rows, cols)
}


data class Field(
    var rows: List<String>,
    var cols: List<String>
)