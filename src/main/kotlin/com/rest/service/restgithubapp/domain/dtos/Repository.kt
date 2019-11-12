package com.rest.service.restgithubapp.domain.dtos

import com.rest.service.restgithubapp.web.dtos.RepositoryResponseBody
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

data class Repository (
    val fullName: String?,
    val description: String?,
    val cloneUrl: String?,
    val stars: Int?,
    val createdAt: OffsetDateTime?
) {
    fun toResponseBody(): RepositoryResponseBody {
        return RepositoryResponseBody (
            fullName,
            description,
            cloneUrl,
            stars,
            createdAt
        )
    }
}