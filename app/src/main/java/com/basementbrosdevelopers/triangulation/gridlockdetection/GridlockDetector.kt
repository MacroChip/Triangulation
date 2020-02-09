package com.basementbrosdevelopers.triangulation.gridlockdetection

import com.basementbrosdevelopers.triangulation.GraphicsManager.WILDCARD_INDEX
import com.basementbrosdevelopers.triangulation.Square

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
        val pairResults: Array<List<Int>> = Array(row.size - 1) { listOf<Int>() }
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
            return winnableNumbers(square, nextSquare)
        } else {
            return determineIfPairWinnable(square, nextSquare)
        }
    }

    private fun squarePairsWinnable(rowResults: Array<Array<List<Int>>>, matrixSize: Int): Boolean {
        val allSquarePairPairs = mutableListOf<Boolean>()
        rowResults.forEachIndexed { rowIndex, row ->
            if (notAtBorder(matrixSize, rowIndex)) {
                row.forEachIndexed { index, winnableNumbers ->
                    if ((rowIndex % 2 == 0 && index % 2 == 0) || (rowIndex % 2 == 1 && index % 2 == 1)) {
                        val squarePairBelowMe = rowResults[rowIndex + 1][index]
                        val thisSquareWinnable = squarePairPairWinnable(winnableNumbers, squarePairBelowMe)
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
        if (myPairWinners.isEmpty() || squarePairBelowMeWinners.isEmpty()) {
            println("not winnable")
            return false
        }
        val hasWildcard = myPairWinners.contains(WILDCARD_INDEX) || squarePairBelowMeWinners.contains(WILDCARD_INDEX)
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

    private fun determineIfPairWinnable(left: Square, right: Square): List<Int> {
        return listOf(left.left, left.right).intersect(listOf(right.left, right.right)).toList()
    }
}

fun isInGridlock(matrix: Array<Array<Square>>): Boolean {
    return GridlockDetector().isInGridlock(matrix)
}
