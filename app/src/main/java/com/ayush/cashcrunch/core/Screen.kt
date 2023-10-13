package com.ayush.cashcrunch.core

sealed class Screen(
    val route: String
) {
    object SplashScreen : Screen("splash_screen")
    object SigninScreen : Screen("login_screen")
    object SignupScreen : Screen("signup_screen")
    object HostScreen: Screen("host_Screen")
}
