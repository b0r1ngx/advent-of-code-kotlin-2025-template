package day06

import kotlin.io.path.Path
import kotlin.io.path.readText

val ANY_AMOUNT_OF_SPACES = "\\s+".toRegex()

fun findTotalSumOfCephalopodMath(lines: List<String>): Long {
    var sum = 0L
    val columns = mutableListOf<MutableList<String>>()

    // first line, create mutableLists with first number for each column
    val firstLineNumbers = lines.first().split(ANY_AMOUNT_OF_SPACES).map {
        mutableListOf(it)
    }.dropLastWhile { it[0] == "" }
    columns.addAll(firstLineNumbers)

    // for remaining lines
    for (i in 1 until lines.size) {
        val line = lines[i]
        val lineNumbers = line.split(ANY_AMOUNT_OF_SPACES).dropLastWhile { it == "" }.dropWhile { it == "" }
        columns.forEachIndexed { index, column ->
            column.add(lineNumbers[index])
        }
    }

    columns.forEach { column ->
        val operation = column.last()
        when(operation) {
            "*" -> sum += column.mapNotNull { it.toLongOrNull() }.multiply()
            "+" -> sum += column.mapNotNull { it.toLongOrNull() }.sum()
        }
    }

    return sum
}

fun Iterable<Long>.multiply(): Long {
    var sum = 1L
    for (element in this) {
        sum *= element
    }
    return sum
}

fun main() {
    val exampleInput = """
123 328  51 64 
 45 64  387 23 
  6 98  215 314
*   +   *   + """.trim().lines()

    val individualInput = Path("src/day06/input.txt").readText().trim().lines()

    val sumOfCephalopodMath = findTotalSumOfCephalopodMath(individualInput)
    println(sumOfCephalopodMath)
}
