package com.debduttapanda.jerokit.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.debduttapanda.jerokit.Routes
import com.debduttapanda.jerokit.jerokit.*

class PageAViewModel: WirelessViewModel, ViewModel() {
    override val softInput = mutableStateOf(SoftInput.adjustNothing)
    override val resolver: Resolver = Resolver()
    override val notifier: NotificationService = NotificationService{id,arg->
        when(id){
            "back_${Routes.pageA.name}"->{
                pownav.scope { navHostController, lifecycleOwner, toaster ->
                    navHostController.popBackStack()
                }
            }
        }
    }
    override val pownav: MutableState<UIScope?> = PowerNavigation()
    override val permitter: Permitter = Permitter()
    override val resultar: Resultar = Resultar()
}