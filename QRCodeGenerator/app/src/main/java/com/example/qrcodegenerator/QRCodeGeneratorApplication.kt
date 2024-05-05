package com.example.qrcodegenerator

import android.app.Application
import com.example.qrcodegenerator.data.AppContainer
import com.example.qrcodegenerator.data.DefaultAppContainer

class QRCodeGeneratorApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
