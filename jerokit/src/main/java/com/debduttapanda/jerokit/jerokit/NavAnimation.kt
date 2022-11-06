package com.debduttapanda.jerokit.jerokit

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

data class NavAnimation @OptIn(ExperimentalAnimationApi::class) constructor(
    val duration: Int = 700,
    val enterTransition: (AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?)? = {
        slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(duration))

    },
    val exitTransition: (AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?)? = {
        slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(duration))
    },
    val popEnterTransition: (
        AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition?
    )? = {
        slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(duration))
    },
    val popExitTransition: (
        AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition?
    )? = {
        slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(duration))
    }
)