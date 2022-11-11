package com.debduttapanda.jerokit.jerokit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@OptIn(ExperimentalPermissionsApi::class)
class Permitter{
    private val _permissions = mutableStateListOf<String>()
    private val _request = mutableStateOf(false)

    suspend fun check(vararg permissions: String): MultiplePermissionsState? =
        suspendCancellableCoroutine {coroutine ->
            if (permissions.isEmpty()){
                coroutine.resume(null)
                return@suspendCancellableCoroutine
            }
            onResult = {
                onResult = {}
                _permissions.clear()
                coroutine.resume(it)
            }
            _permissions.addAll(permissions)
        }

    suspend fun request(vararg permissions: String): Map<String, Boolean>? =
        suspendCancellableCoroutine {coroutine ->
            if (permissions.isEmpty()){
                coroutine.resume(null)
                return@suspendCancellableCoroutine
            }
            onDisposition = {
                onDisposition = {}
                _permissions.clear()
                _request.value = false
                coroutine.resume(it)
            }
            _permissions.addAll(permissions)
            _request.value = true
        }

    private var onDisposition: (states: Map<String, Boolean>) -> Unit = {}
    private var onResult: (multiplePermissionsState: MultiplePermissionsState) -> Unit = {}

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun handlePermission() {
        if (_permissions.isNotEmpty()){
            val permissionState = rememberMultiplePermissionsState(
                _permissions
            ){
                onDisposition(it)
            }
            LaunchedEffect(key1 = permissionState){
                onResult(permissionState)
            }
            LaunchedEffect(key1 = _request){
                if (_request.value){
                    permissionState.launchMultiplePermissionRequest()
                }
            }
        }
    }
}
