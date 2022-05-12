package com.marina_romanovna.apartmentcatalog.utils

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun <T> singleEvent() = MutableSharedFlow<T>(replay = 1)
fun <T> event(data: T) = MutableStateFlow(data)
fun <T> eventWithNull(data: T?) = MutableStateFlow(data)

fun ViewModel.emitSingleEvent(action: suspend () -> Unit) {
    viewModelScope.launch {
        action.invoke()
    }
}

fun <T> Flow<T>.observe(scope: LifecycleCoroutineScope, action: (T) -> Unit) {
    scope.launchWhenCreated {
        collect { action.invoke(it) }
    }
}