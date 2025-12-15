package day11

import kotlin.io.path.Path
import kotlin.io.path.readText

fun countPathsPartTwo(from: String, to: String, rawPaths: List<String>): Long {
    val pathMap = mutableMapOf<String, List<String>>()
    rawPaths.forEach { rawPath ->
        val (path, rawLeadingToPaths) = rawPath.split(": ")
        val leadingToPaths = rawLeadingToPaths.split(' ')
        pathMap[path] = leadingToPaths
    }

    val memory = mutableMapOf<Triple<String, Boolean, Boolean>, Long>()

    fun customDepthFirstSearch(
        path: String,
        isDacVisited: Boolean = false,
        isFftVisited: Boolean = false,
        visitedPaths: Set<String> = emptySet()
    ): Long {
        val key = Triple(path, isDacVisited, isFftVisited)
        if (memory.containsKey(key)) return memory[key]!!

        if (path in visitedPaths) return 0L

        val currentIsDacVisited = isDacVisited || path == "dac"
        val currentIsFftVisited = isFftVisited || path == "fft"

        if (path == to) return if (currentIsDacVisited && currentIsFftVisited) 1L else 0L

        var countPathsThroughDacAndFft = 0L
        val leadingToPaths = visitedPaths + path
        pathMap[path]?.forEach { leadingToPath ->
            countPathsThroughDacAndFft += customDepthFirstSearch(
                leadingToPath, currentIsDacVisited, currentIsFftVisited, leadingToPaths
            )
        }

        memory[key] = countPathsThroughDacAndFft
        return countPathsThroughDacAndFft
    }

    return customDepthFirstSearch(path = from)
}

fun main(args: Array<String>) {
    val exampleInput = """
svr: aaa bbb
aaa: fft
fft: ccc
bbb: tty
tty: ccc
ccc: ddd eee
ddd: hub
hub: fff
eee: dac
dac: fff
fff: ggg hhh
ggg: out
hhh: out""".trim().lines()

    val individualInput = Path("src/day11/input.txt").readText().trim().lines()

    // exampleInput all paths:
    // aaa,fft,ccc,ddd,hub,fff,ggg,out
    // aaa,fft,ccc,ddd,hub,fff,hhh,out
    // aaa,*fft*,ccc,eee,*dac*,fff,ggg,out
    // aaa,*fft*,ccc,eee,*dac*,fff,hhh,out
    // bbb,tty,ccc,ddd,hub,fff,ggg,out
    // bbb,tty,ccc,ddd,hub,fff,hhh,out
    // bbb,tty,ccc,eee,dac,fff,ggg,out
    // bbb,tty,ccc,eee,dac,fff,hhh,out
    val countPaths = countPathsPartTwo(from = "svr", to = "out", rawPaths = individualInput)
    println(countPaths)
}
