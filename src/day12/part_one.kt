package day12

data class Grid(val width: Int, val height: Int) {
    val field = List(height) { BooleanArray(width) }

    fun size() = width * height

    fun addPresent() {

    }

    fun removePresent() {

    }
}

data class Present(val shape: List<Point>, val size: Int) {
    val variants = getUniqueRotatedAndFlippedVariants()

    private fun getUniqueRotatedAndFlippedVariants(): List<Present> {
        val variants = mutableListOf<Present>()
        // TODO: Implement
        return variants
    }
}

data class Point(val x: Int, val y: Int)

enum class FitStatus {
    CANT_FIT, FITS_AS_IS, NEED_TO_DETERMINE
}

fun countPresentsFitUnderTrees(input: List<String>): Int {
    val (presents: Map<Int, Present>, gridsUnderTrees: List<Pair<Grid, List<Int>>>) = parseInput(input)
    println(presents)
    println(gridsUnderTrees)

    var countGridsThatFitsPresents = 0
    for (gridToIndexes in gridsUnderTrees) {
        val caseStatus = getPhysicsStatus(gridToIndexes, presents)
        when(caseStatus) {
            FitStatus.CANT_FIT -> continue
            FitStatus.FITS_AS_IS -> {
                countGridsThatFitsPresents++
                continue
            }
            FitStatus.NEED_TO_DETERMINE -> {
                val (grid, amountOfPresentsOfEachIndex) = gridToIndexes
                // TODO: implement algorithm to determine is presents fits

            }
        }
    }

    return countGridsThatFitsPresents
}

fun getPhysicsStatus(gridToIndexes: Pair<Grid, List<Int>>, presents: Map<Int, Present>): FitStatus {
    val (grid, amountOfPresentsOfEachIndex) = gridToIndexes

    var allPresentsFitsPerfectly = 0L
    var allPresentsFitsAsIs = 0L
    amountOfPresentsOfEachIndex.forEachIndexed { index, amount ->
        allPresentsFitsAsIs += amount * 9
        allPresentsFitsPerfectly += amount * presents[index]!!.size
    }

    println(grid.size())
    println(allPresentsFitsPerfectly)
    println(allPresentsFitsAsIs)
    println()
    if (grid.size() < allPresentsFitsPerfectly) return FitStatus.CANT_FIT
    if (grid.size() >= allPresentsFitsAsIs) return FitStatus.FITS_AS_IS
    return FitStatus.NEED_TO_DETERMINE
}

fun parseInput(input: List<String>): Pair<Map<Int, Present>, List<Pair<Grid, List<Int>>>> {
    val firstGridLineIndex = input.indexOfFirst { it.contains('x') && it.contains(':') }
    val rawPresents = input.take(firstGridLineIndex)
    val rawGridsUnderTrees = input.drop(firstGridLineIndex)

    val presents = parsePresents(rawPresents).mapValues {
        val shape = mutableListOf<Point>()
        for (indexOfRow in 0 until it.value.size) {
            val row = it.value[indexOfRow]
            for (indexOfColumn in 0 until row.length) {
                val char = row[indexOfColumn]
                if (char == '#') {
                    shape.add(Point(indexOfColumn, indexOfRow))
                }
            }
        }
        Present(shape = shape, size = shape.size)
    }
    val grid = parseGrids(rawGridsUnderTrees)
    return presents to grid
}

fun parsePresents(input: List<String>): Map<Int, MutableList<String>> = buildMap {
    var currentKey: Int? = null
    input
        .filter { it.isNotBlank() }
        .forEach { line ->
            if (line.endsWith(':')) {
                currentKey = line.removeSuffix(":").toIntOrNull()
            } else if (currentKey != null) {
                getOrPut(currentKey) { mutableListOf() }.add(line)
            }
        }
}

fun parseGrids(input: List<String>): List<Pair<Grid, List<Int>>> =
    input
        .filter { it.isNotBlank() }
        .map { line ->
            val (rawGrid, rawAmountOfPresentsOfEachIndex) = line.split(':')
            val (width, height) = rawGrid.split('x').map { it.toInt() }
            val amountOfPresentsOfEachIndex = rawAmountOfPresentsOfEachIndex.trim().split(" ").map { it.toInt() }
            Grid(width, height) to amountOfPresentsOfEachIndex
        }

fun main() {
    val exampleInput = """
0:
###
##.
##.

1:
###
##.
.##

2:
.##
###
##.

3:
##.
###
##.

4:
###
#..
###

5:
###
.#.
###

4x4: 0 0 0 0 2 0
12x5: 1 0 1 0 2 2
12x5: 1 0 1 0 3 2""".trim().lines()


//    val individualInput = Path("src/day12/input.txt").readText().trim().lines()

    val countPaths = countPresentsFitUnderTrees(exampleInput)
    println(countPaths)
}
