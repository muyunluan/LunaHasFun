package com.fdeng.lunahasfun

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fdeng.lunahasfun.ui.calendar.CalendarScreen
import com.fdeng.lunahasfun.ui.calendar.CalendarViewModel
import com.fdeng.lunahasfun.ui.dashboard.MainDashboard
import com.fdeng.lunahasfun.ui.math.MathScreen
import com.fdeng.lunahasfun.ui.math.MathViewModel
import com.fdeng.lunahasfun.ui.math.MintGreen
import com.fdeng.lunahasfun.ui.math.SunflowerYellow
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
            var showSettingsDialog by remember { mutableStateOf(false) }
            val mathState by mathViewModel.uiState.collectAsState()

            Surface(modifier = Modifier.fillMaxSize()) {
                when (currentScreen) {
                    is AppScreen.Dashboard -> {
                        MainDashboard(
                            onNavigateToMath = { currentScreen = AppScreen.Math },
                            onNavigateToMoney = { currentScreen = AppScreen.Money },
                            onNavigateToCalendar = { currentScreen = AppScreen.Calendar },
                            onOpenSettings = { showSettingsDialog = true }
                        )

                        if (showSettingsDialog) {
                            SettingsDialog(
                                currentTotal = mathState.totalQuestions,
                                onTotalSelected = { count ->
                                    mathViewModel.setTotalQuestions(count)
                                },
                                onDismiss = { showSettingsDialog = false }
                            )
                        }
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

@Composable
fun SettingsDialog(
    currentTotal: Int,
    onTotalSelected: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "⚙️ Math Quiz Settings", fontWeight = FontWeight.Bold, color = Color(0xFF263238)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(text = "Select total questions per math round:", color = Color(0xFF37474F))
                val options = listOf(10, 15, 20, 25, 30)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    for (opt in options) {
                        Button(
                            onClick = { onTotalSelected(opt) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (opt == currentTotal) MintGreen else SunflowerYellow
                            ),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Text(text = "$opt", fontWeight = FontWeight.Bold, color = Color.Black)
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = SunflowerYellow),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Done", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        },
        containerColor = Color.White,
        shape = RoundedCornerShape(24.dp)
    )
}
