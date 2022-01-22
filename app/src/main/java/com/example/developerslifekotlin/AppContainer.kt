package com.example.developerslifekotlin

import android.content.Context
import com.example.developerslifekotlin.database.DatabaseInit
import com.example.developerslifekotlin.network.DevelopersLifeApi
import com.example.developerslifekotlin.repository.GifRepository

class AppContainer(context: Context) {

    private val remoteDataSource = DevelopersLifeApi.retrofitService
    private val localDataSource = DatabaseInit.getDatabase(context).gifsDatabaseDao

    val userRepository = GifRepository(localDataSource, remoteDataSource)
}
