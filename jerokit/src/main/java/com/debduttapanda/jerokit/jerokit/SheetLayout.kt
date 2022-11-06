package com.debduttapanda.jerokit.jerokit

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.debduttapanda.jerokit.jerokit.bottom_sheet.Sheeting
import com.debduttapanda.jerokit.jerokit.bottom_sheet.sheetContent
import com.debduttapanda.jerokit.jerokit.bottom_sheet.sheeting

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun ModalBottomSheetLayout(
    scrimColor: Color = Color(0x8C243257),
    sheetBackgroundColor: Color = Color.White,
    sheetShape: Shape = RoundedCornerShape(
        topStart = 33.dep(),
        topEnd = 33.dep()
    ),
    sheeting: Sheeting = sheeting(),
    pageContent: @Composable () -> Unit
) {
    Log.d("fldfldlf","${sheeting.sheets.value}")
    val sheetState = sheeting.sheetHandler.handle()
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            sheeting.sheetContent()
        },
        scrimColor = scrimColor,
        sheetBackgroundColor = sheetBackgroundColor,
        sheetShape = sheetShape
    ) {
        pageContent()
    }
}