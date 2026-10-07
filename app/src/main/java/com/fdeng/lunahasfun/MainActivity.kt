package com.fdeng.lunahasfun

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fdeng.lunahasfun.ui.dashboard.MainDashboard
import com.fdeng.lunahasfun.ui.math.MathScreen
import com.fdeng.lunahasfun.ui.math.MathViewModel
import com.fdeng.lunahasfun.ui.math.SkyBlue
import com.fdeng.lunahasfun.ui.math.SunflowerYellow

sealed class AppScreen {
    object Dashboard : AppScreen()
    object Math : AppScreen()
    object Money : AppScreen()
    object Calendar : AppScreen()
}

class MainActivity : ComponentActivity() {
    private val mathViewModel: MathViewModel by viewModels()

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
                        PlaceholderScreen(
                            title = "🪙 Coin Counter\n(Coming Soon!)",
                            onBack = { currentScreen = AppScreen.Dashboard }
                        )
                    }
                    is AppScreen.Calendar -> {
                        PlaceholderScreen(
                            title = "📅 Calendar Magic\n(Coming Soon!)",
                            onBack = { currentScreen = AppScreen.Dashboard }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PlaceholderScreen(
    title: String,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SkyBlue)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = title,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(containerColor = SunflowerYellow),
                shape = RoundedCornerShape(16.dp),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
            ) {
                Text(text = "⬅️ Back to Dashboard", fontSize = 20.sp, color = Color.Black, fontWeight = FontWeight.Bold)
            }
        }
    }
}
