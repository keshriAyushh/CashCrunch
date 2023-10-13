package com.ayush.cashcrunch.core.navgraphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ayush.cashcrunch.core.BottomBarScreen
import com.ayush.cashcrunch.presentation.main.add.AddTransactionScreen
import com.ayush.cashcrunch.presentation.main.history.HistoryScreen
import com.ayush.cashcrunch.presentation.main.home.HomeScreen
import com.ayush.cashcrunch.presentation.main.profile.ProfileScreen

@Composable
fun BottomNavGraph(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = BottomBarScreen.HomeScreen.route,
        route = "HOST"
    ) {
        composable(route = BottomBarScreen.HomeScreen.route) {
            HomeScreen()
        }
        composable(route = BottomBarScreen.AddTransactionScreen.route) {
            AddTransactionScreen()
        }
        composable(route = BottomBarScreen.ProfileScreen.route) {
            ProfileScreen(navController = navHostController)
        }
        composable(route = BottomBarScreen.HistoryScreen.route) {
            HistoryScreen()
        }
    }
}