package com.xbeastop.gitApi.presentation

import com.xbeastop.gitApi.domain.models.RepoEntity

data class MainUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isActive: Boolean = false,
    val searchSuggestions: List<String> = emptyList(),
    val repos: List<RepoEntity> = emptyList()
)