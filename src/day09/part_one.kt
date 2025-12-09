package day09

import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.math.abs

data class Tile(val x: Long, val y: Long)

fun findLargestRectangleOfTwoCorners(redTilesOnTheFloor: List<String>): Long {
    var largestRectangle = 0L

    redTilesOnTheFloor.forEachIndexed { tileIndex, tilePosition ->
        val (iTileX, iTileY) = tilePosition.split(',').map { it.toLong() }
        val iTile = Tile(iTileX, iTileY)

        for (j in tileIndex + 1 until redTilesOnTheFloor.size) {
            val (jTileX, jTileY) = redTilesOnTheFloor[j].split(',').map { it.toLong() }
            val jTile = Tile(jTileX, jTileY)

            val currentRectangle = (abs(jTile.x - iTile.x) + 1) * (abs(jTile.y - iTile.y) + 1)
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