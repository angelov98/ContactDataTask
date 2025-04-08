package com.nikolaa.faktorzweitask.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nikolaa.faktorzweitask.ui.user_details.ContactDetailsScreenComposable
import com.nikolaa.faktorzweitask.ui.user_list.ContactScreenComposable
import com.nikolaa.faktorzweitask.util.ApiConstants.USERS_BY_ID_PATH
import com.nikolaa.faktorzweitask.util.NavigationConstants.CONTACTS_NAV_ROUTE
import com.nikolaa.faktorzweitask.util.NavigationConstants.CONTACT_DETAILS_NAV_ROUTE

@Composable
fun MainNavHost(
    navController: NavHostController,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentColor = Color.Transparent
    ) { innerPadding ->
        NavHost(
            modifier = Modifier
                .padding(innerPadding)
                .background(color = Color.Transparent)
                .fillMaxSize(),
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            },
            navController = navController,
            startDestination = CONTACTS_NAV_ROUTE
        ) {
            composable(CONTACTS_NAV_ROUTE) {
                ContactScreenComposable(navController)
            }

            composable(
                route = "$CONTACT_DETAILS_NAV_ROUTE/{$USERS_BY_ID_PATH}",
                arguments = listOf(navArgument(USERS_BY_ID_PATH) {
                    type = NavType.IntType
                })
            ) {
                val id = it.arguments?.getInt(USERS_BY_ID_PATH)
                if (id != null) {
                    ContactDetailsScreenComposable(navController, id)
                }
            }
        }
    }
}
