package com.rest.service.restgithubapp.web.dtos

import java.time.OffsetDateTime

data class RepositoryResponseBody(
    val fullName: String,
    val description: String?,
    val cloneUrl: String,
    val stars: Int,
    val createdAt: OffsetDateTime
)