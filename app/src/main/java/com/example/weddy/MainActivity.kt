package com.example.weddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.api.presentation.navigation.AuthDestination
import com.example.impl.MainScreen
import com.example.impl.di.AuthComponent
import com.example.impl.di.LocalAuthViewModelFactory
import com.example.impl.presentation.screens.LoginScreen
import com.example.impl.presentation.screens.RegistrationScreen
import com.example.navigation.AppNavigatorImpl
import com.example.weddy.ui.theme.WeddyTheme

class MainActivity : ComponentActivity() {

    private val authComponent: AuthComponent
        get() = (application as WeddyApplication).authComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeddingApp(authComponent = authComponent)
        }
    }
}

@Composable
fun WeddingApp(authComponent: AuthComponent) {  // ← Добавь параметр
    WeddyTheme {
        val navController = rememberNavController()
        val appNavigator = remember { AppNavigatorImpl(navController) }

        // Фабрика из компонента
        val authViewModelFactory = authComponent.authViewModelFactory()

        CompositionLocalProvider(
            LocalAuthViewModelFactory provides authViewModelFactory  // ← Предоставляем фабрику
        ) {
            NavHost(
                navController = navController,
                startDestination = AuthDestination.Login.route
            ) {
                composable(AuthDestination.Login.route) {
                    LoginScreen(
                        onRegisterScreenClick = { appNavigator.navigateTo(AuthDestination.Registration) },
                        onMainScreenClick = { name ->
                            appNavigator.navigateTo(
                                AuthDestination.Main(
                                    name
                                )
                            )
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                composable(AuthDestination.Registration.route) {
                    RegistrationScreen(
                        onLoginScreenClick = { appNavigator.popBackStack() },
                        onMainScreenClick = { name ->
                            appNavigator.navigateTo(
                                AuthDestination.Main(
                                    name
                                )
                            )
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                composable(
                    route = "${AuthDestination.Main("").route}/{name}",
                    arguments = listOf(navArgument("name") {
                        type = NavType.StringType; defaultValue = "Гость"
                    })
                ) { backStackEntry ->
                    val name = backStackEntry.arguments?.getString("name") ?: "Гость"
                    MainScreen(name = name)
                }
            }
        }
    }
}