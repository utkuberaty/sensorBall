package com.utku.sensorball.app

import android.app.Application
import com.utku.sensorball.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class SensorBallApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SensorBallApp)
            androidLogger(Level.ERROR)
            modules(allModules)
        }
    }
}