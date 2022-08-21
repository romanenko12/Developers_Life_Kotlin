package com.example.developerslifekotlin

import android.app.Application
import android.content.Context
import com.example.developerslifekotlin.di.components.AppComponent
import com.example.developerslifekotlin.di.components.DaggerAppComponent
import com.example.developerslifekotlin.di.dependencies.AppDependencies

class DevelopersLifeApplication : Application(), AppDependencies {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appDependencies(this)
            .build()
    }

    override val context: Context = this
}
