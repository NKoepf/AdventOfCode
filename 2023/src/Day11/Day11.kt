package Day11

import java.io.File
import kotlin.math.abs


fun main() {
    val file = File("2023/src/Day11/input.txt")
    Util.printPart1() // 10077850
    doIt(file, 1)
    Util.printPart2() // 504715068438
    doIt(file, 1000000 - 1)
}

fun doIt(file: File, growthFactor: Long) {
    val originalField = createField(file)
    Util.printStringField(originalField)

    val starMap = getStarMap(originalField)
    println(starMap)

    val populatedRows = starMap.map { it.value.first }.distinct().sorted()
    val populatedCols = starMap.map { it.value.second }.distinct().sorted()

    for (i in originalField.size - 1 downTo 0L) {
        if (!populatedRows.contains(i)) {
            println("empty row on $i")
            starMap.filter { it.value.first > i }.toList().forEach {
                starMap[it.first] = Pair(starMap[it.first]!!.first + growthFactor, starMap[it.first]!!.second)
            }
        }
    }

    for (j in originalField[0].size - 1 downTo 0L) {
        if (!populatedCols.contains(j)) {
            println("empty col on $j")
            starMap.filter { it.value.second > j }.toList().forEach {
                starMap[it.first] = Pair(starMap[it.first]!!.first, starMap[it.first]!!.second + growthFactor)
            }
        }
    }
    println(starMap)
    var count = 0L
    var sum = 0L
    starMap.forEach { star ->
        for (i in star.key..<starMap.keys.size) {
            val distance =
                abs(star.value.first - starMap[i + 1]!!.first) + abs(star.value.second - starMap[i + 1]!!.second)
            sum += distance
            count++
        }
    }
    println("There are $count pairs with a sum of distances of ${sum}")
}

fun createField(file: File): MutableList<MutableList<String>> {

    val field: MutableList<MutableList<String>> = mutableListOf()
    var lineIndex = 0
    var count = 1
    file.forEachLine { line ->
        field.add(mutableListOf())
        line.forEachIndexed { cIndex, char ->
            if (char != '.') {
                field[lineIndex].add(count.toString())
                count++
            } else {
                field[lineIndex].add(".")
            }
        }
        lineIndex++
    }
    return field
}

fun getStarMap(field: MutableList<MutableList<String>>): MutableMap<Long, Pair<Long, Long>> {
    val starMap: MutableMap<Long, Pair<Long, Long>> = mutableMapOf()
    field.forEachIndexed { rIdx, row ->
        row.forEachIndexed { cIdx, col ->
            if (col != ".") {
                starMap.put(col.toLong(), Pair(rIdx.toLong(), cIdx.toLong()))
            }
        }
    }
    return starMap
}