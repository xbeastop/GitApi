package com.xbeastop.gitApi.data.repository_impl

import com.xbeastop.gitApi.data.data_source.DataAccessObject
import com.xbeastop.gitApi.data.data_source.GitHubService
import com.xbeastop.gitApi.domain.models.RepoEntity
import com.xbeastop.gitApi.domain.models.Resource
import com.xbeastop.gitApi.domain.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val dao: DataAccessObject, private val api: GitHubService
) : MainRepository {
    override suspend fun getUserRepos(userName: String): Flow<Resource<List<RepoEntity>>> {

        //Todo: This could be a separate useCase
        return flow<Resource<List<RepoEntity>>> {
            emit(Resource.Loading())
            val cachedRepos = dao.getReposOfUser(userName)
            if (cachedRepos.isNotEmpty()) {
                emit(Resource.Success(cachedRepos))
                return@flow
            }
            runCatching {
                api.fetchUserRepos(userName).execute().body() ?: listOf()
            }.onFailure {
                emit(Resource.Error("Something went wrong ${it.message}"))
            }.onSuccess { repoList ->
                if (repoList.isNotEmpty()) {
                    val repoEntities = repoList.map { it.toEntity(userName) }
                    dao.insertRepos(repoEntities)
                    emit(Resource.Success(repoEntities))
                } else emit(Resource.Error("No repos found"))
            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getUserNames(userName: String): List<String> {
        return dao.getUserNames(userName)
    }
}