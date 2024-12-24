package aoc24.Day17

import kotlin.math.pow

var A = 0L
var B = 0L
var C = 0L
var program = mutableListOf<Long>()
var opPointer = 0
var outPuts = mutableListOf<Int>()

fun main() {

//    run(30553366)
    println("Part 1: The resulting output array: ${run(30553366)}")

    init(0)
    val minAVal = findAMatchingOutput(program, program)
    println("Part 2: The minimum value in Register A is $minAVal")
}

private fun findAMatchingOutput(program: List<Long>, target: List<Long>): Long {
    var aStart = if (target.size == 1) {
        0
    } else {
        8 * findAMatchingOutput(program, target.subList(1, target.size))
    }

    while (run(aStart) != target) {
        aStart++
    }

    return aStart
}

fun run(a: Long): MutableList<Long> {
    var aVal = a
    init(aVal)

    var out = mutableListOf<Long>()
    while (true) {
        if (opPointer + 1 > program.lastIndex) {
            break
        }
        val opcode = program[opPointer]
        val operand = program[opPointer + 1]

        when (opcode) {
            0L -> { // division
                A = (A / 2.0.pow(getComboOperand(operand).toDouble())).toLong()
            }

            1L -> { // xor of b register and operand
                B = B xor operand.toLong()
            }

            2L -> { // B modulo
                B = getComboOperand(operand) % 8
            }

            3L -> {
                if (A != 0L) {
                    opPointer = operand.toInt()
                    continue
                }
            }

            4L -> {
                B = B xor C
            }

            5L -> { // out
                out.add((getComboOperand(operand) % 8))
            }

            6L -> {
                B = (A / 2.0.pow(getComboOperand(operand).toDouble())).toLong()
            }

            7L -> {
                C = (A / 2.0.pow(getComboOperand(operand).toDouble())).toLong()
            }
        }
        opPointer += 2

    }
    return out
}

fun init(aVal: Long) {
    A = aVal
    B = 0L
    C = 0L
    program = mutableListOf(2, 4, 1, 1, 7, 5, 4, 7, 1, 4, 0, 3, 5, 5, 3, 0)
    opPointer = 0
    outPuts = mutableListOf<Int>()
}

fun getComboOperand(operand: Long): Long {
    when (operand) {
        0L, 1L, 2L, 3L -> return operand.toLong()
        4L -> return A
        5L -> return B
        6L -> return C
        else -> throw Exception("invalid operand")
    }
}