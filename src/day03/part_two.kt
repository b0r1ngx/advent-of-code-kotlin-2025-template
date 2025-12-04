package day03

import kotlin.io.path.Path
import kotlin.io.path.readText

fun voltageSumPartTwo(voltagesBanks: List<String>): Long {
    var sum: Long = 0
    voltagesBanks.forEach { voltages ->
        sum += findNMaxDigits(voltages, 12)
    }
    return sum
}

fun findNMaxDigits(input: String, n: Int): Long {
    if (input.length <= n) return input.toLong()

    val digitStack = StringBuilder()
    var removalsRemaining = input.length - n

    for (digit in input) {
        while (
            removalsRemaining > 0
            && digitStack.isNotEmpty()
            && digitStack.last() < digit
        ) {
            digitStack.deleteCharAt(digitStack.length - 1)
            removalsRemaining--
        }
        digitStack.append(digit)
    }

    val takeFirstN = digitStack.take(n)
    return takeFirstN.toString().toLong()
}

fun main(args: Array<String>) {
    val exampleInput = """
987654321111111
811111111111119
234234234234278
818181911112111""".trim().lines()

    val individualInput = Path("src/day03/input.txt").readText().trim().lines()

    val sum = voltageSumPartTwo(individualInput)
    println(sum)
}
