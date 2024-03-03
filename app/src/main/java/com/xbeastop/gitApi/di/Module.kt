package com.xbeastop.gitApi.di

import android.app.Application
import androidx.room.Room
import com.xbeastop.gitApi.data.data_source.AppDatabase
import com.xbeastop.gitApi.data.data_source.DataAccessObject
import com.xbeastop.gitApi.data.data_source.GitHubService
import com.xbeastop.gitApi.data.repository_impl.MainRepositoryImpl
import com.xbeastop.gitApi.domain.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Provides
    @Singleton
    fun provideDao(application: Application): DataAccessObject = Room.databaseBuilder(
        application, AppDatabase::class.java, "app_database"
    ).build().userDao()

    @Provides
    @Singleton
    fun provideApiService(): GitHubService =
        Retrofit.Builder().baseUrl("https:///api.github.com")
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(GitHubService::class.java)

    @Provides
    @Singleton
    fun provideMainRepository(
        dao: DataAccessObject,
        api: GitHubService
    ): MainRepository = MainRepositoryImpl(
        dao, api
    )
}