package com.debduttapanda.jerokit.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.debduttapanda.jerokit.Routes
import com.debduttapanda.jerokit.jerokit.*

class PageAViewModel: WirelessViewModelInterface, ViewModel() {
    override val softInputMode = mutableStateOf(SoftInputMode.adjustNothing)
    override val resolver: Resolver = Resolver()
    override val notifier: NotificationService = NotificationService{id,arg->
        when(id){
            "back_${Routes.pageA.name}"->{
                navigation.scope { navHostController, lifecycleOwner, toaster ->
                    navHostController.popBackStack()
                }
            }
        }
    }
    override val navigation: MutableState<UIScope?> = Navigation()
    override val permissionHandler: PermissionHandler = PermissionHandler()
    override val resultingActivityHandler: ResultingActivityHandler = ResultingActivityHandler()
}