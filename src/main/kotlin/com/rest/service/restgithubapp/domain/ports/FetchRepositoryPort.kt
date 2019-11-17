package com.rest.service.restgithubapp.domain.ports

import com.rest.service.restgithubapp.domain.dtos.Repository
import com.rest.service.restgithubapp.domain.usecase.RepositoryQuery

interface FetchRepositoryPort {
    fun fetchRepository(repositoryQuery: RepositoryQuery): Repository
}
