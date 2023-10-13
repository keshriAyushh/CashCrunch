package com.ayush.cashcrunch.core

import androidx.annotation.DrawableRes
import com.ayush.cashcrunch.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    @DrawableRes val icon: Int
) {
    object HomeScreen: BottomBarScreen(
        route = "home_screen",
        title = "Home",
        icon = R.drawable.home
    )

    object ProfileScreen: BottomBarScreen(
        route = "profile_screen",
        title = "Profile",
        icon = R.drawable.profile
    )

    object HistoryScreen: BottomBarScreen(
        route = "history_screen",
        title = "History",
        icon = R.drawable.statistics
    )

    object AddTransactionScreen: BottomBarScreen(
        route = "add_transaction_screen",
        title = "Add",
        icon = R.drawable.plus
    )

    object NotificationScreen: BottomBarScreen(
        route = "notifications_screen",
        title = "Notifications",
        icon = R.drawable.notification
    )
}


