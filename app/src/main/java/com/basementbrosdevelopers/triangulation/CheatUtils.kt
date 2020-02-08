package com.basementbrosdevelopers.triangulation

class CheatUtils {
    fun cheat(): Array<Array<Square>> {
        return arrayOf(
                arrayOf(Square(0, 1), Square(2, 3), Square(3, 4), Square(2, 0)),
                arrayOf(Square(0, 1), Square(2, 3), Square(3, 4), Square(2, 0))
        )
    }
}