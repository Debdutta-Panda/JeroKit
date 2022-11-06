package com.debduttapanda.jerokit.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PageA(){
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text("PageA")
    }
}