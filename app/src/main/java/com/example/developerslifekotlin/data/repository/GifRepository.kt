package com.example.developerslifekotlin.data.repository

import com.example.developerslifekotlin.data.database.DatabaseGif
import com.example.developerslifekotlin.data.database.GifsDatabaseDao
import com.example.developerslifekotlin.data.network.DevelopersLifeApiFilter
import com.example.developerslifekotlin.data.network.DevelopersLifeApiService
import com.example.developerslifekotlin.data.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GifRepository(private val database: GifsDatabaseDao, private val server: DevelopersLifeApiService) {

    suspend fun downloadGif(category: DevelopersLifeApiFilter, number: Int): DatabaseGif {
        return withContext(Dispatchers.IO) {
            val download = server.getProperties(category.value, number).asDatabaseModel()
            database.insert(download)
            return@withContext download
        }
    }

    suspend fun getGif(id: Long): DatabaseGif {
        return withContext(Dispatchers.IO) {
            return@withContext database.get(id)
        }
    }

    suspend fun getCount(): Long {
        return withContext(Dispatchers.IO) {
            return@withContext database.size()
        }
    }

    suspend fun clearDatabase() {
        database.clear()
        database.resetTable()
    }

}
