package com.example.api.presentation.navigation

import com.example.navigation.BaseRoute

sealed class AuthDestination(
    route: String
): BaseRoute(route) {
    object Login : AuthDestination(route = "auth/login")
    object Registration : AuthDestination(route = "auth/registration")

    data class Main(val name: String): AuthDestination(route = "auth/main") {
        override fun createRoute(vararg args: String): String = "$route/$name"
    }

}