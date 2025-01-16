package com.aopr.shared_domain.resource_manager

import com.aopr.shared_domain.R

sealed class SharedStringResourceManager(val messageId:Int) {
    data object DefaultMessage : SharedStringResourceManager(R.string.DefaultErrorMessage)
    data object EmptyTittleMessage : SharedStringResourceManager(R.string.EmptyTittle)
    data object EmptyDescriptionMessage : SharedStringResourceManager(R.string.EmptyDescription)
}