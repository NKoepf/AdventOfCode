package aoc24.Day9

import java.io.File
import kotlin.system.measureTimeMillis

fun main() {
    val input = File("2024/src/aoc24/day9/input.txt")
    val line = input.readLines()[0]
    val filesList = createList(line)

    var checksum = 0L
    var checksumWholeFile = 0L
    val t1 = measureTimeMillis {
        val compactedList = compactFiles(filesList)
        checksum = calcChecksum(compactedList)
    }

    val t2 = measureTimeMillis {
        val compactWholeFile = compactWholeFiles2(filesList)
        checksumWholeFile = calcChecksum(compactWholeFile)
    }

    println("Part1: The checksum is $checksum [$t1 ms]") // 4884ms
    println("Part1: By compacting the whole files, the checksum is $checksumWholeFile [$t2 ms]") //24458ms
}

fun createList(line: String): List<Int?> {
    val list = mutableListOf<Int?>()
    var isFile = true
    var fileIndex = 0
    line.forEach { digit ->
        for (i in 0..<digit.digitToInt()) {
            if (isFile) {
                list.add(fileIndex)
            } else {
                list.add(null)
            }
        }
        if (isFile) {
            fileIndex++
        }
        isFile = !isFile
    }

    return list
}

fun compactFiles(files: List<Int?>): List<Int?> {
    val compacted = files.toMutableList()
    compacted.forEachIndexed { index, value ->
        if (value == null) {
            val lastVal = compacted.indexOfLast { it != null }
            if (lastVal < index) {
                return@forEachIndexed
            }
            compacted[index] = compacted[lastVal]
            compacted[lastVal] = null
        }
    }
    return compacted
}

fun compactWholeFiles2(input: List<Int?>): List<Int?> {

    var files = input.toMutableList()
    val group = files.groupingBy { it }.eachCount()
    for (i in files.size - 1 downTo 0) {
        if (files[i] == null) {
            continue
        }

        var lastIndex = files[i]
        val size = group[lastIndex]!!

        // find big enough gap
        var gapSize = 0
        var firstGapIndex = -1
        run find@{
            files.forEachIndexed { index, value ->
                if (value == null) {
                    gapSize++
                    if (firstGapIndex == -1) {
                        firstGapIndex = index
                    }
                    if (gapSize == size) {
                        return@find
                    }
                } else {
                    firstGapIndex = -1
                    gapSize = 0
                }
            }
        }


        // replace gap values with last number
        val indicesToReplace = files.indices.filter { files[it] == lastIndex }
        if (gapSize >= size && indicesToReplace.first() > firstGapIndex) {
            (indicesToReplace).forEach {
                files[it] = null
            }
            for (i1 in 0 until size) {
                files[firstGapIndex + i1] = lastIndex
            }

        }
    }
    return files
}

fun compactWholeFiles(files: List<Int?>, line: String) {
    val digits = mutableListOf<Int>()
    val file = mutableListOf<Int>()
    val spaces = mutableListOf<Int>()
    line.forEachIndexed { index, s ->
        if (index % 2 != 0) {
            spaces.add(s.digitToInt())
        } else {
            file.add(s.digitToInt())
        }
        digits.add(s.digitToInt())
    }

    println(digits)
    println(file)
    println(spaces)

    val parts = digits.chunked(2).map { it.toMutableList() }
    println(parts)

    for (i in parts.size - 1 downTo 0) {
        println(i)
        var moved = false
        parts.forEach { part ->
            println(part)
            if (moved == false) {
                if (part.size > 1 && part[1] >= parts[i][0]) {
                    part.add(part[1] - parts[i][0])
                    part[0] = 0
                    part[1] = parts[i][0]

                    parts[i][0] = 0
                    moved = true
                }

            }
        }
    }


//    val file = mutableListOf<Int>()
//    val spaces = mutableListOf<Int>()
//    line.forEachIndexed { index, s ->
//        if(index % 2 != 0){
//            spaces.add(s.digitToInt())
//        }else{
//            file.add(s.digitToInt())
//        }
//    }
//    val compacted = files.toMutableList()
//    file.reverse().for


}

fun calcChecksum(input: List<Int?>): Long {
    var checksum = 0L
    input.forEachIndexed { index, digit ->
        if (digit != null) {
            checksum += digit * index
        }
    }
    return checksum
}