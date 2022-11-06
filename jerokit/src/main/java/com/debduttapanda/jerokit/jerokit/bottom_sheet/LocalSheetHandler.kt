package com.debduttapanda.jerokit.jerokit.bottom_sheet

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

@OptIn(ExperimentalMaterialApi::class)
val LocalSheetHandler = compositionLocalOf {
    SheetHandler(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
        confirmStateChange = { false },
        onVisibilityChange = {}
    )
}

@Composable
fun localSheetHandler(): SheetHandler {
    return LocalSheetHandler.current
}
