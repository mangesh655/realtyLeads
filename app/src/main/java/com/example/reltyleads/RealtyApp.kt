package com.example.reltyleads

import android.app.Application
import com.example.reltyleads.repository.InventorySourceHelper.InventorySourceHelper
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
internal class RealtyApp : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @Inject
    lateinit var inventorySourceHelper: InventorySourceHelper

    override fun onCreate() {
        super.onCreate()
        applicationScope.launch {
            inventorySourceHelper.createAndInsertDummyProjectsData()
        }
    }
}