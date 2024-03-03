package com.xbeastop.gitApi.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.xbeastop.gitApi.domain.models.RepoEntity


@Dao
interface DataAccessObject {
    @Insert
    suspend fun insertRepos(repos: List<RepoEntity>)
    @Query("SELECT * FROM RepoEntity WHERE userName =:userName")
    suspend fun getReposOfUser(userName: String): List<RepoEntity>
    @Query("SELECT DISTINCT(userName) FROM RepoEntity WHERE userName LIKE :userName || '%'")
    suspend fun getUserNames(userName: String): List<String>
}