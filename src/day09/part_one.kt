package day09

import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.math.abs

fun findLargestRectangleOfTwoCorners(redTilesOnTheFloor: List<String>): Long {
    var largestRectangle = 0L

    redTilesOnTheFloor.forEach { i ->
        val iTile = i.split(',').map { it.toLong() }

        redTilesOnTheFloor.forEach { j ->
            val jTile = j.split(',').map { it.toLong() }
            val currentRectangle = (abs(jTile.first() - iTile.first()) + 1) * (abs(jTile.last() - iTile.last()) + 1)
            if (currentRectangle > largestRectangle)
                largestRectangle = currentRectangle
        }
    }

    return largestRectangle
}

fun main() {
    val exampleInput = """
7,1
11,1
11,7
9,7
9,5
2,5
2,3
7,3
    """.trim().lines()
    // visualizer
    // # - red, . - empty
    //..............
    //.......#...#..
    //..............
    //..#....#......
    //..............
    //..#......#....
    //..............
    //.........#.#..
    //..............

    val individualInput = Path("src/day09/input.txt").readText().trim().lines()

    val largestRectangle = findLargestRectangleOfTwoCorners(individualInput)
    println(largestRectangle)
}