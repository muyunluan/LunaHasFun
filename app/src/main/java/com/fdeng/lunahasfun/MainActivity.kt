package com.fdeng.lunahasfun

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.fdeng.lunahasfun.ui.calendar.CalendarScreen
import com.fdeng.lunahasfun.ui.calendar.CalendarViewModel
import com.fdeng.lunahasfun.ui.dashboard.MainDashboard
import com.fdeng.lunahasfun.ui.math.MathScreen
import com.fdeng.lunahasfun.ui.math.MathViewModel
import com.fdeng.lunahasfun.ui.money.MoneyScreen
import com.fdeng.lunahasfun.ui.money.MoneyViewModel

sealed class AppScreen {
    object Dashboard : AppScreen()
    object Math : AppScreen()
    object Money : AppScreen()
    object Calendar : AppScreen()
}

class MainActivity : ComponentActivity() {
    private val mathViewModel: MathViewModel by viewModels()
    private val moneyViewModel: MoneyViewModel by viewModels()
    private val calendarViewModel: CalendarViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var currentScreen by remember { mutableStateOf<AppScreen>(AppScreen.Dashboard) }

            Surface(modifier = Modifier.fillMaxSize()) {
                when (currentScreen) {
                    is AppScreen.Dashboard -> {
                        MainDashboard(
                            onNavigateToMath = { currentScreen = AppScreen.Math },
                            onNavigateToMoney = { currentScreen = AppScreen.Money },
                            onNavigateToCalendar = { currentScreen = AppScreen.Calendar }
                        )
                    }
                    is AppScreen.Math -> {
                        MathScreen(
                            viewModel = mathViewModel,
                            onBackToDashboard = { currentScreen = AppScreen.Dashboard }
                        )
                    }
                    is AppScreen.Money -> {
                        MoneyScreen(
                            viewModel = moneyViewModel,
                            onBackToDashboard = { currentScreen = AppScreen.Dashboard }
                        )
                    }
                    is AppScreen.Calendar -> {
                        CalendarScreen(
                            viewModel = calendarViewModel,
                            onBackToDashboard = { currentScreen = AppScreen.Dashboard }
                        )
                    }
                }
            }
        }
    }
}
