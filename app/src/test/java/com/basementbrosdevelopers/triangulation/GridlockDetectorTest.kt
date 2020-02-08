package com.basementbrosdevelopers.triangulation

import com.basementbrosdevelopers.triangulation.GraphicsManager.WILDCARD_INDEX
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isFalse
import strikt.assertions.isTrue

class GridlockDetectorTest {

    @Test
    fun `non gridlocked board`() {
        val matrix = arrayOf(
                arrayOf(Square(0, 1), Square(2, 1)),
                arrayOf(Square(0, 1), Square(2, 1))
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
    fun `square pair below you must have the same winnable number as you for a square to be winnable`() {
        val matrix = arrayOf(
                arrayOf(Square(1, 1), Square(1, 1)),
                arrayOf(Square(2, 2), Square(2, 2))
        )
        expectThat(GridlockDetector().isInGridlock(matrix)).isTrue()
    }

    @Test
    fun `tall board not gridlocked`() {
        val matrix = arrayOf(
                arrayOf(Square(1, 1), Square(1, 1)),
                arrayOf(Square(1, 1), Square(1, 1)),
                arrayOf(Square(0, 1), Square(2, 3)),
                arrayOf(Square(0, 1), Square(2, 3))
        )
        expectThat(GridlockDetector().isInGridlock(matrix)).isFalse()
    }

    @Test
    fun `not square tall board not gridlocked`() {
        val matrix = arrayOf(
                arrayOf(Square(0, 1), Square(2, 3)),
                arrayOf(Square(0, 1), Square(2, 3)),
                arrayOf(Square(0, 1), Square(2, 3)),
                arrayOf(Square(0, 1), Square(2, 3)),
                arrayOf(Square(1, 1), Square(1, 1)),
                arrayOf(Square(1, 1), Square(1, 1))
        )
        expectThat(GridlockDetector().isInGridlock(matrix)).isFalse()
    }

    @Test
    fun `tall board gridlocked`() {
        val matrix = arrayOf(
                arrayOf(Square(0, 1), Square(2, 3)),
                arrayOf(Square(0, 1), Square(2, 3)),
                arrayOf(Square(0, 1), Square(2, 3)),
                arrayOf(Square(0, 1), Square(2, 3))
        )
        expectThat(GridlockDetector().isInGridlock(matrix)).isTrue()
    }

    @Test
    fun `wide board not gridlocked`() {
        val matrix = arrayOf(
                arrayOf(Square(0, 1), Square(2, 3), Square(1, 1), Square(1, 1)),
                arrayOf(Square(0, 1), Square(2, 3), Square(1, 1), Square(1, 1)),
                arrayOf(Square(0, 1), Square(2, 3), Square(0, 1), Square(2, 3)),
                arrayOf(Square(0, 1), Square(2, 3), Square(0, 1), Square(2, 3))
        )
        expectThat(GridlockDetector().isInGridlock(matrix)).isFalse()
    }

    @Test
    fun `wide board gridlocked`() {
        val matrix = arrayOf(
                arrayOf(Square(0, 1), Square(2, 3), Square(0, 1), Square(2, 3)),
                arrayOf(Square(0, 1), Square(2, 3), Square(0, 1), Square(2, 3)),
                arrayOf(Square(0, 1), Square(2, 3), Square(0, 1), Square(2, 3)),
                arrayOf(Square(0, 1), Square(2, 3), Square(0, 1), Square(2, 3))
        )
        expectThat(GridlockDetector().isInGridlock(matrix)).isTrue()
    }

    @Test
    fun `board gridlocked because winning squares don't line up`() {
        val matrix = arrayOf(
                arrayOf(Square(0, 1), Square(2, 3), Square(3, 4), Square(2, 0)),
                arrayOf(Square(0, 1), Square(2, 3), Square(3, 4), Square(2, 0))
        )
        expectThat(GridlockDetector().isInGridlock(matrix)).isTrue()
    }

    @Test
    fun `bug where I thought filter took things out, not kept them`() {
        val matrix = arrayOf(
                arrayOf(Square(0, 1), Square(WILDCARD_INDEX, 0)),
                arrayOf(Square(2, 1), Square(2, 3))
        )
        expectThat(GridlockDetector().isInGridlock(matrix)).isTrue()
    }

    @Test
    fun `not gridlocked when left has wildcard but right does not`() {
        val matrix = arrayOf(
                arrayOf(Square(0, WILDCARD_INDEX), Square(0, 2)),
                arrayOf(Square(2, 1), Square(2, 3))
        )
        expectThat(GridlockDetector().isInGridlock(matrix)).isFalse()
    }

    @Test
    fun `4 wildcards`() {
        val matrix = arrayOf(
                arrayOf(Square(WILDCARD_INDEX, WILDCARD_INDEX), Square(WILDCARD_INDEX, WILDCARD_INDEX)),
                arrayOf(Square(2, 1), Square(2, 3))
        )
        expectThat(GridlockDetector().isInGridlock(matrix)).isFalse()
    }
    //TODO: multiple options per square. i.e. 12, 12
}
