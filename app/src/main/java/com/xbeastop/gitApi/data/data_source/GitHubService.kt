package com.xbeastop.gitApi.data.data_source

import com.xbeastop.gitApi.domain.models.RepoDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {
    @GET("users/{user}/repos")
    fun fetchUserRepos(@Path("user") userName: String): Call<List<RepoDto>?>
}