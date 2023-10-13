package com.ayush.cashcrunch.core.navgraphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ayush.cashcrunch.core.Screen
import com.ayush.cashcrunch.presentation.main.host.HostScreen

@Composable
fun RootNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "AUTHENTICATION",
        route = "ROOT"
    ) {
        authNavGraph(navController)

        composable(route = Screen.HostScreen.route) {
            HostScreen(navController = rememberNavController())
        }

    }
}