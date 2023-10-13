package com.ayush.cashcrunch.presentation.splash

import android.view.animation.OvershootInterpolator
import android.widget.Space
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ayush.cashcrunch.R
import com.ayush.cashcrunch.core.Screen
import com.ayush.cashcrunch.presentation.ui.theme.MatteBlack
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
) {

    val scale = remember {
        Animatable(0f)
    }

    val colorState = animateColorAsState(targetValue = if(!scale.isRunning) Color.Gray else MatteBlack, label = "color_transition")

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1.7f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(6f).getInterpolation(it)
                }
            )
        )
        delay(2500L)
        navController.navigate(Screen.SignupScreen.route)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize(1f)
            .background(MatteBlack)
            .padding(4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Cash Crunch",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.font2)),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.scale(scale.value)
            )
            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = "Budget Wiser",
                color = colorState.value,
                fontFamily = FontFamily(Font(R.font.grotesco_light)),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.scale(scale.value)
            )
        }

    }
}