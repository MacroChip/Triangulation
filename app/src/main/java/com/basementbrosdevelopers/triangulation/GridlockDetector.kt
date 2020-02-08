package com.basementbrosdevelopers.triangulation

import com.basementbrosdevelopers.triangulation.GraphicsManager.WILDCARD_INDEX

private const val NOT_WINNABLE = -1

class GridlockDetector {

    fun isInGridlock(matrix: Array<Array<Square>>): Boolean {
        val rowResults: Array<Array<Int>> = Array(matrix.size) { arrayOf<Int>() }
        matrix.forEachIndexed { rowIndex, row ->
            rowResults[rowIndex] = findPairCombosForSquaresInRow(row)
        }
        println("Row results ${rowResults.contentDeepToString()}")
        return !squarePairsWinnable(rowResults, matrix.size)
    }

    private fun findPairCombosForSquaresInRow(row: Array<Square>): Array<Int> {
        val pairResults: Array<Int> = Array(row.size - 1) { NOT_WINNABLE }
        row.forEachIndexed { index, square ->
            if (notAtBorder(row.size, index)) {
                val nextSquare = row[index + 1]
                pairResults[index] = pairWinnability(square, nextSquare)
            }
        }
        return pairResults
    }

    private fun pairWinnability(square: Square, nextSquare: Square): Int {
        if (hasAnyWildcards(square, nextSquare)) {
            return nonWildcardNumber(square, nextSquare)
        } else {
            return determineIfPairWinnable(square, nextSquare)
        }
    }

    private fun nonWildcardNumber(square: Square, nextSquare: Square): Int {
        //TODO: protect against both squares having a wildcard
        //TODO: return both sides of the non-wildcard square because either of the triangles could pair with the triangle
        val bothSquares = listOf(square, nextSquare)
        val isAllWildcards = bothSquares
                .map { listOf(it.left, it.right) }
                .flatten()
                .none { it != WILDCARD_INDEX }
        if (isAllWildcards) {
            return WILDCARD_INDEX
        }
        return bothSquares.filter { it.left != WILDCARD_INDEX && it.right != WILDCARD_INDEX }[0].left
    }

    private fun squarePairsWinnable(rowResults: Array<Array<Int>>, matrixSize: Int): Boolean {
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

    private fun squarePairPairWinnable(myPair: Int, squarePairBelowMe: Int): Boolean {
        if (myPair == NOT_WINNABLE || squarePairBelowMe == NOT_WINNABLE) {
            println("not winnable")
            return false
        }
        val hasWildcard = myPair == WILDCARD_INDEX || squarePairBelowMe == WILDCARD_INDEX
        val isCurrentlyScoring = myPair == squarePairBelowMe //not sure if isCurrentlyScoring can happen because gridlock detection happens after scoring clears out scoring square pair squares
        println("hasWildcard: $hasWildcard isCurrentlyScoring: $isCurrentlyScoring")
        return isCurrentlyScoring || hasWildcard
    }

    private fun hasAnyWildcards(left: Square, right: Square): Boolean {
        return listOf(left.left, left.right, right.left, right.right).contains(WILDCARD_INDEX)
    }

    private fun notAtBorder(size: Int, index: Int): Boolean {
        return index + 1 < size
    }

    private fun determineIfPairWinnable(left: Square, right: Square): Int { //TODO: this could return 1 or 2 numbers
        if (left.left == right.left || left.left == right.right) {
            return left.left
        } else if (left.right == right.left || left.right == right.right) {
            return left.right
        } else {
            return NOT_WINNABLE
        }
    }
}

fun isInGridlock(matrix: Array<Array<Square>>): Boolean {
    return GridlockDetector().isInGridlock(matrix)
}
