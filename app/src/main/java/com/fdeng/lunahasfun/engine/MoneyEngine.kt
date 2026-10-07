package com.fdeng.lunahasfun.engine

import com.fdeng.lunahasfun.R
import kotlin.random.Random

enum class Coin(val centValue: Int, val displayName: String, val drawableResId: Int) {
    PENNY(1, "Penny", R.drawable.ic_coin_penny),
    NICKEL(5, "Nickel", R.drawable.ic_coin_nickel),
    DIME(10, "Dime", R.drawable.ic_coin_dime),
    QUARTER(25, "Quarter", R.drawable.ic_coin_quarter)
}

data class MoneyProblem(
    val coins: List<Coin>,
    val expectedTotalCents: Int
)

object MoneyEngine {
    /**
     * Generates a random cluster of 3 to 8 US coins and calculates the total cent value.
     */
    fun generateCoinCluster(): MoneyProblem {
        val count = Random.nextInt(3, 9)
        val availableCoins = listOf(Coin.PENNY, Coin.NICKEL, Coin.DIME, Coin.QUARTER)
        val coinsList = mutableListOf<Coin>()
        var total = 0
        repeat(count) {
            val randomCoin = availableCoins.random()
            coinsList.add(randomCoin)
            total += randomCoin.centValue
        }
        return MoneyProblem(coins = coinsList, expectedTotalCents = total)
    }
}
