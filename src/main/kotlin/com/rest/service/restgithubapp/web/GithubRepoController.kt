package com.rest.service.restgithubapp.web

import com.rest.service.restgithubapp.domain.usecase.GetRepositoryUseCase
import com.rest.service.restgithubapp.domain.usecase.RepositoryQuery
import com.rest.service.restgithubapp.web.dtos.RepositoryResponseBody
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
internal class GithubRepoController(
    private val getRepositoryUseCase: GetRepositoryUseCase
) {
    companion object {
        const val REPO_API_PATH = "/repositories/{owner}/{repository-name}"
        const val API_VERSION_1 = "application/vnd.rest.github.app.public.v1+json"
    }

    @GetMapping(REPO_API_PATH, produces = [API_VERSION_1])
    fun findRepository(
        @PathVariable("owner", required = true) owner: String,
        @PathVariable("repository-name", required = true) repositoryName: String
    ): RepositoryResponseBody {
        val repositoryInfo = RepositoryQuery(owner, repositoryName)
        val repository = getRepositoryUseCase.getRepository(repositoryInfo)

        return repository.toResponseBody()
    }
}