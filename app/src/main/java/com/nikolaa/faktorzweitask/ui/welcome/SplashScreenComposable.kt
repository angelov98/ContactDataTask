package com.nikolaa.faktorzweitask.ui.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nikolaa.faktorzweitask.R
import com.nikolaa.faktorzweitask.view_model.WelcomeViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreenComposable(
    viewModel: WelcomeViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        delay(1000)
        viewModel.onFinishSplashScreen()
    }
    val backgroundColor = MaterialTheme.colorScheme.background
    val textColor = MaterialTheme.colorScheme.onBackground
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.ic_contact),
                contentDescription = "LogoImage",
                modifier = Modifier.size(112.dp),
                colorFilter = ColorFilter.tint(textColor)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Faktor Zwei Task",
                style = TextStyle(
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Black,
                    color = textColor
                )
            )
        }
    }
}