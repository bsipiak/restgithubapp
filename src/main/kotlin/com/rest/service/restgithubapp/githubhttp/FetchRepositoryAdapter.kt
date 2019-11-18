package com.rest.service.restgithubapp.githubhttp

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.rest.service.restgithubapp.domain.dtos.Repository
import com.rest.service.restgithubapp.domain.ports.FetchRepositoryPort
import com.rest.service.restgithubapp.domain.usecase.RepositoryQuery
import com.rest.service.restgithubapp.githubhttp.dtos.FetchRepositoryResponseBody
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.time.OffsetDateTime

@Component
internal class FetchRepositoryAdapter(
    private val objectMapper: ObjectMapper,
    private val restTemplate: RestTemplate
): FetchRepositoryPort {
    companion object {
        const val FETCH_REPOSITORY_INFO_URL = "https://api.github.com/repos/"
    }

    override fun fetchRepository(repositoryQuery: RepositoryQuery): Repository {
        val url = buildUrl(repositoryQuery)
        val responseString = restTemplate.getForObject(url, String::class.java)

        val result = objectMapper.readValue(responseString, FetchRepositoryResponseBody::class.java)

        return Repository(
            result.fullName,
            result?.description,
            result.cloneUrl,
            result.stars.toInt(),
            OffsetDateTime.parse(result.createdAt)
        )
    }

    private fun buildUrl(repositoryQuery: RepositoryQuery): String {
        return "$FETCH_REPOSITORY_INFO_URL${repositoryQuery.repositoryOwner}/${repositoryQuery.repositoryName}"
    }
}