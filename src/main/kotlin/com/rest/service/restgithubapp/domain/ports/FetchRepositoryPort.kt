package com.rest.service.restgithubapp.domain.ports

import com.rest.service.restgithubapp.domain.dtos.Repository
import com.rest.service.restgithubapp.domain.usecase.RepositoryCommand

interface FetchRepositoryPort {
    fun fetchRepository(repositoryCommand: RepositoryCommand): Repository
}
