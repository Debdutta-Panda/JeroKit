package com.debduttapanda.jerokit.viewModels

import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.debduttapanda.jerokit.DataIds
import com.debduttapanda.jerokit.MySheets
import com.debduttapanda.jerokit.Routes
import com.debduttapanda.jerokit.jerokit.*
import com.debduttapanda.jerokit.jerokit.bottom_sheet.Sheets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MyViewModel: WirelessViewModel, ViewModel() {
    private val value = mutableStateOf("")
    private val value1 = mutableStateOf("")
    /////
    private var rand = Random(0)
    override val softInput = mutableStateOf(SoftInput.adjustNothing)
    override val resolver: Resolver = Resolver()
    override val pownav: MutableState<UIScope?> = PowerNavigation()
    override val permitter: Permitter = Permitter()
    override val resultar: Resultar = Resultar()
    override val notifier: NotificationService = NotificationService{ id, arg->
        when(id) {
            "value_click"->{
                value.value = "${rand.nextInt()}"
            }
            "value_click"->{
                value1.value = "${rand.nextInt()}"
            }
        }
    }
    init {
        resolver.addAll(
            DataIds.value to value,
            DataIds.value1 to value1,
        )
    }
}