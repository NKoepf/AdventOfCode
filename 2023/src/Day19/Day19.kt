package Day19

import java.io.File

var acceptedPermutations: Long = 0

fun main() {
    val file = File("2023/src/Day19/input.txt")
    Util.printPart1()
    part1(file)

    Util.printPart2()
    part2(file)
}

private fun part1(file: File) {
    val workMap: MutableMap<String, Workflow> = mutableMapOf()
    val accepted: MutableList<PartRatings> = mutableListOf()
    val rejected: MutableList<PartRatings> = mutableListOf()

    val blocks = file.readText().split("\n\n")
    val workflows = getWorkflows(blocks[0])
    val parts = getPartRatings(blocks[1])

    workflows.forEach { workMap[it.nam] = it }
    parts.forEach { part ->
        var next = "in"
        while (next != "A" && next != "R") {
            var found = false

            run loop@{
                workMap[next]!!.mappings.forEach { m ->
                    val split = m.split(":")
                    if (split[0].contains("<")) {
                        if (split[0][0] == 'x' && part.x < split[0].substringAfter("<").toInt()) {
                            next = split[1]
                            found = true
                            return@loop
                        }
                        if (split[0][0] == 'm' && part.m < split[0].substringAfter("<").toInt()) {
                            next = split[1]
                            found = true
                            return@loop
                        }
                        if (split[0][0] == 'a' && part.a < split[0].substringAfter("<").toInt()) {
                            next = split[1]
                            found = true
                            return@loop
                        }
                        if (split[0][0] == 's' && part.s < split[0].substringAfter("<").toInt()) {
                            next = split[1]
                            found = true
                            return@loop
                        }
                    } else {
                        if (split[0][0] == 'x' && part.x > split[0].substringAfter(">").toInt()) {
                            next = split[1]
                            found = true
                            return@loop
                        }
                        if (split[0][0] == 'm' && part.m > split[0].substringAfter(">").toInt()) {
                            next = split[1]
                            found = true
                            return@loop
                        }
                        if (split[0][0] == 'a' && part.a > split[0].substringAfter(">").toInt()) {
                            next = split[1]
                            found = true
                            return@loop
                        }
                        if (split[0][0] == 's' && part.s > split[0].substringAfter(">").toInt()) {
                            next = split[1]
                            found = true
                            return@loop
                        }
                    }
                }
            }

            if (!found) {
                next = workMap[next]!!.default
            }
        }

        if (next == "A") {
            accepted.add(part)
        } else {
            rejected.add(part)
        }
    }

    println(
        "The sum of all accepted part ratings is ${
            accepted.map { a -> a.x + a.m + a.a + a.s }.reduce { a, b -> a + b }
        }"
    )
}

private fun part2(file: File) {
    val workMap: MutableMap<String, Workflow> = mutableMapOf()
    val workflows = getWorkflows(file.readText().split("\n\n")[0])
    workflows.forEach { workMap[it.nam] = it }

    val start = workMap["in"]
    val xVals: IntRange = 1..4000
    val mVals: IntRange = 1..4000
    val aVals: IntRange = 1..4000
    val sVals: IntRange = 1..4000
    val partRanges = mutableMapOf("x" to xVals, "m" to mVals, "a" to aVals, "s" to sVals)

    checkCondition(workMap, start!!, partRanges)
    workflows.forEach { workMap[it.nam] = it }
    println("There are $acceptedPermutations distinct combinations possible")
}

fun saveAcceptedPerms(ranges: MutableMap<String, IntRange>) {
    var res = 1L
    ranges.values.map { it.count() }.forEach { res *= it }
    acceptedPermutations += res
}

private fun checkCondition(
    workMap: MutableMap<String, Workflow>,
    start: Workflow,
    partRanges: MutableMap<String, IntRange>
) {
    var ranges = partRanges.toMutableMap()

    start.mappings.forEach {
        val parts = it.split("<", ">", ":")
        var c = parts[0]
        var num = parts[1].toInt()
        var next = parts[2]
        var range = ranges[c]!!

        if (it.contains("<")) {
            if (range.first < num && range.last < num) { //whole range is smaller than c -> full match: goto next with full ranges
                if (next == "A") {
                    saveAcceptedPerms(ranges)
                } else if (next != "R") checkCondition(workMap, workMap[next]!!, ranges)

            } else if (range.first < num && range.last >= num) { //range is partially smaller than c -> split ranges and continue wih subrange which is smaller
                val valid = range.start..<num
                val invalid = num..range.last
                var nextRanges = ranges.toMutableMap()
                nextRanges[c] = valid
                ranges[c] = invalid
                if (next == "A") saveAcceptedPerms(nextRanges)
                else if (next != "R") checkCondition(workMap, workMap[next]!!, nextRanges)
            } else { //whole range is bigger than c -> all invalid

            }
        } else { //same thing for bigger than c
            if (range.first > num && range.last > num) { //whole range is bigger than c -> full match: goto next with full ranges
                if (next == "A") {
                    saveAcceptedPerms(ranges)
                } else if (next != "R") checkCondition(workMap, workMap[next]!!, ranges)

            } else if (range.first <= num && range.last > num) { //range is partially bigger than c -> split ranges and continue wih subrange which is bigger
                val invalid = range.start..num
                val valid = num + 1..range.last
                var nextRanges = ranges.toMutableMap()
                nextRanges[c] = valid
                ranges[c] = invalid
                if (next == "A") saveAcceptedPerms(nextRanges)
                else if (next != "R") checkCondition(workMap, workMap[next]!!, nextRanges)
            } else { //whole range is smaller than c -> all invalid

            }
        }

    }

    // all conditions are checked, now only non-fitting ranges are remaining -> goto default
    if (start.default == "A") {
        saveAcceptedPerms(ranges)
    } else if (start.default != "R") checkCondition(workMap, workMap[start.default]!!, ranges)
}

private data class PartRatings(
    val x: Int,
    val m: Int,
    val a: Int,
    val s: Int
)

private data class Workflow(
    val nam: String,
    val mappings: List<String>,
    val default: String,
    val isTarget: Boolean = false
)

private fun getPartRatings(text: String): List<PartRatings> {
    val parts = text.split("\n").map {
        val t = it.replace("{", "")
            .replace("}", "")
            .replace("x=", "")
            .replace("m=", "")
            .replace("a=", "")
            .replace("s=", "")
            .split(",")
        PartRatings(t[0].toInt(), t[1].toInt(), t[2].toInt(), t[3].toInt())
    }
    return parts
}

private fun getWorkflows(text: String): MutableList<Workflow> {
    val flows: MutableList<Workflow> = mutableListOf()
    text.split("\n").forEach { flow ->
        val tar = flow.contains(":A") || flow.contains(":R")
        val name = flow.substringBefore("{")
        val flowParts = flow.substringAfter("{").replace("}", "")
            .split(",")
        val mappings = mutableListOf<String>()
        flowParts.dropLast(1).forEach { p ->
            mappings.add(p)
        }
        flows.add(Workflow(name, mappings, flowParts.last(), tar))
    }
    return flows
}