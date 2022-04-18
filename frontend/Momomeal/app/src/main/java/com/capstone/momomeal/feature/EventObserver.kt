package com.capstone.momomeal.feature

import androidx.lifecycle.Observer

class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>>
{
    override fun onChanged(event: Event<T>?) {
        //이벤트가 처리 안되었을경우에만 처리할수있음 (처리안되면 null이라 .let으로 뒤에코드가 실행이안됨)
        event?.getContentIfNotHandled()?.let { value -> onEventUnhandledContent(value) }
    }
}