package com.debduttapanda.jerokit.jerokit.bottom_sheet

import androidx.compose.runtime.compositionLocalOf

val LocalSheetMap = compositionLocalOf { mapOf<Sheets, BottomSheetModel>() }
val LocalSheeting = compositionLocalOf { Sheeting() }