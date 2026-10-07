package com.fdeng.lunahasfun.engine

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MathEngineTest {

    @Test
    fun testGenerateAddition_rangeAndAnswer() {
        repeat(100) {
            val problem = MathEngine.generateAddition()
            assertTrue("num1 (${problem.num1}) should be between 10 and 99", problem.num1 in 10..99)
            assertTrue("num2 (${problem.num2}) should be between 10 and 99", problem.num2 in 10..99)
            assertEquals("expectedAnswer should equal num1 + num2", problem.num1 + problem.num2, problem.expectedAnswer)
            assertTrue("isAddition should be true", problem.isAddition)
        }
    }

    @Test
    fun testGenerateSubtraction_rangeAndNoNegative() {
        repeat(100) {
            val problem = MathEngine.generateSubtraction()
            assertTrue("num1 (${problem.num1}) should be between 10 and 99", problem.num1 in 10..99)
            assertTrue("num2 (${problem.num2}) should be between 10 and 99", problem.num2 in 10..99)
            assertTrue("num1 (${problem.num1}) should be >= num2 (${problem.num2})", problem.num1 >= problem.num2)
            assertEquals("expectedAnswer should equal num1 - num2", problem.num1 - problem.num2, problem.expectedAnswer)
            assertFalse("isAddition should be false", problem.isAddition)
        }
    }
}
