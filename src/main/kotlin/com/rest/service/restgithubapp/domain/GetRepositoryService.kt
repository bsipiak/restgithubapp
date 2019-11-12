package com.rest.service.restgithubapp.domain

import com.rest.service.restgithubapp.domain.dtos.Repository
import com.rest.service.restgithubapp.domain.ports.FetchRepositoryPort
import com.rest.service.restgithubapp.domain.usecase.GetRepositoryUseCase
import com.rest.service.restgithubapp.domain.usecase.RepositoryCommand
import org.springframework.stereotype.Component

@Component
internal class GetRepositoryService(private val fetchRepositoryPort: FetchRepositoryPort) : GetRepositoryUseCase {
    override fun getRepository(repositoryCommand: RepositoryCommand): Repository {
        return fetchRepositoryPort.fetchRepository(repositoryCommand)
    }
}