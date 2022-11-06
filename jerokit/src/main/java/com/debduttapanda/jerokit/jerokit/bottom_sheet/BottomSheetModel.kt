package com.debduttapanda.jerokit.jerokit.bottom_sheet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import com.debduttapanda.jerokit.jerokit.LocalNotificationService
import com.debduttapanda.jerokit.jerokit.LocalResolver
import com.debduttapanda.jerokit.jerokit.NotificationService
import com.debduttapanda.jerokit.jerokit.Resolver
import kotlinx.coroutines.CoroutineScope

interface BottomSheetModel{
    val resolver: Resolver
    val notifier: NotificationService
    val scope: CoroutineScope

    @Composable fun Content()
    fun initialize()
    fun clear()
    fun onBack()

    @Composable
    operator fun invoke(){
        CompositionLocalProvider(
            LocalResolver provides resolver,
            LocalNotificationService provides notifier
        ) {
            Content()
        }
    }

    fun onVisibilityChange(it: Boolean)
}
