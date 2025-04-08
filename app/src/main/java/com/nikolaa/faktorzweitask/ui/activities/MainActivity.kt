package com.nikolaa.faktorzweitask.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nikolaa.faktorzweitask.navigation.MainNavHost
import com.nikolaa.faktorzweitask.ui.theme.FaktorZweiTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FaktorZweiTaskTheme {
                val navController = rememberNavController()
                this.navController = navController
                MainNavHost(navController)
            }
        }
    }
}
