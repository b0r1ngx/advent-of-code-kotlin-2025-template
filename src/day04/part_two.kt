package day04

import kotlin.io.path.Path
import kotlin.io.path.readText

fun printingDepartmentPartTwo(papersGrid: MutableList<String>): Int {
    var possibleToAccessWithForkliftCounter = 0
    var previousCycleCounter = 0

    while (true) {
        papersGrid.forEachIndexed { i, row ->
            row.forEachIndexed { j, value ->
                if (value == '@') {
                    if (countAdjacentPapers(i, j, papersGrid) < 4) {
                        possibleToAccessWithForkliftCounter++
                        papersGrid[i] = papersGrid[i].replaceRange(j, j + 1, ".")
                    }
                }
            }
        }

        if (previousCycleCounter == possibleToAccessWithForkliftCounter) break
        else previousCycleCounter = possibleToAccessWithForkliftCounter
    }

    return possibleToAccessWithForkliftCounter
}

fun main(args: Array<String>) {
    val exampleInput = """
..@@.@@@@.
@@@.@.@.@@
@@@@@.@.@@
@.@@@@..@.
@@.@@@@.@@
.@@@@@@@.@
.@.@.@.@@@
@.@@@.@@@@
.@@@@@@@@.
@.@.@@@.@.""".trim().lines()

    val individualInput = Path("src/day04/input.txt").readText().trim().lines()

    val sum = printingDepartmentPartTwo(individualInput.toMutableList())
    println(sum)
}
