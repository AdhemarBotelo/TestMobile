package com.example.testmobile.features

import android.app.Application
import com.example.testmobile.features.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(listOf(mLocalModules, mRepositoryModules, mUseCaseModules, mViewModelsModules))
        }
    }
}