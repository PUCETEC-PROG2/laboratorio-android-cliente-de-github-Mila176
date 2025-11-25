package ec.edu.uisek.githubclient.services

import ec.edu.uisek.githubclient.models.Repo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.DELETE
import retrofit2.http.Path
import retrofit2.http.Body

interface GitHubApiService {

    @GET("/user/repos")
    fun getRepos(): Call<List<Repo>>

    @PATCH("/repos/{owner}/{repo}")
    fun updateRepository(
        @Path("owner") owner: String,
        @Path("repo") repoName: String,
        @Body updateRequest: RepoUpdateRequest
    ): Call<Repo>

    @DELETE("/repos/{owner}/{repo}")
    fun deleteRepository(
        @Path("owner") owner: String,
        @Path("repo") repoName: String
    ): Call<Void>

}

data class RepoUpdateRequest(
    val name: String?,
    val description: String?
)