package com.rest.service.restgithubapp.githubhttp

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.rest.service.restgithubapp.domain.dtos.Repository
import com.rest.service.restgithubapp.domain.ports.FetchRepositoryPort
import com.rest.service.restgithubapp.domain.usecase.RepositoryCommand
import com.rest.service.restgithubapp.githubhttp.dtos.FetchRepositoryResponseBody
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.time.OffsetDateTime

@Component
internal class FetchRepositoryAdapter() : FetchRepositoryPort {
    companion object {
        const val FETCH_REPOSITORY_INFO_URL = "https://api.github.com/repos/"
    }

    override fun fetchRepository(repositoryCommand: RepositoryCommand): Repository {
        val url = buildUrl(repositoryCommand)
        val result = RestTemplate().getForObject(url, FetchRepositoryResponseBody::class.java)

        return Repository(
            result?.fullName,
            result?.description,
            result?.cloneUrl,
            result?.stars?.toInt(),
            OffsetDateTime.parse(result?.createdAt)
        )
    }

    private fun buildUrl(repositoryCommand: RepositoryCommand): String {
        return "$FETCH_REPOSITORY_INFO_URL${repositoryCommand.repositoryOwner}/${repositoryCommand.repositoryName}"
    }
}