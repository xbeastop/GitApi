package com.xbeastop.gitApi.presentation

sealed interface MainUiEvents {
    data class OnSearchbarActiveChange(val active: Boolean) : MainUiEvents
    data class OnSuggestionClicked(val suggestionItem: String) : MainUiEvents
    data object OnSearchClicked : MainUiEvents
    data class OnSearchQueryChanged(val query: String) : MainUiEvents
    data object OnClearSearchQueryClicked: MainUiEvents
}