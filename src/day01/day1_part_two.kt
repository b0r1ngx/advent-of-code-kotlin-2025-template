package day01

import kotlin.collections.first
import kotlin.io.path.Path
import kotlin.io.path.readText

fun getSecretEntrancePasswordPartTwo(data: List<String>): Int {
    var currentPointer = START_POINT
    var pointAtZeroCounter = 0 // password

    data.forEach { rotation ->
        currentPointer.pointAfterRotation(rotation, pointAtZeroCounter).also {
            currentPointer = it.first
            pointAtZeroCounter = it.second
        }
        if (currentPointer == 0) pointAtZeroCounter++
    }

    return pointAtZeroCounter
}

private fun Int.pointAfterRotation(rotation: String, globalCounter: Int): Pair<Int, Int> {
    val direction = rotation[0]
    val amount = rotation.drop(1).toInt()

    var newPoint: Int
    var _globalCounter = globalCounter
    when (direction) {
        'L' -> DIAL.rotateLeftPartTwo(this, amount, globalCounter).also {
            newPoint = it.first
            _globalCounter = it.second
        }
        'R' -> DIAL.rotateRightPartTwo(this, amount, globalCounter).also {
            newPoint = it.first
            _globalCounter = it.second
        }
        else -> newPoint = 0
    }

    return newPoint to _globalCounter
}

// for sorted lists (dial)
// 5 - 10 -> 4 3 2 1 0 99 98 97 96 95 = -5 => 95
//           1 2 3 4 5 6  7  8  9  10
fun List<Int>.rotateLeftPartTwo(
    from: Int, // 5
    amount: Int, // 10
    globalCounter: Int,
): Pair<Int, Int> {
    val min = this.first() // 0
    val max = this.last() // 99

    var currentPointer = from // 5
    var _globalCounter = globalCounter
    for (i in 0 until amount) {
        if (currentPointer == min) {
            currentPointer = max + 1
            _globalCounter++
        }
        currentPointer--
    }

    return currentPointer to _globalCounter
}

fun List<Int>.rotateRightPartTwo(
    from: Int, // 95
    amount: Int, // 10
    globalCounter: Int,
): Pair<Int, Int> {
    val min = this.first() // 0
    val max = this.last() // 99

    var currentPointer = from // 95
    var _globalCounter = globalCounter
    for (i in 0 until amount) {
        if (currentPointer == max) {
            currentPointer = min - 1
            _globalCounter++
        }
        currentPointer++
    }

    return currentPointer to _globalCounter
}

fun main() {
    val data = Path("src/day01/input.txt").readText().trim().lines()
    val password = getSecretEntrancePasswordPartTwo(data)
    println("Password: $password")
}
