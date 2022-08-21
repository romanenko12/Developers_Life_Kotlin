package com.example.developerslifekotlin.utils

import android.content.Context
import com.example.developerslifekotlin.DevelopersLifeApplication
import com.example.developerslifekotlin.di.components.AppComponent

val Context.appComponent: AppComponent
    get() = when(this) {
        is DevelopersLifeApplication -> appComponent
        else -> this.applicationContext.appComponent
    }
