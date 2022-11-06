package com.debduttapanda.jerokit.jerokit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

val localDesignWidth = compositionLocalOf { 360f }
@Composable
fun ep(
    dimension: Float,
    fullWidth: Float = 360f
): Float{
    val context = LocalContext.current
    val density = LocalDensity.current.density
    val widthPx = context.resources.displayMetrics.widthPixels
    val factor = dimension / fullWidth
    return widthPx * factor / density
}

@Composable
fun dep(
    dimension: Float,
    fullWidth: Float
): Dp {
    return ep(dimension, fullWidth).dp
}

@Composable
fun sep(
    dimension: Float,
    fullWidth: Float
): TextUnit {

    return with(LocalDensity.current) {
        ep(dimension, fullWidth).dp.toSp()
    }
}

@Composable
fun Number.dep(fullWidth: Number = localDesignWidth.current): Dp{
    return dep(dimension = toFloat(), fullWidth = fullWidth.toFloat())
}

@Composable
fun Number.sep(fullWidth: Number = localDesignWidth.current): TextUnit{
    return sep(dimension = toFloat(), fullWidth = fullWidth.toFloat())
}
