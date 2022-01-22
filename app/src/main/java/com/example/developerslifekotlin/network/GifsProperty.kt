package com.example.developerslifekotlin.network

import android.os.Parcelable
import com.example.developerslifekotlin.database.DatabaseGif
import kotlinx.parcelize.Parcelize

@Parcelize
data class GifsProperty(
    val result: List<NetworkGif>,
    val totalCount: Int) : Parcelable

@Parcelize
data class NetworkGif(
    val id: Int,
    val description: String,
    val votes: Int,
    val author: String,
    val date: String,
    val gifURL: String,
    val gifSize: Int,
    val previewURL: String,
    val videoURL: String,
    val videoPath: String,
    val videoSize: Int,
    val type: String,
    val width: String,
    val height: String,
    val commentsCount: Int,
    val fileSize: Int,
    val canVote: Boolean) : Parcelable

fun GifsProperty.asDatabaseModel(): DatabaseGif {
    val gif = result[(0 until PAGE_SIZE).random()]
    return  DatabaseGif(
            url = gif.gifURL,
            description = gif.description)
}

