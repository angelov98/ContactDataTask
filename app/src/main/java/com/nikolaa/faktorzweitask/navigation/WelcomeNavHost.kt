package com.nikolaa.faktorzweitask.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nikolaa.faktorzweitask.ui.welcome.SplashScreenComposable
import com.nikolaa.faktorzweitask.util.NavigationConstants
import com.nikolaa.faktorzweitask.view_model.WelcomeViewModel

@Composable
fun WelcomeNavHost(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: WelcomeViewModel = hiltViewModel()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        startDestination = NavigationConstants.WELCOME_NAV_ROUTE
    ) {
        composable(NavigationConstants.WELCOME_NAV_ROUTE) {
            SplashScreenComposable(viewModel)
        }
    }
}