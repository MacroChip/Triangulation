package com.basementbrosdevelopers.triangulation

import com.basementbrosdevelopers.triangulation.GraphicsManager.WILDCARD_INDEX

private const val NOT_WINNABLE = -1

class GridlockDetector {

    fun isInGridlock(matrix: Array<Array<Square>>): Boolean {
        var rowResults: Array<Array<Int>> = Array(matrix.size) { arrayOf<Int>() }
        matrix.forEach { row ->
            val pairResult: Array<Int> = Array(row.size - 1) { NOT_WINNABLE }
            row.forEachIndexed(fun(index: Int, square: Square) {
                if (notAtBorder(row, index)) {
                    if (hasAnyWildcards(square, row[index + 1])) {
                        pairResult[index] = WILDCARD_INDEX
                    } else {
                        pairResult[index] = determineIfPairWinnable(square, row[index + 1])
                    }
                }
            })
            rowResults = rowResults.plus(pairResult)
        }
        return rowResults.flatten().all { it == NOT_WINNABLE }
    }

    private fun hasAnyWildcards(left: Square, right: Square): Boolean {
        return listOf(left.left, left.right, right.left, right.right).contains(WILDCARD_INDEX)
    }

    private fun notAtBorder(row: Array<Square>, index: Int): Boolean {
        return index + 1 < row.size
    }

    private fun determineIfPairWinnable(left: Square, right: Square): Int {
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
