package com.rest.service.restgithubapp.domain.usecase

import com.rest.service.restgithubapp.domain.dtos.Repository

interface GetRepositoryUseCase {
    fun getRepository(repositoryQuery: RepositoryQuery): Repository
}

data class RepositoryQuery(
    val repositoryOwner: String,
    val repositoryName: String
)