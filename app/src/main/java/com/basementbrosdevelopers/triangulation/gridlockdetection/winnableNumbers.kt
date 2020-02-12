package com.basementbrosdevelopers.triangulation.gridlockdetection

import com.basementbrosdevelopers.triangulation.GraphicsManager.WILDCARD_INDEX
import com.basementbrosdevelopers.triangulation.Square

fun winnableNumbers(square: Square, nextSquare: Square): List<Int> {
    val bothSquares = listOf(square, nextSquare)
    val isAllWildcards = bothSquares
            .map { listOf(it.left, it.right) }
            .flatten()
            .none { it != WILDCARD_INDEX }
    if (isAllWildcards) {
        println("all wildcards")
        return listOf(WILDCARD_INDEX)
    }
    val leftSquareHasWildcard = square.left == WILDCARD_INDEX || square.right == WILDCARD_INDEX
    val rightSquareHasWildcard = nextSquare.left == WILDCARD_INDEX || nextSquare.right == WILDCARD_INDEX
    println("leftSquareHasWildcard: $leftSquareHasWildcard rightSquareHasWildcard: $rightSquareHasWildcard")
    val winnableNumbers = mutableListOf<Int>()
    if (leftSquareHasWildcard) {
        winnableNumbers.addAll(winnableNumbersAccountingForWildcard(square, nextSquare))
    }
    if (rightSquareHasWildcard) {
        winnableNumbers.addAll(winnableNumbersAccountingForWildcard(nextSquare, square))
    }
    if (rightSquareHasWildcard && leftSquareHasWildcard) {
        winnableNumbers.add(WILDCARD_INDEX)
    }
    return winnableNumbers
}

private fun winnableNumbersAccountingForWildcard(thisSide: Square, otherSide: Square): List<Int> {
    val winnableNumberRight = winnableNumber(thisSide) //could still be wildcard
    val rightIsTwoWildcards = winnableNumberRight == WILDCARD_INDEX
    val winnableNumbers = mutableListOf(otherSide.left, otherSide.right)
    if (!rightIsTwoWildcards) {
        if (otherSide.left == winnableNumberRight || otherSide.right == winnableNumberRight) {
            winnableNumbers.add(winnableNumberRight)
        }
    }
    return winnableNumbers
}

private fun winnableNumber(square: Square): Int {
    return if (square.left == WILDCARD_INDEX) {
        square.right
    } else {
        square.left
    }
}
