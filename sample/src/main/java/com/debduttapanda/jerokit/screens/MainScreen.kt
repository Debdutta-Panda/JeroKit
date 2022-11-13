package com.debduttapanda.jerokit.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.debduttapanda.jerokit.DataIds
import com.debduttapanda.jerokit.jerokit.*
import com.debduttapanda.jerokit.viewModels.MyViewModel

@Composable
fun MainScreen(
    myText: String = stringState(key = "myText").value,
    myPermissionCheck: String = stringState(key = "permissionCheck").value,
    image: Any? = tState<Any?>(key = "image").value,
    notifier: NotificationService = myNotifier()
){
    ModalBottomSheetLayout() {
        Column(
            modifier = Modifier.fillMaxSize(),
        ){
            Text(myText)
            Button(onClick = {
                notifier.notify("change")
            }) {
                Text("Change")
            }
            Button(onClick = {
                notifier.notify("goto_a")
            }) {
                Text("Goto A")
            }
            Button(onClick = {
                notifier.notify("change_color")
            }) {
                Text("StatusBarColor")
            }
            Column(){
                Row(){
                    Button(onClick = {
                        notifier.notify("check_permission")
                    }) {
                        Text("CheckPermission")
                    }
                    Button(onClick = {
                        notifier.notify("request_permission")
                    }) {
                        Text("RequestPermission")
                    }
                }
                Text(myPermissionCheck)
            }
            Column {
                AsyncImage(
                    model = image,
                    contentDescription = "",
                    modifier = Modifier.size(50.dp),
                    contentScale = ContentScale.Fit,
                )
                Button(onClick = {
                    notifier.notify("image")
                }) {
                    Text("Take Image")
                }
            }
            Row(){
                Button(onClick = {
                    notifier.notify("sheet",1)
                }) {
                    Text("Sheet1")
                }
                Button(onClick = {
                    notifier.notify("sheet",2)
                }) {
                    Text("Sheet2")
                }
            }
            Column {
                Row(){
                    Button(onClick = {
                        notifier.notify("softInputMode",0)
                    }) {
                        Text("Pan")
                    }
                    Button(onClick = {
                        notifier.notify("softInputMode",1)
                    }) {
                        Text("Nothing")
                    }
                    Button(onClick = {
                        notifier.notify("softInputMode",2)
                    }) {
                        Text("Unspecified")
                    }
                    Button(onClick = {
                        notifier.notify("softInputMode",3)
                    }) {
                        Text("Resize")
                    }
                }
                Box(modifier =Modifier.height(100.dp))
                TextField(value = "", onValueChange = {})
            }
        }
    }
}

@Composable
fun MyPage(
    value: String = stringState(key = DataIds.value).value,
    notifier: NotificationService = myNotifier()
){
    Column(){
        Text(value)
        Button(
            onClick = {
                notifier.notify("value_click")
            }
        ) {
            Text("Click Me")
        }
        DeepUI()
    }
}

@Composable
fun DeepUI(
    value: String = stringState(key = DataIds.value1).value,
    notifier: NotificationService = myNotifier()
) {
    Column(){
        Text(value)
        Button(
            onClick = {
                notifier.notify("value_click1")
            }
        ) {
            Text("Click Me 1")
        }
    }
}
