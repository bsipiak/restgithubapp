package com.rest.service.restgithubapp

import com.rest.service.restgithubapp.domain.usecase.RepositoryQuery
import com.rest.service.restgithubapp.githubhttp.FetchRepositoryAdapter
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers
import org.springframework.test.web.client.response.MockRestResponseCreators.withStatus
import org.springframework.web.client.RestTemplate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RunWith(SpringRunner::class)
internal class IntegrationTest {
    companion object {
        const val REPO_OWNER = "bsipiak"
        const val REPO_NAME = "restgithubapp"
        const val GITHUB_PATH = "https://api.github.com/repos/$REPO_OWNER/$REPO_NAME"
    }

    @Autowired
    lateinit var restTemplate: RestTemplate

    @Autowired
    lateinit var uut: FetchRepositoryAdapter

    lateinit var mockServer: MockRestServiceServer

    @Before
    fun setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate)
    }

    @Test
    fun `should return result on request`() {
        // given
        mockServer.expect(MockRestRequestMatchers.requestTo(GITHUB_PATH))
            .andRespond(
                withStatus(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(gitHubResponse())
            )
        val repositoryQuery = RepositoryQuery(REPO_OWNER, REPO_NAME)

        // when
        val result = uut.fetchRepository(repositoryQuery)

        // then
        assertEquals("bsipiak/restgithubapp", result.fullName)
        assertEquals(null, result.description)
        assertEquals("https://github.com/bsipiak/restgithubapp.git", result.cloneUrl)
        assertNotNull(result.stars)
        assertEquals("2019-11-07T21:48:01Z", result.createdAt.toString())
    }

    private fun gitHubResponse() =
        "{\"id\":220335030,\"node_id\":\"MDEwOlJlcG9zaXRvcnkyMjAzMzUwMzA=\",\"name\":\"restgithubapp\",\"full_name\":\"bsipiak/restgithubapp\",\"private\":false,\"owner\":{\"login\":\"bsipiak\",\"id\":39024390,\"node_id\":\"MDQ6VXNlcjM5MDI0Mzkw\",\"avatar_url\":\"https://avatars0.githubusercontent.com/u/39024390?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/bsipiak\",\"html_url\":\"https://github.com/bsipiak\",\"followers_url\":\"https://api.github.com/users/bsipiak/followers\",\"following_url\":\"https://api.github.com/users/bsipiak/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/bsipiak/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/bsipiak/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/bsipiak/subscriptions\",\"organizations_url\":\"https://api.github.com/users/bsipiak/orgs\",\"repos_url\":\"https://api.github.com/users/bsipiak/repos\",\"events_url\":\"https://api.github.com/users/bsipiak/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/bsipiak/received_events\",\"type\":\"User\",\"site_admin\":false},\"html_url\":\"https://github.com/bsipiak/restgithubapp\",\"description\":null,\"fork\":false,\"url\":\"https://api.github.com/repos/bsipiak/restgithubapp\",\"forks_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/forks\",\"keys_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/keys{/key_id}\",\"collaborators_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/collaborators{/collaborator}\",\"teams_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/teams\",\"hooks_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/hooks\",\"issue_events_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/issues/events{/number}\",\"events_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/events\",\"assignees_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/assignees{/user}\",\"branches_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/branches{/branch}\",\"tags_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/tags\",\"blobs_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/git/blobs{/sha}\",\"git_tags_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/git/tags{/sha}\",\"git_refs_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/git/refs{/sha}\",\"trees_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/git/trees{/sha}\",\"statuses_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/statuses/{sha}\",\"languages_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/languages\",\"stargazers_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/stargazers\",\"contributors_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/contributors\",\"subscribers_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/subscribers\",\"subscription_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/subscription\",\"commits_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/commits{/sha}\",\"git_commits_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/git/commits{/sha}\",\"comments_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/comments{/number}\",\"issue_comment_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/issues/comments{/number}\",\"contents_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/contents/{+path}\",\"compare_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/compare/{base}...{head}\",\"merges_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/merges\",\"archive_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/{archive_format}{/ref}\",\"downloads_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/downloads\",\"issues_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/issues{/number}\",\"pulls_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/pulls{/number}\",\"milestones_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/milestones{/number}\",\"notifications_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/notifications{?since,all,participating}\",\"labels_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/labels{/name}\",\"releases_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/releases{/id}\",\"deployments_url\":\"https://api.github.com/repos/bsipiak/restgithubapp/deployments\",\"created_at\":\"2019-11-07T21:48:01Z\",\"updated_at\":\"2019-11-12T20:34:36Z\",\"pushed_at\":\"2019-11-12T20:34:34Z\",\"git_url\":\"git://github.com/bsipiak/restgithubapp.git\",\"ssh_url\":\"git@github.com:bsipiak/restgithubapp.git\",\"clone_url\":\"https://github.com/bsipiak/restgithubapp.git\",\"svn_url\":\"https://github.com/bsipiak/restgithubapp\",\"homepage\":null,\"size\":64,\"stargazers_count\":0,\"watchers_count\":0,\"language\":\"Kotlin\",\"has_issues\":true,\"has_projects\":true,\"has_downloads\":true,\"has_wiki\":true,\"has_pages\":false,\"forks_count\":0,\"mirror_url\":null,\"archived\":false,\"disabled\":false,\"open_issues_count\":0,\"license\":null,\"forks\":0,\"open_issues\":0,\"watchers\":0,\"default_branch\":\"master\",\"network_count\":0,\"subscribers_count\":1}"
}