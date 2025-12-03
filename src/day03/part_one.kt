package day03

import kotlin.io.path.Path
import kotlin.io.path.readText

fun voltageSum(banks: List<String>, isShowHelpPrints: Boolean = false): Int {
    var sum = 0

    banks.forEach { bank ->
        var firstVoltagePointer = 0
        var firstVoltageValue = 0
        var secondVoltagePointer = 0
        var secondVoltageValue = 0

        val firstValueRange = bank.take(bank.length - 1)
        if (isShowHelpPrints) println(firstValueRange)
        firstValueRange.forEachIndexed { index, char ->
            val value = char.toString().toInt()
            if (value > firstVoltageValue) {
                firstVoltageValue = value
                firstVoltagePointer = index
            }
        }

        val shiftStartFrom = firstVoltagePointer + 1
        val secondValueRange = bank.substring(shiftStartFrom, bank.length)
        if (isShowHelpPrints) println(secondValueRange)
        secondValueRange.forEachIndexed { index, char ->
            val value = char.toString().toInt()
            if (value > secondVoltageValue) {
                secondVoltageValue = value
                secondVoltagePointer = index + shiftStartFrom
            }
        }

        val largestVoltage = "${bank[firstVoltagePointer]}${bank[secondVoltagePointer]}".toInt()
        if (isShowHelpPrints) println(bank)
        if (isShowHelpPrints) println(largestVoltage)
        if (isShowHelpPrints) println()
        sum += largestVoltage
    }

    return sum
}

fun main(args: Array<String>) {
    val exampleInput = """
987654321111111
811111111111119
234234234234278
818181911112111""".trim().lines()

    val individualInput = Path("src/day03/input.txt").readText().trim().lines()

    val sum = voltageSum(individualInput)
    println(sum)
}
