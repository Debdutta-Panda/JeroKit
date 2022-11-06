package com.debduttapanda.jerokit.jerokit

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
fun BackHandle(
    suffix: String,
    notifier: NotificationService = notifier()
) {
    BackHandler(enabled = true, onBack = {
        notifier.notify("back_$suffix", null)
    })
}
