package com.aopr.shared_ui.util.global_view_model

import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

object GlobalViewModelStoreOwner:ViewModelStoreOwner {
    override val viewModelStore: ViewModelStore= ViewModelStore()
}