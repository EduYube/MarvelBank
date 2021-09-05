package com.eyubero.marvelbank.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect


fun <T> LifecycleOwner.observe(stateFlow: StateFlow<T>, observer: Observer<T>) {
    lifecycleScope.launchWhenResumed {
        stateFlow.collect {
            observer.onChanged(it)
        }
    }
}
