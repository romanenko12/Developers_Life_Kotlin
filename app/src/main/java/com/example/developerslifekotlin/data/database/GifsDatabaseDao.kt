package com.example.developerslifekotlin.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

@Dao
interface GifsDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(gif: DatabaseGif): Completable

    @Query("SELECT * FROM gifs_table WHERE id = :key")
    fun get(key: Int): Single<DatabaseGif>

    @Query("SELECT COUNT(1) FROM gifs_table")
    fun size(): Maybe<Int>

    @Query("DELETE FROM gifs_table")
    fun clear()

    @Query("DELETE FROM sqlite_sequence")
    fun resetTable()
}
