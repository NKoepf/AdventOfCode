package Day12

import java.io.File


var permutations: MutableMap<Input, Long> = mutableMapOf()

fun main() {
    val file = File("2023/src/Day12/input.txt")
    Util.printPart1()
    part1(file)
    Util.printPart2()
    part2(file)
}

fun part1(file: File) {

    var sum = 0L
    file.forEachLine { line ->
        val parts = line.split(" ")
        val locations = parts[0]
        val groups = parts[1].split(",").map { it.toLong() }
        sum += countPermutations(locations, groups)
    }
    println("There are $sum permutations")
}

fun part2(file: File) {
    var sum = 0L
    file.forEachLine { line ->
        val parts = line.split(" ")
        val locations = unfoldCondition(parts[0], 5)
        val groups = unfoldGroups(parts[1].split(",").map { num -> num.toLong() }.toList(), 5)
        sum += countPermutations(locations, groups)
    }
    println("There are $sum permutations")
}

fun countPermutations(condition: String, groups: List<Long>): Long {
    var input: Input = Input(condition, groups)
    if (permutations.containsKey(Input(condition, groups))) return permutations[Input(condition, groups)]!!

    if (condition.isBlank()) {
        return if (groups.isEmpty()) return 1L else return 0L
    }

    val first = condition.first()
    var perms = 0L
    if (first == '.') perms = countPermutations(condition.substring(1), groups)
    else if (first == '?') {
        perms = countPermutations("." + condition.substring(1), groups) +
                countPermutations("#" + condition.substring(1), groups)
    } else {
        // first is #
        if (groups.size == 0) {
            perms = 0
        } else {
            var nrDamaged = groups[0]
            if (nrDamaged <= condition.length
                && condition.chars().limit(nrDamaged).allMatch { c -> c.toChar() == '#' || c.toChar() == '?' }
            ) {
                val newGroups: List<Long> = groups.subList(1, groups.size)
                if (nrDamaged == condition.length.toLong()) {
                    // The remaining length of the condition is equal to the nr of damaged springs
                    // in the group, so we are done if there are no remaining groups.
                    perms = if (newGroups.isEmpty()) 1 else 0
                } else if (condition[nrDamaged.toInt()] === '.') {
                    // We have just added a group of damaged springs (#) and the next spring is
                    // operational, this is valid, so skip over that operational spring.
                    perms = countPermutations(condition.substring(nrDamaged.toInt() + 1), newGroups)
                } else if (condition[nrDamaged.toInt()] === '?') {
                    // We have just added a group of damaged springs (#), so the next spring can
                    // only be operational (.).
                    perms = countPermutations("." + condition.substring(nrDamaged.toInt() + 1), newGroups)
                } else {
                    // We have just added a group of damaged springs (#), but the next character is
                    // also a damaged spring, this is not valid.
                    perms = 0
                }
            } else {
                // Either size of the group is greater than the remaining length of the
                // condition or the next nrDamaged springs are not either damaged (#) or unknown
                // (?).
                perms = 0
            }
        }
    }

    permutations.put(input, perms);
    return perms;
}

private fun unfoldCondition(condition: String, times: Int): String {
    val sb = StringBuilder()
    for (i in 0 until times - 1) {
        sb.append(condition)
        sb.append("?")
    }
    sb.append(condition)
    return sb.toString()
}

private fun unfoldGroups(groups: List<Long>, times: Int): List<Long> {
    val unfoldedGroups: MutableList<Long> = ArrayList()
    for (i in 0 until times) {
        unfoldedGroups.addAll(groups)
    }
    return unfoldedGroups
}

data class Input(
    var locations: String,
    var groups: List<Long>
)