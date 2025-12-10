package day10

import kotlin.io.path.Path
import kotlin.io.path.readText

fun findFewestButtonPresses(input: List<String>): List<Int> {
    val buttonPressesByMachine = mutableListOf<Int>()

    machineLoop@ for (rawMachineConfiguration in input) {
        println(rawMachineConfiguration)
        val splitConfiguration = rawMachineConfiguration.split(' ')
        // "[.##.]" -> ".##."
        val rawIndicatorLightDiagram = splitConfiguration.first()
        val indicatorLightDiagram = rawIndicatorLightDiagram.subSequence(1, rawIndicatorLightDiagram.length - 1)
        println(indicatorLightDiagram)
        // ((3), (1,3), (2), (2,3), (0,2), (0,1)) ->
        val rawButtons = splitConfiguration.subList(1, splitConfiguration.size - 1)
        println(rawButtons)
        val buttons = rawButtons.map { it.subSequence(1, it.length - 1).split(',').map { it.toInt() } }
        println(buttons)

        // "...."
        val initialLightDiagram = String(indicatorLightDiagram.length) { '.' }

        // we need to save combination of button presses, to lightDiagram
        val findSolutionCollection = mutableListOf<MutableList<Pair<List<Int>, String>>>()
        // add first info to findSolutionCollection (init it)
        val init = mutableListOf<Pair<List<Int>, String>>()
        buttons.forEachIndexed { index, buttonScheme ->
            val newLightDiagram = switchLightDiagram(initialLightDiagram, buttonScheme)
            if (newLightDiagram == indicatorLightDiagram) {
                buttonPressesByMachine.add(1)
                continue@machineLoop
            }
            init.add(listOf(index) to newLightDiagram)
        }
        findSolutionCollection.add(init)

        // find solution algorithm
        var step = 0
        loop2@ while(true) {
            val prevStepVariants = findSolutionCollection[step]
            prevStepVariants.forEach { (buttonPressesCombination, lightDiagram) ->
                buttons.forEachIndexed { index, buttonScheme ->
                    val newLightDiagram = switchLightDiagram(lightDiagram, buttonScheme)
                    if (newLightDiagram == indicatorLightDiagram) {
                        buttonPressesByMachine.add(buttonPressesCombination.size + 1)
                        break@loop2
                    }

                    val newCombination = buttonPressesCombination + index
                    if (findSolutionCollection.getOrNull(step + 1) == null) {
                        findSolutionCollection.add(mutableListOf())
                    }
                    findSolutionCollection[step + 1].add(newCombination to newLightDiagram)
                }
            }
            step++
        }
    }

    return buttonPressesByMachine

}

fun String(size: Int, init: (index: Int) -> Char): String {
    var string = ""
    repeat(size) { index -> string += init(index) }
    return string
}

fun switchLightDiagram(lightDiagram: String, buttonScheme: List<Int>): String {
    val newLightDiagram = lightDiagram.toCharArray()
    buttonScheme.forEach { switchLightIndex ->
        val switchTo: Char
        if (lightDiagram[switchLightIndex] == '.') {
            switchTo = '#'
        } else {
            switchTo = '.'
        }
        newLightDiagram[switchLightIndex] = switchTo

    }
    return newLightDiagram.concatToString()
}

fun main() {
    val exampleInput = """
[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}""".trim().lines()

    val individualInput = Path("src/day10/input.txt").readText().trim().lines()

    val fewestButtonPresses = findFewestButtonPresses(individualInput)
    println(fewestButtonPresses)
    println(fewestButtonPresses.sum())
}
