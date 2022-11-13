package com.debduttapanda.jerokit.jerokit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val localDesignWidth = compositionLocalOf { 360f }
private var localDimensionFactor = 1f
private var savedDensity = 1f
private var savedFontScale = 1f
private var inverseSavedFontScale = 1f

fun ep(
    dimension: Float,
): Float{
    return dimension * localDimensionFactor
}

fun Dp.toMySp(): TextUnit = (value * inverseSavedFontScale).sp

val Number.dep: Dp get(){
    return ep(dimension = toFloat()).dp
}

val Number.depx: Float get() {
    return this.dep.value * savedDensity
}

val Any?.depx: Float get() {
    return if(this is Number) this.dep.value * savedDensity else 0f
}

val Any?.adep: Dp get() {
    return if(this is Number) this.dep else 0.dp
}

val Number.sep get() = ep(dimension = toFloat()).dp.toMySp()

@Composable
fun InitializeMetrics() {
    val screenWidthDp = LocalConfiguration.current.screenWidthDp
    val designWidth = localDesignWidth.current
    val density = LocalDensity.current

    savedDensity = density.density
    savedFontScale = density.fontScale
    inverseSavedFontScale = 1f/ savedFontScale
    localDimensionFactor = screenWidthDp / designWidth
}

operator fun Dp.times(number: Number): Dp {
    return (this.value * number.toFloat()).dp
}
operator fun Number.times(dp: Dp): Dp {
    return (this.toFloat() * dp.value).dp
}

operator fun TextUnit.times(number: Number): TextUnit {
    return (this.value * number.toFloat()).sp
}
operator fun Number.times(sp: TextUnit): TextUnit {
    return (this.toFloat() * sp.value).sp
}