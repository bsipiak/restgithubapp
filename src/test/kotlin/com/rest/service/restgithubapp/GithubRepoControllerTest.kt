package com.rest.service.restgithubapp

import com.rest.service.restgithubapp.domain.dtos.Repository
import com.rest.service.restgithubapp.domain.usecase.RepositoryCommand
import com.rest.service.restgithubapp.githubhttp.FetchRepositoryAdapter
import com.rest.service.restgithubapp.web.dtos.RepositoryResponseBody
import io.mockk.clearMocks
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner
import java.time.OffsetDateTime

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class GithubRepoControllerTest {
    companion object {
        const val REPO_OWNER = "bsipiak"
        const val REPO_NAME = "restgithubapp"
        const val API_PATH = "/repositories/$REPO_OWNER/$REPO_NAME"
        const val VALID_CONTENT_TYPE = "application/vnd.rest.github.app.public.v1+json"
    }

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @MockBean
    lateinit var fetchRepositoryAdapter: FetchRepositoryAdapter

    @BeforeEach
    fun init() {
        clearMocks(fetchRepositoryAdapter)
    }

    @Test
    fun `should return result on correct request`() {
        // given
        val headers = HttpHeaders()
        headers.set("Accept", VALID_CONTENT_TYPE)
        val entity = HttpEntity<String>(headers)
        val repositoryCommand = RepositoryCommand(REPO_OWNER, REPO_NAME)
        given(fetchRepositoryAdapter.fetchRepository(repositoryCommand)).willReturn(githubData());

        // when
        val result = testRestTemplate.exchange(API_PATH, HttpMethod.GET, entity, RepositoryResponseBody::class.java)

        // then
        assertEquals("Name", result.body?.fullName)
        assertEquals("Description", result.body?.description)
        assertEquals("URL", result.body?.cloneUrl)
        assertEquals(1, result.body?.stars)
        assertEquals("2019-10-15T09:12Z", result.body?.createdAt.toString())
        assertEquals(VALID_CONTENT_TYPE, result.headers.contentType.toString())
        assertEquals(HttpStatus.OK, result.statusCode)
    }

    @Test
    fun `should fail on wrong Accept header`() {
        // given
        val headers = HttpHeaders()
        headers.set("Accept", "application/json")
        val entity = HttpEntity<String>(headers)

        // when
        val result = testRestTemplate.exchange(API_PATH, HttpMethod.GET, entity, RepositoryResponseBody::class.java)

        // then
        assertEquals(HttpStatus.NOT_ACCEPTABLE, result.statusCode)
    }

    @Test
    fun `should fail with empty query string parameters given`() {
        // given
        val headers = HttpHeaders()
        headers.set("Accept", VALID_CONTENT_TYPE)
        val entity = HttpEntity<String>(headers)
        val repositoryOwner = ""
        val repositoryName = ""
        val repositoryCommand = RepositoryCommand(repositoryOwner, repositoryName)
        given(fetchRepositoryAdapter.fetchRepository(repositoryCommand)).willReturn(githubData());

        // when
        val result = testRestTemplate.exchange(
            "/repositories/$repositoryOwner/$repositoryName/",
            HttpMethod.GET,
            entity,
            RepositoryResponseBody::class.java
        )

        // then
        assertEquals(HttpStatus.NOT_FOUND, result.statusCode)
    }

    private fun githubData(): Repository {
        return Repository(
            "Name",
            "Description",
            "URL",
            1,
            OffsetDateTime.parse("2019-10-15T10:12:00.00000+01:00")
        )
    }
}