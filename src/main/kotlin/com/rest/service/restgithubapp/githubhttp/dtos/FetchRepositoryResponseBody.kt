package com.rest.service.restgithubapp.githubhttp.dtos

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class FetchRepositoryResponseBody (
    @get:JsonProperty("full_name") val fullName: String?,
    @get:JsonProperty("description") val description: String?,
    @get:JsonProperty("clone_url") val cloneUrl: String?,
    @get:JsonProperty("stargazers_count") val stars: String?,
    @get:JsonProperty("created_at") val createdAt: String?
)