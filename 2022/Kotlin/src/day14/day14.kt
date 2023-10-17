package day14

import java.io.File

fun main(args: Array<String>) {
    println("Hello world")
    generateField()
}

fun generateField(){
    val board = arrayOf<Array<String>>(10)
    repeat(10){
        board[it] = Array(10){"."}
    }

    printBoard(board)
    val field: MutableList<MutableList<String>> = mutableListOf<MutableList<String>>()

    File("Kotlin/src/day14/testInput.txt").forEachLine { line ->
        println("first")
        println("second")
        println(line)


     }
}

fun printBoard(board: Array<Array<String>>){
    for (row in board) {
        for (col in row) {
            print("$col ")
        }
        println()
    }
}