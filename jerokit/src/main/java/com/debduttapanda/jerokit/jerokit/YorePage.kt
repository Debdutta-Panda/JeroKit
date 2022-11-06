package com.debduttapanda.jerokit.jerokit

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.debduttapanda.jerokit.jerokit.bottom_sheet.LocalSheetHandler
import com.debduttapanda.jerokit.jerokit.bottom_sheet.LocalSheeting

@Composable
fun JeroPage(
    navController: NavHostController,
    suffix: String,
    wvm: WirelessViewModelInterface,
    content: @Composable () -> Unit
) {
    wvm.permissionHandler.handlePermission()
    wvm.resultingActivityHandler.handle()
    LaunchedEffect(key1 = Unit){
        wvm.notifier.notify(WirelessViewModelInterface.startupNotification, null)
    }
    val owner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = wvm.navigation.value){
        wvm.navigation.forward(navController, owner)
    }
    // /////////
    val activity = LocalContext.current as Activity
    LaunchedEffect(key1 = wvm.softInputMode.value) {
        activity.window.setSoftInputMode(wvm.softInputMode.value)
    }
    // /////////
    CompositionLocalProvider(
        LocalResolver provides wvm.resolver,
        LocalNotificationService provides wvm.notifier,
        LocalSheetHandler provides wvm.sheeting.sheetHandler,
        LocalSheeting provides wvm.sheeting
    ) {
        StatusBarColorControl()
        content()
        BackHandle(suffix)
    }
}