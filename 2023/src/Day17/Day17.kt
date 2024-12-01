package Day17

import Day10.DirectionEnum
import java.io.File
import java.util.*

var lavaField: MutableList<MutableList<LavaNode>>? = null
var visited: Int = 0
var nodesToVisit: PriorityQueue<LavaNode> = PriorityQueue() { node1, node2 ->
    node1.distance - node2.distance
}
val visitedNodes: MutableSet<Pair<Int, Int>> = mutableSetOf()
fun main() {
    val file = File("2023/src/Day17/testInput.txt")
    Util.printPart1()
    part1(file) //569 too low
}

private fun part1(file: File) {
    println(file.readLines()[0].map { it.digitToInt() })

    var lavaField: MutableList<MutableList<LavaNode>> =
        file.readText().lines().mapIndexed { rIdx, line ->
            line.mapIndexed { cIdx, c ->
                LavaNode(
                    cIdx,
                    rIdx,
                    Int.MAX_VALUE,
                    c.digitToInt()
                )
            }.toMutableList()
        }.toMutableList()

    lavaField[0][0].distance = 0
    println(lavaField)
    lavaField[0][0].direction = DirectionEnum.RIGHT
//    lavaField[1][0].direction = DirectionEnum.DOWN

    nodesToVisit.add(lavaField[0][0])
//    nodesToVisit.add(lavaField[1][0])

    while (nodesToVisit.isNotEmpty()) {
        val node = nodesToVisit.poll()
        if (!node.visited) {
            lavaField = move(lavaField, lavaField[node.yPos][node.xPos])

            val updated = mutableListOf<LavaNode>()
            nodesToVisit.forEach { updated.add(lavaField[it.yPos][it.xPos]) }
            nodesToVisit = PriorityQueue() { node1, node2 ->
                node1.distance - node2.distance
            }
            nodesToVisit.addAll(updated)
        }
    }
    println("Durch in ${lavaField.last().last().distance}")
}

//private fun move(field: List<List<LavaNode>>, node: LavaNode, direction: DirectionEnum, stepCntDir: Int) {
private fun move(field: MutableList<MutableList<LavaNode>>, node: LavaNode): MutableList<MutableList<LavaNode>> {
    field[node.yPos][node.xPos].visited = true
    visited++
    if (node.xPos == 12 && node.yPos == 12) {
        println("reached target: ${node.distance}")
    }
    val neighs = getNeighbors(node, field)
    println("Node ${node.yPos} ${node.xPos}")
    neighs.forEach loop@{ nNode ->
//        if (direction == nNode.first && stepCntDir > 3) { //same direction as before and more than max steps in this direction
//        } else {
        var distanceFromHere = node.distance + nNode.second.cost
        if (nNode.second.distance >= distanceFromHere) {
            if (nNode.first == node.direction) {
                if (nNode.second.stepLength < 4) {
                    nNode.second.stepLength++
                } else {
                    return@loop
                }
            } else {
                nNode.second.direction = node.direction
                nNode.second.stepLength = 1
            }
            nNode.second.distance = distanceFromHere
            field[nNode.second.yPos][nNode.second.xPos].distance = distanceFromHere
            field[nNode.second.yPos][nNode.second.xPos] = nNode.second
            nodesToVisit.add(field[nNode.second.yPos][nNode.second.xPos])
        }
    }
    return field
}

private fun getNeighbors(node: LavaNode, field: List<List<LavaNode>>): List<Pair<DirectionEnum, LavaNode>> {
    val neighs = mutableListOf<Pair<DirectionEnum, LavaNode>>()
    //top
    if (node.yPos - 1 >= 0 && !field[node.yPos - 1][node.xPos].visited) neighs.add(
        Pair(
            DirectionEnum.UP,
            field[node.yPos - 1][node.xPos]
        )
    )
    //bot
    if (node.yPos + 1 < field.size && !field[node.yPos + 1][node.xPos].visited) neighs.add(
        Pair(
            DirectionEnum.DOWN,
            field[node.yPos + 1][node.xPos]
        )
    )
    //left
    if (node.xPos - 1 >= 0 && !field[node.yPos][node.xPos - 1].visited) neighs.add(
        Pair(
            DirectionEnum.LEFT,
            field[node.yPos][node.xPos - 1]
        )
    )
    //right
    if (node.xPos + 1 < field[0].size && !field[node.yPos][node.xPos + 1].visited) neighs.add(
        Pair(
            DirectionEnum.RIGHT,
            field[node.yPos][node.xPos + 1]
        )
    )
    return neighs
}


data class LavaNode(
    val xPos: Int,
    val yPos: Int,
    var distance: Int = Int.MAX_VALUE,
    var cost: Int,
    var prev: LavaNode? = null,
    var visited: Boolean = false,
//    var directions: MutableMap<DirectionEnum, Int> = mutableMapOf(
//        DirectionEnum.UP to Integer.MAX_VALUE,
//        DirectionEnum.DOWN to Integer.MAX_VALUE,
//        DirectionEnum.LEFT to Integer.MAX_VALUE,
//        DirectionEnum.RIGHT to Integer.MAX_VALUE
//    ),
    var direction: DirectionEnum = DirectionEnum.NONE,
    var stepLength: Int = 0,
    var stepLenghts: MutableMap<DirectionEnum, Int> = mutableMapOf()
)