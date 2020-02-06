package com.basementbrosdevelopers.triangulation

import com.basementbrosdevelopers.triangulation.GraphicsManager.WILDCARD_INDEX

private const val NOT_WINNABLE = -1

class GridlockDetector {

    fun isInGridlock(matrix: Array<Array<Square>>): Boolean {
        var rowResults: Array<Array<Int>> = Array(matrix.size) { arrayOf<Int>() }
        matrix.forEachIndexed { rowIndex, row ->
            val pairResult: Array<Int> = Array(row.size - 1) { NOT_WINNABLE }
            row.forEachIndexed(fun(index: Int, square: Square) {
                if (notAtBorder(row, index)) {
                    val nextSquare = row[index + 1]
                    pairResult[index] = pairWinnability(square, nextSquare)
                }
            })
            rowResults[rowIndex] = pairResult
        }
        return squarePairsWinnable(rowResults)
    }

    private fun pairWinnability(square: Square, nextSquare: Square): Int {
        if (hasAnyWildcards(square, nextSquare)) {
            return nonWildcardNumber(square, nextSquare)
        } else {
            return determineIfPairWinnable(square, nextSquare)
        }
    }

    private fun nonWildcardNumber(square: Square, nextSquare: Square): Int {
        //TODO: protect against 4 wildcard triangles
        //TODO: return both sides of the non-wildcard square because either of the triangles could pair with the triangle
        return listOf(square, nextSquare).filter { it.left == WILDCARD_INDEX || it.right == WILDCARD_INDEX }[0].left
    }

    private fun squarePairsWinnable(rowResults: Array<Array<Int>>): Boolean {
        val allSquarePairPairs = mutableListOf<Boolean>()
        rowResults.forEachIndexed { rowIndex, row ->
            if (notAtBorder(row, rowIndex)) {
                row.forEachIndexed { index, winnableNumber ->
                    val squarePairBelowMe = rowResults[rowIndex + 1][index]
                    val thisSquareWinnable = squarePairPairWinnable(winnableNumber, squarePairBelowMe)
                    allSquarePairPairs.add(thisSquareWinnable)
                }
            }
        }
        return allSquarePairPairs.contains(true)
    }

    private fun squarePairPairWinnable(myPair: Int, squarePairBelowMe: Int): Boolean {
        if (myPair == NOT_WINNABLE || squarePairBelowMe == NOT_WINNABLE) {
            return false
        }
        return myPair == squarePairBelowMe || myPair == WILDCARD_INDEX || squarePairBelowMe == WILDCARD_INDEX
    }

    private fun hasAnyWildcards(left: Square, right: Square): Boolean {
        return listOf(left.left, left.right, right.left, right.right).contains(WILDCARD_INDEX)
    }

    private fun notAtBorder(row: Array<out Any>, index: Int): Boolean {
        return index + 1 < row.size
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
