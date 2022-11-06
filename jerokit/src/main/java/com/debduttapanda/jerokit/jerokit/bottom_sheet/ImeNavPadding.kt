package com.debduttapanda.jerokit.jerokit.bottom_sheet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ImeNavPadding() {
    Box(
        modifier = Modifier
            .navigationBarsPadding()
            .imePadding()
    )
}