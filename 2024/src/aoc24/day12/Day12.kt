package aoc24.day12

import Util.generate2DCharField
import Util.printField
import java.io.File

val visited = mutableSetOf<Pair<Int, Int>>()

fun main() {
    val input = File("2024/src/aoc24/day12/testinput.txt")
    val field: MutableList<MutableList<Char>> = generate2DCharField(input)
    printField(field)

    var totalFenceCost = 0L
    var costRegions = 0L

    for (row in 0..field.lastIndex) {
        for (col in 0..field.lastIndex) {
            if (visited.contains(Pair(row, col))) continue
            val plant = field[row][col]
            val startPos = row to col
            var reg = calcRegion(field, startPos, plant, Region())

            println("Region from $startPos and plant $plant : $reg")
            totalFenceCost += reg.area * reg.perimeter
            costRegions += reg.area * reg.corners
        }
    }

    println("Part1: Total Fence Cost: $totalFenceCost")
    println("Part2: Total Fence Cost for sides: $costRegions")
}

data class Region(
    var area: Long = 0L,
    var perimeter: Long = 0L,
    var corners: Long = 0L
)

fun calcRegion(
    field: MutableList<MutableList<Char>>, startPos: Pair<Int, Int>, plant: Char, region: Region
): Region {
    //check for visited and set visited
    if (visited.contains(startPos)) return region
    visited.add(startPos)

    val x = startPos.second
    val y = startPos.first
    region.corners += checkForCorner(field, startPos, plant)

    //up
    if (y - 1 >= 0 && field[y - 1][x] == plant) {
        calcRegion(field, y - 1 to x, plant, region)
    } else {
        region.perimeter++
    }
    //right
    if (x + 1 <= field[0].lastIndex && field[y][x + 1] == plant) {
        calcRegion(field, y to x + 1, plant, region)
    } else {
        region.perimeter++
    }
    //down
    if (y + 1 <= field.lastIndex && field[y + 1][x] == plant) {
        calcRegion(field, y + 1 to x, plant, region)
    } else {
        region.perimeter++
    }
    //left
    if (x - 1 >= 0 && field[y][x - 1] == plant) {
        calcRegion(field, y to x - 1, plant, region)
    } else {
        region.perimeter++
    }
    // nowhere else to go, increase region stats and return
    region.area++

    return region
}

fun checkForCorner(field: MutableList<MutableList<Char>>, startPos: Pair<Int, Int>, plant: Char): Int {
    val x = startPos.second
    val y = startPos.first
    var corners = 0
    var neighbors = 0

//    check if only one neighbors
    if (y - 1 >= 0 && field[y - 1][x] == plant) {
        neighbors++
    }
    if (y + 1 <= field.lastIndex && field[y + 1][x] == plant) {
        neighbors++
    }
    if (x + 1 <= field[0].lastIndex && field[y][x + 1] == plant) {
        neighbors++
    }
    if (x - 1 >= 0 && field[y][x - 1] == plant) {
        neighbors++

    }

    if (neighbors == 1) {
        return 2
    }
    if (neighbors == 0) {
        return 4
    }

    //check for opposite neighs top/bot or left/right
    if (y - 1 >= 0 && y + 1 <= field.lastIndex && field[y - 1][x] == plant && field[y + 1][x] == plant ||
        x - 1 >= 0 && x + 1 <= field[0].lastIndex && field[y][x - 1] == plant && field[y][x + 1] == plant
    ) {
        return 0
    }
    // _|
    if (y - 1 >= 0 && x - 1 >= 0 && field[y - 1][x] == plant && field[y][x - 1] == plant) {
        corners++
        if (field[y - 1][x - 1] != plant) {
            corners++
        }
    }

    // |_
    if (y - 1 >= 0 && x + 1 <= field[0].lastIndex && field[y - 1][x] == plant && field[y][x + 1] == plant) {
        corners++
        if (field[y - 1][x + 1] != plant) {
            corners++
        }
    }
    // -|
    if (y + 1 <= field.lastIndex && x - 1 >= 0 && field[y + 1][x] == plant && field[y][x - 1] == plant) {
        corners++
        if (field[y + 1][x - 1] != plant) {
            corners++
        }
    }

    // |-
    if (y + 1 <= field.lastIndex && x + 1 <= field[0].lastIndex && field[y + 1][x] == plant && field[y][x + 1] == plant) {
        corners++
        if (field[y + 1][x + 1] != plant) {
            corners++
        }
    }
    return corners
}