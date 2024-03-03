package com.xbeastop.gitApi.domain.repository

import com.xbeastop.gitApi.domain.models.RepoEntity
import com.xbeastop.gitApi.domain.models.Resource
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun getUserRepos(userName: String): Flow<Resource<List<RepoEntity>>>
    suspend fun getUserNames(userName: String): List<String>
}


