package com.example.developerslifekotlin.data.mappers

import com.example.developerslifekotlin.data.database.DatabaseGif
import com.example.developerslifekotlin.domain.entity.DomainGif

fun DomainGif.asDatabaseModel(): DatabaseGif {
    return DatabaseGif(
        url = this.url,
        description = this.description
    )
}
