package com.arcticoss.nextplayer.feature.media.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.arcticoss.nextplayer.feature.media.settings.SettingsScreen

const val settingsNavigationRoute = "settings_route"

fun NavController.navigateToSettings(navOptions: NavOptions? = null) {
    this.navigate(settingsNavigationRoute, navOptions)
}

fun NavGraphBuilder.settingsScreen(navController: NavHostController) {
    composable(route = settingsNavigationRoute) {
        SettingsScreen(navController = navController)
    }
}