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

data class MoneyProblem(
    val coins: List<CoinInstance>,
    val expectedTotalCents: Int
)

object MoneyEngine {
    /**
     * Generates a random cluster of 3 to 8 US coins (each randomly heads or tails) and calculates the total cent value.
     */
    fun generateCoinCluster(): MoneyProblem {
        val count = Random.nextInt(3, 9)
        val availableTypes = listOf(CoinType.PENNY, CoinType.NICKEL, CoinType.DIME, CoinType.QUARTER)
        val coinInstances = mutableListOf<CoinInstance>()
        var total = 0
        repeat(count) {
            val randomType = availableTypes.random()
            val isHeads = Random.nextBoolean()
            val instance = CoinInstance(randomType, isHeads)
            coinInstances.add(instance)
            total += instance.centValue
        }
        return MoneyProblem(coins = coinInstances, expectedTotalCents = total)
    }
}
