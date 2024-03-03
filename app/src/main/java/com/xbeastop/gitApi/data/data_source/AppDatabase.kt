package com.xbeastop.gitApi.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.xbeastop.gitApi.domain.models.RepoEntity

@Database(entities = [RepoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): DataAccessObject
}