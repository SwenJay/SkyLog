package com.example.skylog.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.skylog.ui.screens.LoginScreen
import com.example.skylog.ui.screens.RegisterScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onLoginClick = {
                    // login later
                },
                onRegisterClick = {
                    navController.navigate("register")
                }
            )
        }
        composable("register") {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate("login")
                }
            )
        }
    }
}