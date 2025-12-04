package day04

import kotlin.io.path.Path
import kotlin.io.path.readText

const val IS_SHOW_LOG = false

val ADJACENT_POSITIONS = listOf(
    (-1 to -1), (-1 to 0), (-1 to 1),
    (0 to -1),            (0 to 1),
    (1 to -1), (1 to 0), (1 to 1)
)

fun printingDepartment(papersGrid: List<String>): Int {
    var possibleToAccessWithForkliftCounter = 0

    papersGrid.forEachIndexed { i, row ->
        row.forEachIndexed { j, value ->
            if (IS_SHOW_LOG) println("[$i, $j] - $value")
            if (value == '@') {
                if (countAdjacentPapers(i, j, papersGrid) < 4) {
                    possibleToAccessWithForkliftCounter++
                }
            }
        }
    }

    return possibleToAccessWithForkliftCounter
}

fun countAdjacentPapers(rowIndex: Int, columnIndex: Int, papersGrid: List<String>): Int {
    var papersCounter = 0
    for (direction in ADJACENT_POSITIONS) {
        val rowIndex = rowIndex + direction.first
        val columnIndex = columnIndex + direction.second

        val adjacentValue = papersGrid.getOrNull(rowIndex)?.getOrNull(columnIndex) ?: continue
        if (IS_SHOW_LOG) println("adjacentValue: $adjacentValue")
        if (adjacentValue == '@') papersCounter++
    }

    if (IS_SHOW_LOG) println("adjacentPapers: $papersCounter")
    return papersCounter
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

    val sum = printingDepartment(individualInput)
    println(sum)
}
