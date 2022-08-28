package com.example.developerslifekotlin.data.network

import android.os.Parcelable
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
