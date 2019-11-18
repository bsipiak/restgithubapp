package com.rest.service.restgithubapp.githubhttp.dtos

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class FetchRepositoryResponseBody(
    val fullName: String,
    val description: String?,
    val cloneUrl: String,
    @JsonProperty("stargazers_count") val stars: String,
    val createdAt: String
)