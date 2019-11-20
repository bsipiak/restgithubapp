package com.rest.service.restgithubapp.githubhttp

import org.apache.http.client.HttpClient
import org.apache.http.client.config.RequestConfig
import org.apache.http.conn.HttpClientConnectionManager
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

@Configuration
internal class ClientHttpConfig() {
    companion object {
        const val REQUEST_TIMEOUT = 500
        const val CONNECTION_TIMEOUT = 500
        const val SOCKET_TIMEOUT = 1000
        const val MAX_CONNECTIONS = 50
    }

    @Bean
    fun restTemplate(): RestTemplate {
        val restTemplate = RestTemplate()
        val simpleHttpClient = httpClient(httpPoolingConnectionManager(), requestConfig())
        restTemplate.requestFactory = HttpComponentsClientHttpRequestFactory(simpleHttpClient)

        return restTemplate
    }

    private fun httpPoolingConnectionManager(): HttpClientConnectionManager {
        val manager = PoolingHttpClientConnectionManager()
        manager.maxTotal = MAX_CONNECTIONS

        return manager
    }

    private fun httpClient(connectionManager: HttpClientConnectionManager, requestConfig: RequestConfig): HttpClient {
        val builder = HttpClientBuilder.create()
        builder.setConnectionManager(connectionManager)
        builder.setDefaultRequestConfig(requestConfig)

        return builder.build()
    }

    private fun requestConfig(): RequestConfig {
        return RequestConfig.custom()
            .setConnectionRequestTimeout(REQUEST_TIMEOUT)
            .setConnectTimeout(CONNECTION_TIMEOUT)
            .setSocketTimeout(SOCKET_TIMEOUT)
            .build()
    }
}