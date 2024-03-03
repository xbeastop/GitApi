package com.xbeastop.gitApi.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class RepoEntity (
    val userName: String,
    val name: String,
    val description: String? = null,
    @PrimaryKey val id: Int,
    val createdAt: String
)


data class RepoDto(
    val name: String,
    val description: String? = null,
    val id: Int,
    @SerializedName("created_at")
    val createdAt: String
) {
    fun toEntity(userName: String) = RepoEntity(
        userName, name, description, id, createdAt
    )
}