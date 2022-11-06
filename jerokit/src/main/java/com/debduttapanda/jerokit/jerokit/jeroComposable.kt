package com.debduttapanda.jerokit.jerokit

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable

@ExperimentalAnimationApi
fun NavGraphBuilder.JeroComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    navAnimation: NavAnimation = NavAnimation(),
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
){
    composable(
        route,
        arguments,
        deepLinks,
        navAnimation.enterTransition,
        navAnimation.exitTransition,
        navAnimation.popEnterTransition,
        navAnimation.popExitTransition,
        content
    )
}
