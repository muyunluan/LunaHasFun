package com.fdeng.lunahasfun.engine

import com.fdeng.lunahasfun.R
import kotlin.random.Random

enum class CoinType(val centValue: Int, val displayName: String, val headsResId: Int, val tailsResId: Int) {
    PENNY(1, "Penny", R.drawable.penny_coin_heads_screen, R.drawable.penny_coin_tails_screen),
    NICKEL(5, "Nickel", R.drawable.nickel_coin_heads_screen, R.drawable.nickel_coin_tails_screen),
    DIME(10, "Dime", R.drawable.dime_coin_heads_screen, R.drawable.dime_coin_tails_screen),
    QUARTER(25, "Quarter", R.drawable.quarter_coin_heads_screen, R.drawable.quarter_coin_tail_screen)
}

data class CoinInstance(
    val type: CoinType,
    val isHeads: Boolean
) {
    val drawableResId: Int
        get() = if (isHeads) type.headsResId else type.tailsResId
    val centValue: Int
        get() = type.centValue
    val displayName: String
        get() = type.displayName
}

enum class MoneyProblemType {
    CENT_COUNTING,
    DOLLAR_CONVERSION
}

data class MoneyProblem(
    val coins: List<CoinInstance>,
    val expectedTotalCents: Int,
    val problemType: MoneyProblemType
)

object MoneyEngine {
    /**
     * Generates a random cluster of US coins and dynamically assigns either CENT_COUNTING or DOLLAR_CONVERSION.
     */
    fun generateCoinCluster(): MoneyProblem {
        val problemType = if (Random.nextBoolean()) MoneyProblemType.CENT_COUNTING else MoneyProblemType.DOLLAR_CONVERSION
        
        val availableTypes = listOf(CoinType.PENNY, CoinType.NICKEL, CoinType.DIME, CoinType.QUARTER)
        val coinInstances = mutableListOf<CoinInstance>()
        var total = 0

        if (problemType == MoneyProblemType.DOLLAR_CONVERSION) {
            // Ensure total is at least 100 cents ($1.00) for dollar conversion
            while (total < 100) {
                coinInstances.clear()
                total = 0
                val count = Random.nextInt(4, 9)
                repeat(count) {
                    val randomType = availableTypes.random()
                    val isHeads = Random.nextBoolean()
                    val instance = CoinInstance(randomType, isHeads)
                    coinInstances.add(instance)
                    total += instance.centValue
                }
            }
        } else {
            val count = Random.nextInt(3, 7)
            repeat(count) {
                val randomType = availableTypes.random()
                val isHeads = Random.nextBoolean()
                val instance = CoinInstance(randomType, isHeads)
                coinInstances.add(instance)
                total += instance.centValue
            }
        }

        return MoneyProblem(
            coins = coinInstances,
            expectedTotalCents = total,
            problemType = problemType
        )
    }
}
