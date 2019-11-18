package com.rest.service.restgithubapp

import com.fasterxml.jackson.databind.ObjectMapper
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class IntegrationTest {
    companion object {
        const val REPO_OWNER = "bsipiak"
        const val REPO_NAME = "restgithubapp"
        const val API_PATH = "/repositories/$REPO_OWNER/$REPO_NAME"
        const val VALID_CONTENT_TYPE = "application/vnd.rest.github.app.public.v1+json"
    }

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun `should return result on request`() {
        // when
        val resultString = testRestTemplate.getForEntity(API_PATH, String::class.java)

        // then
        val result = objectMapper.readValue(resultString.body, Map::class.java)

        assertEquals(HttpStatus.OK, resultString.statusCode)
        assertEquals(VALID_CONTENT_TYPE, resultString.headers.contentType.toString())
        assertEquals("bsipiak/restgithubapp", result["fullName"])
        assertEquals(null, result["description"])
        assertEquals("https://github.com/bsipiak/restgithubapp.git", result["cloneUrl"])
        assertNotNull(result["stars"])
        assertEquals("2019-11-07T21:48:01Z", result["createdAt"].toString())
    }
}