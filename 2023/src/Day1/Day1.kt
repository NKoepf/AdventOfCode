package Day1

import java.io.File

fun main() {
    part2()

}

fun part1() {
    val input = File("2023/src/Day1/input.txt")

    var sum = 0
    input.forEachLine {
        val lineVal = getNumberOfString(it)
        println(lineVal)
        sum += lineVal
    }

    println("The total sum of calibration is $sum")
}

fun part2() {
    val input = File("2023/src/Day1/Input.txt")

    var sum = 0
    input.forEachLine {
        val line = replaceWrittenDigits(it)
        val lineVal = getNumberOfString(line)
        println(lineVal)
        sum += lineVal
    }

    println("The total sum of calibration is $sum")
}

fun replaceWrittenDigits(line: String): String {
    println("original: $line")
    var newLine = line
    
    for(i: Int in 1..line.length){
        val subLine = line.subSequence(0, i).toString()
        if(subLine[subLine.length -1].isDigit()) break
        println("sub: $subLine")
        if(subLine.contains("one")){
            newLine = newLine.replaceFirst("one", "1")
            break
        }
        if(subLine.contains("two")){
            newLine = newLine.replaceFirst("two", "2")
            println(newLine)
            break
        }
        if(subLine.contains("three")){
            newLine = newLine.replaceFirst("three", "3")
            println(newLine)
            break
        }
        if(subLine.contains("four")){
            newLine = newLine.replaceFirst("four", "4")
            println(newLine)
            break
        }
        if(subLine.contains("five")){
            newLine = newLine.replaceFirst("five", "5")
            println(newLine)
            break
        }
        if(subLine.contains("six")){
            newLine = newLine.replaceFirst("six", "6")
            println(newLine)
            break
        }
        if(subLine.contains("seven")){
            newLine = newLine.replaceFirst("seven", "7")
            println(newLine)
            break
        }
        if(subLine.contains("eight")){
            newLine = newLine.replaceFirst("eight", "8")
            println(newLine)
            break
        }
        if(subLine.contains("nine")){
            newLine = newLine.replaceFirst("nine", "9")
            println(newLine)
            break
        }

        println(newLine)
    }
    println("first number replaced $newLine")

    for(i: Int in 0..newLine.length - 1){
        val subLine = newLine.subSequence(newLine.length - i - 1, newLine.length).toString()
        println("sub: $subLine")
        if(subLine[0].isDigit()) break

        if(subLine.contains("one")){
            val startIndex = newLine.lastIndexOf("one")
            newLine = newLine.replaceRange(startIndex, startIndex + 3, "1")
            println(newLine)
            break
        }
        if(subLine.contains("two")){
            val startIndex = newLine.lastIndexOf("two")
            newLine = newLine.replaceRange(startIndex, startIndex + 3, "2")
            println(newLine)
            break
        }
        if(subLine.contains("three")){
            val startIndex = newLine.lastIndexOf("three")
            newLine = newLine.replaceRange(startIndex, startIndex + 5, "3")
            println(newLine)
            break
        }
        if(subLine.contains("four")){
            val startIndex = newLine.lastIndexOf("four")
            newLine = newLine.replaceRange(startIndex, startIndex + 4, "4")
            println(newLine)
            break
        }
        if(subLine.contains("five")){
            val startIndex = newLine.lastIndexOf("five")
            newLine = newLine.replaceRange(startIndex, startIndex + 4, "5")
            println(newLine)
            break
        }
        if(subLine.contains("six")){
            val startIndex = newLine.lastIndexOf("six")
            newLine = newLine.replaceRange(startIndex, startIndex + 3, "6")
            println(newLine)
            break
        }
        if(subLine.contains("seven")){
            val startIndex = newLine.lastIndexOf("seven")
            newLine = newLine.replaceRange(startIndex, startIndex + 5, "7")
            println(newLine)
            break
        }
        if(subLine.contains("eight")){
            val startIndex = newLine.lastIndexOf("eight")
            newLine = newLine.replaceRange(startIndex, startIndex + 5, "8")
            println(newLine)
            break
        }
        if(subLine.contains("nine")){
            val startIndex = newLine.lastIndexOf("nine")
            newLine = newLine.replaceRange(startIndex, startIndex + 4, "9")
            println(newLine)
            break
        }

        println(newLine)
    }
    println(newLine)
    return newLine
}


fun getNumberOfString(input: String): Int {
    val onlyDigits = input.replace(Regex("[^0-9]"), "")
    val digits = "${onlyDigits[0]}${onlyDigits[onlyDigits.length - 1]}".toInt()
    return digits
}