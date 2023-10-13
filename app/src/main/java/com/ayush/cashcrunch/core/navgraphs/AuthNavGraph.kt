package com.ayush.cashcrunch.core.navgraphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ayush.cashcrunch.core.Screen
import com.ayush.cashcrunch.presentation.auth.signin.SigninScreen
import com.ayush.cashcrunch.presentation.auth.signup.SignupScreen
import com.ayush.cashcrunch.presentation.splash.SplashScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = "AUTHENTICATION",
        startDestination = Screen.SplashScreen.route
    ) {
        composable(route = Screen.SigninScreen.route) {
            SigninScreen(navController = navController)
        }

        composable(route = Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }

        composable(route = Screen.SignupScreen.route) {
            SignupScreen(navController = navController)
        }
    }

}