package day11

import kotlin.io.path.Path
import kotlin.io.path.readText

fun countPathsFromYouToOut(paths: List<String>): Int {
    val pathMap = mutableMapOf<String, List<String>>()
    paths.forEach { rawPath ->
        val (path, rawLeadingToPaths) = rawPath.split(": ")
        val leadingToPaths = rawLeadingToPaths.split(' ')
        pathMap[path] = leadingToPaths
    }

    var pathsToOut = mutableListOf<String>()
    var countPathsToOut = 0
    // listOf(bbb, ccc)
    pathsToOut.addAll(pathMap["you"]!!)
    while (pathsToOut.isNotEmpty()) {
        val _possiblePaths = mutableListOf<String>()
        _possiblePaths.addAll( pathsToOut)
        _possiblePaths.forEach {
            // bbb -> ddd, eee
            // ccc -> ddd, eee, fff
            val path = pathsToOut.removeFirst()
            val nextPaths = pathMap[path]!!
            if (nextPaths.contains("out")) {
                countPathsToOut++
            } else {
                pathsToOut.addAll(nextPaths)
            }
        }
    }

    return countPathsToOut
}

fun main(args: Array<String>) {
    val exampleInput = """
aaa: you hhh
you: bbb ccc
bbb: ddd eee
ccc: ddd eee fff
ddd: ggg
eee: out
fff: out
ggg: out
hhh: ccc fff iii
iii: out""".trim().lines()

    val individualInput = Path("src/day11/input.txt").readText().trim().lines()

    val countPaths = countPathsFromYouToOut(individualInput)
    println(countPaths)
}
