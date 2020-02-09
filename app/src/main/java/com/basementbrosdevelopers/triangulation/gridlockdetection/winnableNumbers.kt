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
        val winnableNumberLeft = winnableNumber(square) //could still be wildcard
        val leftIsTwoWildcards = winnableNumberLeft == WILDCARD_INDEX
        winnableNumbers.addAll(listOf(nextSquare.left, nextSquare.right))
        if (!leftIsTwoWildcards) {
            if (nextSquare.left == winnableNumberLeft || nextSquare.right == winnableNumberLeft) {
                winnableNumbers.add(winnableNumberLeft)
            }
        }
    }
    if (rightSquareHasWildcard) {
        val winnableNumberRight = winnableNumber(nextSquare) //could still be wildcard
        val rightIsTwoWildcards = winnableNumberRight == WILDCARD_INDEX
        winnableNumbers.addAll(listOf(square.left, square.right))
        if (!rightIsTwoWildcards) {
            if (square.left == winnableNumberRight || square.right == winnableNumberRight) {
                winnableNumbers.add(winnableNumberRight)
            }
        }
    }
    if (rightSquareHasWildcard && leftSquareHasWildcard) {
        winnableNumbers.add(WILDCARD_INDEX)
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
