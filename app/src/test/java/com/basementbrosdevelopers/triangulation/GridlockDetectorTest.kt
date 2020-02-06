package com.basementbrosdevelopers.triangulation

import com.basementbrosdevelopers.triangulation.GraphicsManager.WILDCARD_INDEX
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

class GridlockDetectorTest {

    @Test
    fun `when every single triangle is a different color in a row then you are gridlocked`() {
        val matrix = arrayOf(
                arrayOf(Square(0, 1), Square(2, 3))
        )
        expectThat(GridlockDetector().isInGridlock(matrix)).isTrue()
    }

    @Test
    fun `every triangle is different but wildtriangle saves you from gridlock`() {
        val matrix = arrayOf(
                arrayOf(Square(0, 1), Square(2, WILDCARD_INDEX))
        )
        expectThat(GridlockDetector().isInGridlock(matrix)).isFalse()
    }

    @Test
    fun `not gridlocked`() {
        val sameColor = 1
        val matrix = arrayOf(
                arrayOf(Square(0, sameColor), Square(sameColor, 2))
        )
        expectThat(GridlockDetector().isInGridlock(matrix)).isFalse()
    }

    @Test
    fun `every single row must be gridlocked for the board to be gridlocked`() {
        val matrix = arrayOf(
                arrayOf(Square(0, 1), Square(2, 3)),
                arrayOf(Square(2, 3), Square(0, 1))
        )
        expectThat(GridlockDetector().isInGridlock(matrix)).isTrue()
    }

    @Test
    fun `one non gridlocked row means that the board might not be gridlocked`() {
        val rowWithPotential = arrayOf(Square(1, 1), Square(1, 1))
        val hopelessRow = arrayOf(Square(0, 1), Square(2, 3))
        val matrix = arrayOf(rowWithPotential, hopelessRow)

        expectThat(GridlockDetector().isInGridlock(matrix)).isFalse()
    }

    @Test
    fun `square pair below you must have the same winnable number as you for a square to be winnable`() {
        val matrix = arrayOf(
                arrayOf(Square(1, 1), Square(1, 1)),
                arrayOf(Square(2, 2), Square(2, 2))
        )
        expectThat(GridlockDetector().isInGridlock(matrix)).isTrue()
    }
}
