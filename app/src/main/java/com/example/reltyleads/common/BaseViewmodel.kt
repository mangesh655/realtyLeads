package com.example.reltyleads.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlin.coroutines.CoroutineContext

open class BaseViewModel: ViewModel(), CoroutineScope {

    private val scopeJob: Job = SupervisorJob()

    override val coroutineContext: CoroutineContext = scopeJob + Dispatchers.Main

    override fun onCleared() {
        coroutineContext.cancelChildren()
        super.onCleared()
    }
}