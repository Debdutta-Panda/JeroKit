package com.debduttapanda.jerokit.jerokit

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

@OptIn(ExperimentalAnimationApi::class)
inline fun <reified T : ViewModel> NavGraphBuilder.JeroScreen(
    navController: NavHostController,
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    crossinline content: @Composable () -> Unit
){
    JeroComposable(
        route,
        arguments = arguments,
        deepLinks = deepLinks
    ){
        JeroPage(
            navController,
            suffix = route,
            wvm = viewModel<T>() as? WirelessViewModelInterface ?: return@JeroComposable
        ) {
            content()
        }
    }
}