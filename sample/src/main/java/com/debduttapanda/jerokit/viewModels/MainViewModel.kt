package com.debduttapanda.jerokit.viewModels

import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.debduttapanda.jerokit.MySheets
import com.debduttapanda.jerokit.Routes
import com.debduttapanda.jerokit.jerokit.*
import com.debduttapanda.jerokit.jerokit.bottom_sheet.Sheeting
import com.debduttapanda.jerokit.jerokit.bottom_sheet.Sheets
import com.debduttapanda.sheets.Sheet1Model
import com.debduttapanda.sheets.Sheet2Model
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainViewModel: WirelessViewModel, ViewModel() {
    var obj: Random = Random()
    val colors = listOf(
        Color.Red,
        Color.Green,
        Color.Blue,
        Color.Yellow,
        Color.Magenta,
        Color.Cyan,
        Color.White,
        Color.Black,
        Color.Gray,
        Color.DarkGray,
        Color.LightGray,
    )
    ////////
    override val softInput = mutableStateOf(SoftInput.adjustNothing)
    override val resolver: Resolver = Resolver()
    @OptIn(ExperimentalPermissionsApi::class)
    override val notifier: NotificationService = NotificationService{ id, arg->
        when(id) {
            "change"->{
                myText.value = "${++count}"
            }
            "change_color"->{
                obj.setSeed(System.currentTimeMillis())
                val rand_num: Int = obj.nextInt(colors.size - 1)
                val color = colors[rand_num]
                statusBarColor.value = StatusBarColor(
                    color = color,
                    darkIcons = true
                )
            }
            "goto_a"->{
                pownav.scope { navHostController, lifecycleOwner, toaster ->
                    navHostController.navigate(Routes.pageA.name)
                    toaster?.toast("Hello")
                    toaster?.toast("Hello", Toast.LENGTH_LONG)
                }
            }
            "image"->{
                viewModelScope.launch(Dispatchers.Main) {
                    val result = resultar.takePicturePreview()
                    withContext(Dispatchers.Main){
                        image.value = result
                    }
                }
            }
            "sheet"->{
                when(arg){
                    1->{
                        mySheeting.change(MySheets.Sheet1)
                        mySheeting.show()
                    }
                    2->{
                        mySheeting.change(MySheets.Sheet2)
                        mySheeting.show()
                    }
                }
            }
            "check_permission"->{
                viewModelScope.launch(Dispatchers.Main) {
                    val result = permitter.check(android.Manifest.permission.CAMERA)
                    val granted = result?.allPermissionsGranted==true
                    myPermissionCheck.value = "CheckResult: $granted"
                }
            }
            "request_permission"->{
                viewModelScope.launch(Dispatchers.Main) {
                    val result = permitter.request(android.Manifest.permission.CAMERA)
                    val granted = result?.get(android.Manifest.permission.CAMERA)==true
                    myPermissionCheck.value = "RequestResult: $granted"
                }
            }
            "softInputMode"->{
                when(arg){
                    0->softInput.value = SoftInput.adjustPan
                    1->softInput.value = SoftInput.adjustNothing
                    2->softInput.value = SoftInput.adjustUnspecified
                    3->softInput.value = SoftInput.adjustResize
                }
            }
            "back_${Routes.main.name}"->{
                if(mySheeting.sheets.value==Sheets.None){
                    pageBack()
                }
                else{
                    mySheeting.onBack()
                }
            }
        }
    }

    private fun pageBack() {
        pownav.scope { navHostController, lifecycleOwner, toaster ->
            navHostController.popBackStack()
        }
    }

    val statusBarColor = mutableStateOf<StatusBarColor?>(null)
    override val pownav: MutableState<UIScope?> = PowerNavigation()
    override val permitter: Permitter = Permitter()
    override val resultar: Resultar = Resultar()
    override val sheeting = Sheeting(
        sheetMap = mapOf(
            MySheets.Sheet1 to Sheet1Model(
                object: Sheet1Model.Callback{
                    override fun scope(): CoroutineScope {
                        return viewModelScope
                    }

                    override fun close() {
                        mySheeting.hide()
                    }

                    override fun switch() {
                        mySheeting.change(MySheets.Sheet2)
                    }
                }
            ),
            MySheets.Sheet2 to Sheet2Model(
                object: Sheet2Model.Callback{
                    override fun scope(): CoroutineScope {
                        return viewModelScope
                    }

                    override fun close() {
                        mySheeting.hide()
                    }

                    override fun switch() {
                        mySheeting.change(MySheets.Sheet1)
                    }
                }
            )
        ),
        onVisibilityChanged = {
            if(!it){
                mySheeting.change(Sheets.None)
            }
        }
    )
    ////////////////////////////////////////////////////////////////
    private var count = 0
    private val myText = mutableStateOf("$count")
    private val myPermissionCheck = mutableStateOf("CheckResult")
    private val image = mutableStateOf<Any?>(null)
    ////////////////////////////////////////////////////////////////
    init {
        resolver.addAll(
            "myText" to myText,
            StatusBarColorId to statusBarColor,
            "permissionCheck" to myPermissionCheck,
            "image" to image
        )
    }
}