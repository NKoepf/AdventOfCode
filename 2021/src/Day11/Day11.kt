
import java.io.File

fun main() {
    val file = File("2021/src/Day11/testInput.txt")
    val field: MutableList<MutableList<Int>> = mutableListOf()

    var lineIndex = 0
    file.forEachLine {
        field.add(mutableListOf())
        it.forEach {
            field[lineIndex].add(it.toString().toInt())
        }
        lineIndex++
    }

    for (i in 0..10) {
        Util.printIntField(field)
        makeStep(field)
        println()
    }
}

fun makeStep(field: MutableList<MutableList<Int>>) {
    var idx = 0
    field.forEach { row ->
        var colIdx = 0
        row.forEach { col ->
            field[idx][colIdx]++
            colIdx++
        }
        idx++
    }
}