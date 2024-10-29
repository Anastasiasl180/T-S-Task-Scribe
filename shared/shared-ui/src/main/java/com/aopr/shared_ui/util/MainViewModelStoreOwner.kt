package com.aopr.shared_ui.util

import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

object MainViewModelStoreOwner:ViewModelStoreOwner {
    override val viewModelStore: ViewModelStore= ViewModelStore()
}