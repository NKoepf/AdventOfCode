package Day17

import Day10.DirectionEnum
import java.io.File

// key is Combination of direction, from which node is reached + node position in map
// value is total heat loss on this node for this direction
private val valueMap: MutableMap<Position, Int> = mutableMapOf()

fun main() {
    val file = File("2023/src/Day17/testInput.txt")
    part1(file)
}


private fun part1(file: File) {
    val city = Util.generate2DCharField(file)
    Util.printField(city)

    val start = (0 to 0)

}

private data class Step(
    val pos: Pair<Int, Int>,
    val totalHeatLoss: Int,
    val direction: DirectionEnum,
    val straight: Int,
    val afterTurn: Int
)

private data class Position(
    var pos: Pair<Int, Int>,
    var dir: DirectionEnum
)