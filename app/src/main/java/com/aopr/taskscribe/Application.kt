package com.aopr.taskscribe

import android.app.Application
import com.aopr.shared_data.di.SharedDataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.ksp.generated.module

class Application: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@Application)
            modules(SharedDataModule().module)
        }
    }
}