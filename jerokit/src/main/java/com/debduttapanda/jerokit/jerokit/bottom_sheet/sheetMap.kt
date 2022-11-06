package com.debduttapanda.jerokit.jerokit.bottom_sheet

import androidx.compose.runtime.Composable

@Composable
fun sheetMap(): Map<Sheets, BottomSheetModel>{
    return LocalSheetMap.current
}
