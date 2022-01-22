package com.example.developerslifekotlin

import android.app.Application

class DevelopersLifeApplication: Application() {

    val appContainer by lazy { AppContainer(applicationContext) }
}