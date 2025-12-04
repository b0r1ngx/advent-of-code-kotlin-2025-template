package day01

import kotlin.collections.first
import kotlin.io.path.Path
import kotlin.io.path.readText

private var pointAtZeroCounter = 0

fun getSecretEntrancePasswordPartTwo(data: List<String>): Int {
    var currentPointer = START_POINT

    data.forEach { rotation ->
        currentPointer = currentPointer.pointAfterRotation(rotation)
    }

    return pointAtZeroCounter
}

private fun Int.pointAfterRotation(rotation: String): Int {
    val direction = rotation[0]
    val amount = rotation.drop(1).toInt()

    var newPoint: Int
    when (direction) {
        'L' -> newPoint = DIAL.rotateLeftPartTwo(this, amount)
        'R' -> newPoint = DIAL.rotateRightPartTwo(this, amount)
        else -> newPoint = 0
    }

    return newPoint
}

// for sorted lists (dial)
// 5 - 10 -> 4 3 2 1 0 99 98 97 96 95 = -5 => 95
//           1 2 3 4 5 6  7  8  9  10
fun List<Int>.rotateLeftPartTwo(
    from: Int, // 5
    amount: Int, // 10
): Int {
    val min = this.first() // 0
    val max = this.last() // 99

    var currentPointer = from // 5
    for (i in 0 until amount) {
        currentPointer--
        if (currentPointer < min) currentPointer = max
        if (currentPointer == 0) pointAtZeroCounter++
    }

    return currentPointer
}

fun List<Int>.rotateRightPartTwo(
    from: Int, // 95
    amount: Int, // 10
): Int {
    val min = this.first() // 0
    val max = this.last() // 99

    var currentPointer = from // 95
    for (i in 0 until amount) {
        currentPointer++
        if (currentPointer > max) currentPointer = min
        if (currentPointer == 0) pointAtZeroCounter++
    }

    return currentPointer
}

fun solutionFromStream(data: List<String>): Int {
    var currentPointer = START_POINT

    data.forEach { rotation ->
        val direction = if (rotation[0] == 'L') - 1 else 1
        val amount = rotation.drop(1).toInt()

        repeat(amount) {
            currentPointer = (currentPointer + direction) % 100
            if (currentPointer == 0) pointAtZeroCounter++
        }
    }

    return pointAtZeroCounter
}

fun main() {
    val data = Path("src/day01/input.txt").readText().trim().lines()
    val password = getSecretEntrancePasswordPartTwo(data)
    pointAtZeroCounter = 0
    check(password == solutionFromStream(data))
    println("password: $password")
}
