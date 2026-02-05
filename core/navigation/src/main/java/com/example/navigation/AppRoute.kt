package com.example.navigation

interface AppRoute {
    val route: String
    fun createRoute(vararg args: String): String
}

abstract class BaseRoute(
    override val route: String
) : AppRoute {
    override fun createRoute(vararg args: String): String {
        return if (args.isEmpty()) route else "$route/${args.joinToString("/")}"
    }
}