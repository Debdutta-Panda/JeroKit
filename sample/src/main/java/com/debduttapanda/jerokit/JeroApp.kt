package com.debduttapanda.jerokit

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.debduttapanda.jerokit.jerokit.JeroScreen
import com.debduttapanda.jerokit.screens.MainScreen
import com.debduttapanda.jerokit.screens.PageA
import com.debduttapanda.jerokit.viewModels.MainViewModel
import com.debduttapanda.jerokit.viewModels.PageAViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun JeroApp() {
    val navController = rememberAnimatedNavController()
    AnimatedNavHost(
        navController,
        startDestination = "main"
    ) {
        JeroScreen<MainViewModel>(
            navController = navController,
            route = Routes.main.full,
        ) {
            MainScreen()
        }
        JeroScreen<PageAViewModel>(
            navController = navController,
            route = Routes.pageA.full,
        ) {
            PageA()
        }
    }
}