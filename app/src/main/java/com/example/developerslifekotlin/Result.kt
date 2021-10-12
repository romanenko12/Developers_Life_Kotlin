package com.example.developerslifekotlin

class Result {
    private val id = 0
    val description: String? = null
    private val votes = 0
    private val author: String? = null
    private val date: String? = null
    private val gifURL: String? = null
    private val gifSize = 0
    private val previewURL: String? = null
    private val videoURL: String? = null
    private val videoPath: String? = null
    private val videoSize = 0
    private val type: String? = null
    private val width: String? = null
    private val height: String? = null
    private val commentsCount = 0
    private val fileSize = 0
    private val canVote = false

    fun getGifURL(): String {
        return gifURL!!.replace("http:", "https:")
    }

    fun getPreviewURL(): String {
        return previewURL!!.replace("http:", "https:")
    }
}