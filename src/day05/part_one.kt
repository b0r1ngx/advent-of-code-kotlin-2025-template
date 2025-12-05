package day05

import kotlin.io.path.Path
import kotlin.io.path.readText

fun splitRangesAndAvailableIngredients(freshIngredientsRangesAndAvailableIngredients: List<String>): Pair<List<String>, List<String>> {
    val freshIngredientsRanges = mutableListOf<String>()
    val availableIngredients = mutableListOf<String>()

    var isUpdateRanges = true
    for (line in freshIngredientsRangesAndAvailableIngredients) {
        if (line == "") {
            isUpdateRanges = false
            continue
        }

        if (isUpdateRanges) freshIngredientsRanges.add(line)
        else availableIngredients.add(line)
    }

    return freshIngredientsRanges to availableIngredients
}

fun countFreshIngredientsByIdNotOptimized(freshIngredientsRanges: List<String>, availableIngredients: List<String>): Int {
    var freshIngredientsCounter = 0

    for (ingredient in availableIngredients) {
        if (freshIngredientsRanges.any { rawRange ->
                val (from, to) = rawRange.split('-').map { it.toLong() }
                val value =ingredient.toLong()
                from <= value && value <= to
        }) {
            freshIngredientsCounter++
        }
    }

    return freshIngredientsCounter
}

fun countFreshIngredientsById(freshIngredientsRanges: List<LongRange>, availableIngredients: List<String>): Int {
    var freshIngredientsCounter = 0

    for (ingredient in availableIngredients) {
        if (freshIngredientsRanges.any { it.contains(ingredient.toLong()) }) {
            freshIngredientsCounter++
        }
    }

    return freshIngredientsCounter
}

fun mergeRangesToOneRange(freshIngredientsRanges: List<String>): List<LongRange> {
    val freshIngredientsRanges = mutableListOf<String>()

    for (_range in freshIngredientsRanges) {
        val (from, to) = _range.split('-')
        // TODO: Implement this
    }

    return listOf()
}

fun main() {
    val exampleInput = """
3-5
10-14
16-20
12-18

1
5
8
11
17
32""".trim().lines()

    val individualInput = Path("src/day05/input.txt").readText().trim().lines()

    val (a, b) = splitRangesAndAvailableIngredients(individualInput)
    val freshIngredients = countFreshIngredientsByIdNotOptimized(a, b)
    println(freshIngredients)
}
