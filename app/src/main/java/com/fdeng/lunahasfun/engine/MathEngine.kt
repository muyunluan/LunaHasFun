package com.fdeng.lunahasfun.engine

import kotlin.random.Random

data class MathProblem(
    val num1: Int,
    val num2: Int,
    val expectedAnswer: Int,
    val isAddition: Boolean
)

object MathEngine {
    /**
     * Generates a 2-digit addition problem where both numbers are between 10 and 99.
     */
    fun generateAddition(): MathProblem {
        val num1 = Random.nextInt(10, 100)
        val num2 = Random.nextInt(10, 100)
        return MathProblem(
            num1 = num1,
            num2 = num2,
            expectedAnswer = num1 + num2,
            isAddition = true
        )
    }

    /**
     * Generates a 2-digit subtraction problem where num1 >= num2 to prevent negative answers.
     */
    fun generateSubtraction(): MathProblem {
        var num1 = Random.nextInt(10, 100)
        var num2 = Random.nextInt(10, 100)
        if (num1 < num2) {
            val temp = num1
            num1 = num2
            num2 = temp
        }
        return MathProblem(
            num1 = num1,
            num2 = num2,
            expectedAnswer = num1 - num2,
            isAddition = false
        )
    }
}
