package com.rest.service.restgithubapp.githubhttp

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
internal class ClientHttpConfig () {
    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}