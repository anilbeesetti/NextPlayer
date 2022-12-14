package com.arcticoss.nextplayer.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.arcticoss.nextplayer.feature.settings.SettingsNavigateTo
import com.arcticoss.nextplayer.feature.settings.navigation.aboutScreen
import com.arcticoss.nextplayer.feature.settings.navigation.interfacePreferencesScreen
import com.arcticoss.nextplayer.feature.settings.navigation.navigateToAboutScreen
import com.arcticoss.nextplayer.feature.settings.navigation.navigateToInterfacePreferences
import com.arcticoss.nextplayer.feature.settings.navigation.navigateToPlayerPreferences
import com.arcticoss.nextplayer.feature.settings.navigation.playerPreferencesScreen
import com.arcticoss.nextplayer.feature.settings.navigation.settingsNavigationRoute
import com.arcticoss.nextplayer.feature.settings.navigation.settingsScreen

const val SETTINGS_ROUTE = "settings_nav_route"

fun NavGraphBuilder.settingsNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = settingsNavigationRoute,
        route = SETTINGS_ROUTE
    ) {
        settingsScreen(
            onNavigate = { navigateTo ->
                when (navigateTo) {
                    SettingsNavigateTo.Interface -> navController.navigateToInterfacePreferences()
                    SettingsNavigateTo.Player -> navController.navigateToPlayerPreferences()
                    SettingsNavigateTo.About -> navController.navigateToAboutScreen()
                }
            },
            onBackClick = {
                navController.popBackStack()
            }
        )
        interfacePreferencesScreen(
            onBackClick = {
                navController.popBackStack()
            }
        )
        playerPreferencesScreen(
            onBackClick = {
                navController.popBackStack()
            }
        )
        aboutScreen(
            onBackClick = {
                navController.popBackStack()
            }
        )
    }
}