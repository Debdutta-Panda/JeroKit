package com.debduttapanda.jerokit.jerokit

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class ResultingActivityHandler {
    private var _callback = mutableStateOf<(@Composable () -> Unit)?>(null)

    suspend fun takePicturePreview(
        maxTry: Int = 10,
        millis: Long = 200
    ): Bitmap?{
        return request(
            ActivityResultContracts
                .TakePicturePreview(),
            maxTry,
            millis
        ){
            it.launch()
        }
    }
    suspend fun getContent(
        type: String,
        maxTry: Int = 10,
        millis: Long = 200
    ): Uri?{
        return request(
            ActivityResultContracts.GetContent(),
            maxTry,
            millis
        ){
            it.launch(type)
        }
    }

    suspend fun <I, O> request(
        contract: ActivityResultContract<I, O>,
        maxTry: Int = 10,
        millis: Long = 200,
        launcher: (ManagedActivityResultLauncher<I, O>) -> Unit
    ): O? = suspendCancellableCoroutine {coroutine ->
        _callback.value = {
            val a = rememberLauncherForActivityResult(
                contract
            ) {
                coroutine.resume(it)
                _callback.value = null
                return@rememberLauncherForActivityResult
            }

            LaunchedEffect(a){
                var tried = 0
                var tryOn = true
                while (tryOn){
                    ++tried
                    delay(millis)
                    try {
                        launcher(a)
                        tryOn = false
                    } catch (e: Exception) {
                        if (tried > maxTry){
                            tryOn = false
                            coroutine.resume(null)
                            _callback.value = null
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun handle() {
        if (_callback.value != null){
            _callback.value?.invoke()
        }
    }
}
