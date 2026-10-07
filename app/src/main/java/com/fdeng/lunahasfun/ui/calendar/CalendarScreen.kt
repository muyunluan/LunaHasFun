package com.fdeng.lunahasfun.ui.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fdeng.lunahasfun.ui.math.KeypadButton
import com.fdeng.lunahasfun.ui.math.MintGreen
import com.fdeng.lunahasfun.ui.math.SkyBlue
import com.fdeng.lunahasfun.ui.math.SoftOrange
import com.fdeng.lunahasfun.ui.math.SunflowerYellow

@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel,
    onBackToDashboard: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SkyBlue)
            .padding(24.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Bar
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onBackToDashboard,
                    colors = ButtonDefaults.buttonColors(containerColor = SunflowerYellow),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(text = "⬅️ Home", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                }
                Text(
                    text = "⭐ Score: ${uiState.score}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // Calendar Card
            val cardBg = when (uiState.feedbackState) {
                CalendarFeedbackState.CORRECT -> MintGreen
                CalendarFeedbackState.INCORRECT -> SoftOrange
                CalendarFeedbackState.IDLE -> Color.White
            }

            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 12.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = cardBg),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "📅 Calendar Magic",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF263238),
                        textAlign = TextAlign.Center
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Today is ${uiState.todayInfo.dayOfWeek}, ${uiState.todayInfo.date}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = modifier.height(16.dp))
                        Text(
                            text = uiState.currentProblem.questionText,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF263238),
                            textAlign = TextAlign.Center
                        )
                    }

                    Text(
                        text = "Target Day Number: ${uiState.userInput.ifEmpty { "?" }}",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF263238),
                        textAlign = TextAlign.Center
                    )

                    when (uiState.feedbackState) {
                        CalendarFeedbackState.CORRECT -> {
                            Text(
                                text = "🎉 Spot On!",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1B5E20)
                            )
                            Button(
                                onClick = { viewModel.nextProblem() },
                                colors = ButtonDefaults.buttonColors(containerColor = SunflowerYellow),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Text(text = "Next Date ➡️", fontSize = 18.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                            }
                        }
                        CalendarFeedbackState.INCORRECT -> {
                            Text(
                                text = "💪 Try Again!",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE65100)
                            )
                        }
                        CalendarFeedbackState.IDLE -> {
                            Text(
                                text = "Enter day number (e.g. 15)",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }

            // Keypad
            Column(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val buttons = listOf(
                    listOf(1, 2, 3),
                    listOf(4, 5, 6),
                    listOf(7, 8, 9),
                    listOf(-1, 0, -2)
                )
                for (row in buttons) {
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (btn in row) {
                            KeypadButton(
                                modifier = modifier
                                    .weight(1f)
                                    .height(56.dp),
                                text = when (btn) {
                                    -1 -> "C"
                                    -2 -> "✔"
                                    else -> btn.toString()
                                },
                                backgroundColor = when (btn) {
                                    -1 -> SoftOrange
                                    -2 -> MintGreen
                                    else -> SunflowerYellow
                                },
                                onClick = {
                                    when (btn) {
                                        -1 -> viewModel.onClearPressed()
                                        -2 -> {
                                            if (uiState.feedbackState == CalendarFeedbackState.CORRECT) {
                                                viewModel.nextProblem()
                                            } else {
                                                viewModel.onSubmitAnswer()
                                            }
                                        }
                                        else -> viewModel.onDigitPressed(btn)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
