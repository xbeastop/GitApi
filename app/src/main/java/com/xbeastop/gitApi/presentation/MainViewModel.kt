package com.xbeastop.gitApi.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xbeastop.gitApi.domain.models.Resource
import com.xbeastop.gitApi.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    private var searchSuggestionsJob: Job? = null
    private var searchJob: Job? = null
    private val _mainUiStateFlow = MutableStateFlow(MainUiState())
    val mainUiState get() = _mainUiStateFlow.asStateFlow()
    var searchQuery by mutableStateOf("")
        private set

    fun onEvent(event: MainUiEvents) {
        when (event) {
            is MainUiEvents.OnSearchQueryChanged -> onSearchQueryChanged(event.query)
            is MainUiEvents.OnSearchbarActiveChange -> onActiveChange(event.active)
            is MainUiEvents.OnSuggestionClicked -> onSuggestionClicked(event.suggestionItem)
            MainUiEvents.OnSearchClicked -> onSearchClicked()
            MainUiEvents.OnClearSearchQueryClicked -> onClearSearchQueryClicked()

        }
    }

    private fun onClearSearchQueryClicked() {
        onSearchQueryChanged("")
        _mainUiStateFlow.update { it.copy(isActive = true) }
    }

    private fun onSearchQueryChanged(value: String) {
        searchQuery = value
        searchSuggestionsJob = viewModelScope.launch {
            searchSuggestionsJob?.cancel()
            delay(300) // skipping fast key strokes from user
            _mainUiStateFlow.update {
                it.copy(searchSuggestions = repository.getUserNames(searchQuery))
            }
        }
    }

    private fun onSearchClicked() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            repository.getUserRepos(searchQuery).collectLatest {
                when (it) {
                    is Resource.Error -> _mainUiStateFlow.update { _ -> MainUiState(error = it.message) }
                    is Resource.Loading -> _mainUiStateFlow.update { _ -> MainUiState(isLoading = true) }
                    is Resource.Success -> _mainUiStateFlow.update { _ ->
                        MainUiState(repos = it.data ?: emptyList())
                    }
                }
            }
        }
    }

    private fun onActiveChange(active: Boolean) {
        _mainUiStateFlow.update { it.copy(isActive = active) }
    }

    private fun onSuggestionClicked(suggestionItem: String) {
        onSearchQueryChanged(suggestionItem)
        onSearchClicked()
    }
}

