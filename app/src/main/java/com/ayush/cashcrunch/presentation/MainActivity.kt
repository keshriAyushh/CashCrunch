package com.ayush.cashcrunch.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.ayush.cashcrunch.core.BottomBarScreen
import com.ayush.cashcrunch.core.Screen
import com.ayush.cashcrunch.core.navgraphs.RootNavGraph
import com.ayush.cashcrunch.presentation.auth.signin.SigninScreen
import com.ayush.cashcrunch.presentation.auth.signup.SignupScreen
import com.ayush.cashcrunch.presentation.main.add.AddTransactionScreen
import com.ayush.cashcrunch.presentation.main.home.HomeScreen
import com.ayush.cashcrunch.presentation.main.host.HostScreen
import com.ayush.cashcrunch.presentation.main.profile.ProfileScreen
import com.ayush.cashcrunch.presentation.splash.SplashScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation(navController = rememberNavController())
        }
    }
}

@Composable
fun Navigation(navController: NavHostController) {
    RootNavGraph(navController = navController)

}
