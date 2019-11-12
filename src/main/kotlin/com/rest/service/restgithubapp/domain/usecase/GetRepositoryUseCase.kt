package com.rest.service.restgithubapp.domain.usecase

import com.rest.service.restgithubapp.domain.dtos.Repository

interface GetRepositoryUseCase {
    fun getRepository(repositoryCommand: RepositoryCommand): Repository
}

data class RepositoryCommand(
    val repositoryOwner: String,
    val repositoryName: String
)