package ec.edu.uisek.githubclient.services

import retrofit2.http.GET
import retrofit2.Call
import ec.edu.uisek.githubclient.models.Repo

interface GitHubApiService {
    @GET(value="/user/repos")
    fun getRepos(): Call<List<Repo>>
}