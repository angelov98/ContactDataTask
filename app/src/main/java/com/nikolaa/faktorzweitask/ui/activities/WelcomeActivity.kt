package com.nikolaa.faktorzweitask.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nikolaa.faktorzweitask.navigation.WelcomeNavHost
import com.nikolaa.faktorzweitask.ui.theme.FaktorZweiTaskTheme
import com.nikolaa.faktorzweitask.util.EnumClasses
import com.nikolaa.faktorzweitask.view_model.WelcomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WelcomeActivity : AppCompatActivity() {
    private var navController: NavController? = null
    private val welcomeViewModel: WelcomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FaktorZweiTaskTheme {
                val navController = rememberNavController()
                this.navController = navController
                WelcomeNavHost(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController,
                    welcomeViewModel
                )
            }
        }

        welcomeViewModel.navigationEvent.observe(this) { event ->
            event?.let {
                when (it) {
                    EnumClasses.NavigationEvent.NavigateToMainNavigation -> {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }
}