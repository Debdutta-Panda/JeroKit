package com.debduttapanda.jerokit.jerokit

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController

data class Toaster(
    private val context: Context
){
    fun toast(message: String, duration: Int = Toast.LENGTH_SHORT){
        Toast.makeText(context, message, duration).show()
    }

    fun stringResource(@StringRes id: Int): String{
        return context.getString(id)
    }
}

typealias UIScope = suspend Bundle.(NavHostController, LifecycleOwner, Toaster?) -> Unit

fun MutableState<UIScope?>.scope(block: UIScope?){
    this.value = {navHostController, lifecycleOwner, toaster ->
        block?.invoke(
            navHostController.currentBackStackEntry?.arguments ?: Bundle(),
            navHostController,
            lifecycleOwner,
            toaster
        )
        this@scope.value = null
    }
}

suspend fun MutableState<UIScope?>.forward(
    navHostController: NavHostController,
    lifecycleOwner: LifecycleOwner,
    toaster: Toaster? = null
){
    this.value?.invoke(
        navHostController.currentBackStackEntry?.arguments ?: Bundle(),
        navHostController,
        lifecycleOwner,
        toaster
    )
}

fun Navigation() = mutableStateOf<UIScope?>(null)

fun NavHostController.set(
    route: String,
    key: String,
    value: Any?,
    last: Boolean = false
){
    if (last){
        backQueue
            .lastOrNull{ it.destination.route == route }
            ?.savedStateHandle
            ?.set(key, value)
    } else {
        backQueue
            .firstOrNull{ it.destination.route == route }
            ?.savedStateHandle
            ?.set(key, value)
    }
}

operator fun <T> NavHostController.get(key: String): T? {
    return currentBackStackEntry
        ?.savedStateHandle
        ?.get<T>(key)
}