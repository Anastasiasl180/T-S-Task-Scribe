package com.aopr.shared_ui.util

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.radusalagean.infobarcompose.InfoBarMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow


abstract class ViewModelKit<E : EventsType, UiE : UiEventsType> : ViewModel() {

    private val _infoBar = mutableStateOf<InfoBarMessage?>(null)
    val infoBar: State<InfoBarMessage?> = _infoBar

    protected val _uiEvent = MutableSharedFlow<UiE>()
    val uiEvent = _uiEvent.asSharedFlow()

    protected val dataViewModelScope = CoroutineScope(Dispatchers.IO)

    protected fun hideInfoBar() {
        _infoBar.value = null
    }

    abstract fun onEvent(event: E)
    override fun onCleared() {
        dataViewModelScope.cancel()
        super.onCleared()
    }

    protected fun showLongInfoBar(textId: Int,time:Int=4) {
        _infoBar.value = InfoBarMessage(
            textStringResId = textId

        )
    }
    protected fun showShortInfoBar(textId: Int,time:Int) {
        _infoBar.value = InfoBarMessage(
            textStringResId = textId, displayTimeSeconds = time
        )
    }
}