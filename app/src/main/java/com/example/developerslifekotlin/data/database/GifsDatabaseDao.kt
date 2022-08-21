package com.example.developerslifekotlin.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GifsDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(gif: DatabaseGif)

    @Query("SELECT * FROM gifs_table WHERE id = :key")
    suspend fun get(key: Long): DatabaseGif

    @Query("SELECT COUNT(1) FROM gifs_table")
    suspend fun size(): Long

    @Query("DELETE FROM gifs_table")
    suspend fun clear()

    @Query("DELETE FROM sqlite_sequence")
    suspend fun resetTable()
}
