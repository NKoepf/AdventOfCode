package aoc24.Day19

import java.io.File

var tokens: List<String> = listOf()
var patterns: MutableList<String> = mutableListOf()
var resolvedParts = mutableMapOf<String, Long>()

fun main() {
    val input = File("2024/src/aoc24/day19/input.txt")
    var tokenPart = true
    input.forEachLine { line ->
        if (tokenPart) {
            tokens = line.split(", ")
            tokenPart = false
        } else if (!tokenPart && line != "") {
            patterns.add(line)
        }
    }

    println(tokens)
    println(patterns)
    var validPatters = 0

    patterns.forEach {
        if (checkPatternViability(it)) validPatters++
    }
    println("Part 1: There are $validPatters valid patterns")
    // - Part 2 -

    var allPatterns = 0L
    patterns.forEach { pattern ->
        allPatterns += checkAllPatternOptions(pattern, 0)
    }

    println("Part 2: There are $allPatterns possible patterns combinations")
}

fun checkAllPatternOptions(pattern: String, count: Long): Long {
    var validPatterns = count
    if (pattern == "") return ++validPatterns

    tokens.forEach { token ->
        if (pattern.startsWith(token)) {
            if (resolvedParts.containsKey(pattern.substring(token.length))) {
                validPatterns += resolvedParts[pattern.substring(token.length)]!!
            } else {
                // possible solutions for this substring alone
                val res = checkAllPatternOptions(pattern.substring(token.length), validPatterns) - validPatterns
                resolvedParts[pattern.substring(token.length)] = res
                validPatterns = res + validPatterns
            }
        }
    }
    return validPatterns
}

fun checkPatternViability(pattern: String): Boolean {
    if (pattern == "") return true
    tokens.forEach { token ->
        if (pattern.startsWith(token)) {
            if (checkPatternViability(pattern.substring(token.length))) return true
        }
    }
    return false
}

