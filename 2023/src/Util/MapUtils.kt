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

fun printField(field: MutableList<MutableList<Char>>) {
    field.forEach { line ->
        println(line)
    }
}