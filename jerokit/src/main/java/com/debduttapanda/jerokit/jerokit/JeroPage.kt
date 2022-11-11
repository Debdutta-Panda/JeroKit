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
    wvm: WirelessViewModel,
    content: @Composable () -> Unit
) {
    wvm.permitter.handlePermission()
    wvm.resultar.handle()
    LaunchedEffect(key1 = Unit){
        wvm.notifier.notify(WirelessViewModel.startupNotification, null)
    }
    val owner = LocalLifecycleOwner.current
    val context = LocalContext.current
    LaunchedEffect(key1 = wvm.pownav.value){
        wvm.pownav.forward(navController, owner, Toaster(context))
    }
    // /////////
    val activity = LocalContext.current as Activity
    LaunchedEffect(key1 = wvm.softInput.value) {
        activity.window.setSoftInputMode(wvm.softInput.value)
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