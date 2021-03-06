package com.basementbrosdevelopers.triangulation

import com.basementbrosdevelopers.triangulation.GraphicsManager.WILDCARD_INDEX
import java.util.*

fun areAllTheSame(numbers: List<Int>): Boolean {
    if (numbers.isEmpty()) {
        return true
    }
    val first = numbers[0]
    return numbers.all { it == first }
}

fun filterOutWildCards(numbers: List<Int>): List<Int> {
    return numbers.filter { it != WILDCARD_INDEX }
}

fun main() {
    print(areAllTheSame(filterOutWildCards(Arrays.asList(5, 3, 5, 3))))
}
