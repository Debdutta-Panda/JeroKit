package com.debduttapanda.sheets

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.debduttapanda.jerokit.jerokit.NotificationService
import com.debduttapanda.jerokit.jerokit.Resolver
import com.debduttapanda.jerokit.jerokit.bottom_sheet.BottomSheetModel
import com.debduttapanda.jerokit.jerokit.bottom_sheet.SheetColumn
import com.debduttapanda.jerokit.jerokit.myNotifier
import com.debduttapanda.jerokit.jerokit.stringState
import kotlinx.coroutines.CoroutineScope

class Sheet2Model(val callback: Callback): BottomSheetModel {
    interface Callback{
        fun scope(): CoroutineScope
        fun close()
        fun switch()
    }
    /////////////////
    private val _resolver = Resolver()
    private val _notifier = NotificationService{id,arg->
        when(id){
            "text1"->{
                text1.value = arg as? String?:return@NotificationService
            }
            "text2"->{
                text2.value = arg as? String?:return@NotificationService
            }
            "button"->{
                text.value = "${text1.value} or ${text2.value}"
            }
            "done"->{
                callback.close()
            }
            "switch"->{
                callback.switch()
            }
        }
    }
    /////////////////
    override val resolver = _resolver
    override val notifier = _notifier
    override val scope get() = callback.scope()

    @Composable
    override fun Content() {
        Sheet2()
    }

    override fun initialize() {

    }

    override fun clear() {
        text.value = ""
        text1.value = ""
        text2.value = ""
    }

    override fun onBack() {
        callback.close()
    }

    override fun onVisibilityChange(it: Boolean) {
        clear()
    }

    ////////////
    private val text = mutableStateOf("")
    private val text1 = mutableStateOf("")
    private val text2 = mutableStateOf("")
    ////////////
    init {
        _resolver.addAll(
            "text" to text,
            "text1" to text1,
            "text2" to text2
        )
    }
}

@Composable
fun Sheet2(
    text: String = stringState(key = "text").value,
    text1: String = stringState(key = "text1").value,
    text2: String = stringState(key = "text2").value,
    notifier: NotificationService = myNotifier()
){
    SheetColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
    ){
        Text("Sheet2")
        Text(text)
        TextField(
            value = text1,
            onValueChange = {
                notifier.notify("text1",it)
            }
        )
        TextField(
            value = text2,
            onValueChange = {
                notifier.notify("text2",it)
            }
        )
        Row(){
            Button(onClick = {
                notifier.notify("button")
            }) {
                Text("Join")
            }
            Button(onClick = {
                notifier.notify("done")
            }) {
                Text("Done")
            }
            Button(onClick = {
                notifier.notify("switch")
            }) {
                Text("Switch")
            }
        }
    }
}