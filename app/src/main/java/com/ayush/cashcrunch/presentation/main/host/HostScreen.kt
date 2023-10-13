package com.ayush.cashcrunch.presentation.main.host

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ayush.cashcrunch.core.BottomBarScreen
import com.ayush.cashcrunch.core.navgraphs.BottomNavGraph
import com.ayush.cashcrunch.presentation.ui.theme.MatteBlack

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HostScreen(
    navController: NavHostController,
) {
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        BottomNavGraph(navController)
    }
}

@Composable
fun BottomBar(navController: NavController) {
    val screens = listOf(
        BottomBarScreen.HomeScreen,
        BottomBarScreen.HistoryScreen,
        BottomBarScreen.AddTransactionScreen,
        BottomBarScreen.ProfileScreen
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = MatteBlack,
        contentColor = Color.Gray,
        modifier = Modifier.clip(
            RoundedCornerShape(
                topStart = 10.dp, topEnd = 10.dp
            )
        )
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navHostController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navHostController: NavController
) {

    val isSelected = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true
    NavigationBarItem(
        selected = isSelected,
        onClick = { navHostController.navigate(screen.route) },
        icon = {
            Icon(
                painter = painterResource(id = screen.icon),
                contentDescription = "bottom_bar_icon",
                modifier = Modifier.rotate(
                    if(screen.route == BottomBarScreen.HistoryScreen.route) 270f else 0f
                )
            )
        },
        label = {
            Text(text = screen.title)
        },
        colors = NavigationBarItemDefaults.colors(
            selectedTextColor = Color.White,
            selectedIconColor = Color.Black,
            indicatorColor = Color.White,
            unselectedIconColor = Color.Gray
        ),
        alwaysShowLabel = false
    )
}