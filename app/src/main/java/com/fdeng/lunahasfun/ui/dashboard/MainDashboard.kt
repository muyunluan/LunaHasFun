package com.fdeng.lunahasfun.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val SkyBlue = Color(0xFF4DB6AC)
val SunflowerYellow = Color(0xFFFFF176)
val MintGreen = Color(0xFF81C784)
val SoftOrange = Color(0xFFFFB74D)

@Composable
fun MainDashboard(
    onNavigateToMath: () -> Unit,
    onNavigateToMoney: () -> Unit,
    onNavigateToCalendar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SkyBlue)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "🌟 Luna Has Fun! 🌟",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = "Choose what you want to play:",
                fontSize = 20.sp,
                color = Color.White
            )

            Spacer(modifier = modifier.height(12.dp))

            // Math Mode Card
            DashboardCard(
                title = "➕ Math Fun",
                subtitle = "Practice 2-digit addition and subtraction",
                backgroundColor = SunflowerYellow,
                onClick = onNavigateToMath
            )

            // Money Mode Card
            DashboardCard(
                title = "🪙 Coin Counter",
                subtitle = "Count pennies, nickels, dimes & quarters",
                backgroundColor = MintGreen,
                onClick = onNavigateToMoney
            )

            // Calendar Mode Card
            DashboardCard(
                title = "📅 Calendar Magic",
                subtitle = "Learn dates and days of the week",
                backgroundColor = SoftOrange,
                onClick = onNavigateToCalendar
            )
        }
    }
}

@Composable
fun DashboardCard(
    title: String,
    subtitle: String,
    backgroundColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(110.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF263238)
            )
            Spacer(modifier = modifier.height(4.dp))
            Text(
                text = subtitle,
                fontSize = 16.sp,
                color = Color(0xFF37474F)
            )
        }
    }
}
