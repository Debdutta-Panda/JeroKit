package com.debduttapanda.jerokit.jerokit.bottom_sheet

import androidx.compose.runtime.Composable

@Composable
fun sheeting(): Sheeting {
    return LocalSheeting.current
}
