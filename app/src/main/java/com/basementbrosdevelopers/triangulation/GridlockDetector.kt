package com.basementbrosdevelopers.triangulation

import com.basementbrosdevelopers.triangulation.GraphicsManager.WILDCARD_INDEX

private const val NOT_WINNABLE = -1

class GridlockDetector {

    fun isInGridlock(matrix: Array<Array<Square>>): Boolean {
        val rowResults: Array<Array<List<Int>>> = Array(matrix.size) { Array(matrix[0].size) { listOf<Int>() } }
        matrix.forEachIndexed { rowIndex, row ->
            rowResults[rowIndex] = findPairCombosForSquaresInRow(row)
        }
        println("Row results ${rowResults.contentDeepToString()}")
        return !squarePairsWinnable(rowResults, matrix.size)
    }

    private fun findPairCombosForSquaresInRow(row: Array<Square>): Array<List<Int>> {
        val pairResults: Array<List<Int>> = Array(row.size - 1) { listOf(NOT_WINNABLE) }
        row.forEachIndexed { index, square ->
            if (notAtBorder(row.size, index)) {
                val nextSquare = row[index + 1]
                pairResults[index] = pairWinnability(square, nextSquare)
            }
        }
        return pairResults
    }

    private fun pairWinnability(square: Square, nextSquare: Square): List<Int> {
        if (hasAnyWildcards(square, nextSquare)) {
            return nonWildcardNumber(square, nextSquare)
        } else {
            return determineIfPairWinnable(square, nextSquare)
        }
    }

    private fun nonWildcardNumber(square: Square, nextSquare: Square): List<Int> {
        val bothSquares = listOf(square, nextSquare)
        val isAllWildcards = bothSquares
                .map { listOf(it.left, it.right) }
                .flatten()
                .none { it != WILDCARD_INDEX }
        if (isAllWildcards) {
            return listOf(WILDCARD_INDEX)
        }
        val leftSquareHasWildcard = square.left == WILDCARD_INDEX || square.right == WILDCARD_INDEX
        val rightSquareHasWildcard = nextSquare.left == WILDCARD_INDEX || nextSquare.right == WILDCARD_INDEX
        println("leftSquareHasWildcard: $leftSquareHasWildcard rightSquareHasWildcard: $rightSquareHasWildcard")
        val winnableNumbers = mutableListOf<Int>()
        if (leftSquareHasWildcard) {
            val winnableNumberLeft = nonWildcardNumber(square) //could still be wildcard
            val leftIsTwoWildcards = winnableNumberLeft == WILDCARD_INDEX
            if (leftIsTwoWildcards) {
                return listOf(nextSquare.left, nextSquare.right)
            } else {
                winnableNumbers.add(winnableNumberLeft)
            }
        }
        if (rightSquareHasWildcard) {
            val winnableNumberRight = nonWildcardNumber(nextSquare) //could still be wildcard
            val rightIsTwoWildcards = winnableNumberRight == WILDCARD_INDEX
            if (rightIsTwoWildcards) {
                return listOf(square.left, square.right)
            } else {
                winnableNumbers.add(winnableNumberRight)
            }
        }
        return winnableNumbers
    }

    private fun nonWildcardNumber(square: Square): Int {
        return if (square.left == WILDCARD_INDEX) {
            square.right
        } else {
            square.left
        }
    }

    private fun squarePairsWinnable(rowResults: Array<Array<List<Int>>>, matrixSize: Int): Boolean {
        val allSquarePairPairs = mutableListOf<Boolean>()
        rowResults.forEachIndexed { rowIndex, row ->
            if (notAtBorder(matrixSize, rowIndex)) {
                row.forEachIndexed { index, winnableNumber ->
                    if ((rowIndex % 2 == 0 && index % 2 == 0) || (rowIndex % 2 == 1 && index % 2 == 1)) {
                        val squarePairBelowMe = rowResults[rowIndex + 1][index]
                        val thisSquareWinnable = squarePairPairWinnable(winnableNumber, squarePairBelowMe)
                        allSquarePairPairs.add(thisSquareWinnable)
                    }
                }
            }
        }
        println("all square pair pairs: $allSquarePairPairs")
        val winnable = allSquarePairPairs.contains(true)
        println("winnable: $winnable")
        return winnable
    }

    private fun squarePairPairWinnable(myPairWinners: List<Int>, squarePairBelowMeWinners: List<Int>): Boolean {
        if (myPairWinners[0] == NOT_WINNABLE || squarePairBelowMeWinners[0] == NOT_WINNABLE) {
            println("not winnable")
            return false
        }
        val hasWildcard = myPairWinners[0] == WILDCARD_INDEX || squarePairBelowMeWinners[0] == WILDCARD_INDEX
        val isCurrentlyScoring = myPairWinners == squarePairBelowMeWinners //not sure if isCurrentlyScoring can happen because gridlock detection happens after scoring clears out scoring square pair squares
        val hasAWinnerInCommon = hasAWinnerInCommon(myPairWinners, squarePairBelowMeWinners)
        println("hasWildcard: $hasWildcard isCurrentlyScoring: $isCurrentlyScoring")
        return isCurrentlyScoring || hasWildcard || hasAWinnerInCommon
    }

    private fun hasAWinnerInCommon(myPairWinners: List<Int>, squarePairBelowMeWinners: List<Int>): Boolean {
        myPairWinners.forEach {
            if (squarePairBelowMeWinners.contains(it)) {
                return true
            }
        }
        return false
    }

    private fun hasAnyWildcards(left: Square, right: Square): Boolean {
        return listOf(left.left, left.right, right.left, right.right).contains(WILDCARD_INDEX)
    }

    private fun notAtBorder(size: Int, index: Int): Boolean {
        return index + 1 < size
    }

    private fun determineIfPairWinnable(left: Square, right: Square): List<Int> { //TODO: this could return 1 or 2 numbers
        if (left.left == right.left || left.left == right.right) {
            return listOf(left.left)
        } else if (left.right == right.left || left.right == right.right) {
            return listOf(left.right)
        } else {
            return listOf(NOT_WINNABLE)
        }
    }
}

fun isInGridlock(matrix: Array<Array<Square>>): Boolean {
    return GridlockDetector().isInGridlock(matrix)
}
