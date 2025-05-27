package com.example.simplenote.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simplenote.R

@Composable
fun OnboardingScreen(onGetStarted: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Replace with actual onboarding image from drawable
        Image(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = null)
        Spacer(modifier = Modifier.height(24.dp))
        Text("Jot Down anything you want to achieve, today or in the future", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onGetStarted) {
            Text("Let’s Get Started")
        }
    }
}
