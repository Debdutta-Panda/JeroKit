package com.debduttapanda.jerokit

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.debduttapanda.jerokit.jerokit.localDesignWidth
import com.debduttapanda.jerokit.ui.theme.JerokitTheme

open class JeroActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            JerokitTheme {
                CompositionLocalProvider(
                    localDesignWidth provides 360f,
                ) {
                    Surface(
                        modifier = Modifier
                            .systemBarsPadding()
                            .fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        JeroApp()
                    }
                }
            }
        }
    }
}