package day07

import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.text.indexOf

fun countBeamSplitTimes(diagram: List<String>): Int {
    var splitCounter = 0

    val beamInitialPointer = diagram.first().indexOf('S')
    val beamsPointer = mutableSetOf(beamInitialPointer)

    diagram.forEach { row ->
        row.forEachIndexed { index, char ->
            if (char == '^' && beamsPointer.any { index == it }) {
                splitCounter++
                beamsPointer.remove(index)
                beamsPointer.add(index - 1)
                beamsPointer.add(index + 1)
            }
        }
    }


    return splitCounter
}

fun main() {
    val exampleInput = """
.......S.......
...............
.......^.......
...............
......^.^......
...............
.....^.^.^.....
...............
....^.^...^....
...............
...^.^...^.^...
...............
..^...^.....^..
...............
.^.^.^.^.^...^.
...............
    """.trim().lines()

    val individualInput = Path("src/day07/input.txt").readText().trim().lines()

    val sum = countBeamSplitTimes(individualInput)
    println(sum)
}