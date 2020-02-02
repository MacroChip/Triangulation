package com.basementbrosdevelopers.triangulation

fun areAllTheSame(numbers: List<Int>): Boolean {
    if (numbers.isEmpty()) {
        return true
    }
    val first = numbers[0]
    return numbers.all { it == first }
}

fun filterOutWildCards(numbers: List<Int>): List<Int> {
    return numbers.filter { it != 5 }
}

fun main() {
    print(areAllTheSame(filterOutWildCards(listOf(5, 3, 5, 3))))
}
