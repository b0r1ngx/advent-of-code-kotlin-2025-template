package day01

import kotlin.collections.first
import kotlin.io.path.Path
import kotlin.io.path.readText

val DIAL: List<Int> = List(100) { i -> i }
const val START_POINT = 50

fun getSecretEntrancePassword(data: List<String>): Int {
    var currentPointer = START_POINT
    var pointAtZeroCounter = 0 // password

    data.forEach { rotation ->
        currentPointer = currentPointer.pointAfterRotation(rotation)
        if (currentPointer == 0) pointAtZeroCounter++
    }

    return pointAtZeroCounter
}

private fun Int.pointAfterRotation(rotation: String): Int {
    val direction = rotation[0]
    val amount = rotation.drop(1).toInt()

    var newPoint: Int
    when (direction) {
        'L' -> newPoint = DIAL.rotateLeft(this, amount)
        'R' -> newPoint = DIAL.rotateRight(this, amount)
        else -> newPoint = 0
    }

    return newPoint
}

// for sorted lists (dial)
// 5 - 10 -> 4 3 2 1 0 99 98 97 96 95 = -5 => 95
//           1 2 3 4 5 6  7  8  9  10
fun List<Int>.rotateLeft(
    from: Int, // 5
    amount: Int // 10
): Int {
    val min = this.first() // 0
    val max = this.last() // 99

    var currentPointer = from // 5
    for (i in 0 until amount) {
        if (currentPointer == min) currentPointer = max + 1
        currentPointer--
    }

    return currentPointer
}

fun List<Int>.rotateRight(
    from: Int, // 95
    amount: Int // 10
): Int {
    val min = this.first() // 0
    val max = this.last() // 99

    var currentPointer = from // 95
    for (i in 0 until amount) {
        if (currentPointer == max) currentPointer = min - 1
        currentPointer++
    }

    return currentPointer
}

fun main() {
    val data = Path("src/day01/input.txt").readText().trim().lines()
    val password = getSecretEntrancePassword(data)
    println("Password: $password")
}
