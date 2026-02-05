package com.example.navigation

import androidx.navigation.NavController

interface IAppNavigator {
    fun navigateTo(route: AppRoute)
    fun popBackStack()
}

class AppNavigatorImpl(
    private val navController: NavController
): IAppNavigator {
    override fun navigateTo(route: AppRoute) {
        navController.navigate(route.createRoute())
    }

    override fun popBackStack() {
        navController.popBackStack()
    }
}