package com.xbeastop.gitApi.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xbeastop.gitApi.presentation.components.ErrorChip
import com.xbeastop.gitApi.presentation.components.RepoItem
import com.xbeastop.gitApi.presentation.components.SuggestionItem
import com.xbeastop.gitApi.presentation.ui.theme.GitApiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GitApiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        val state by viewModel.mainUiState.collectAsState()
                        AnimatedVisibility(visible = state.isLoading) {
                            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                        }
                        SearchBar(
                            query = viewModel.searchQuery,
                            onQueryChange = { viewModel.onEvent(MainUiEvents.OnSearchQueryChanged(it)) },
                            onSearch = { viewModel.onEvent(MainUiEvents.OnSearchClicked) },
                            placeholder = { Text("Search") },
                            leadingIcon = { Icon(Icons.Default.Search, null) },
                            trailingIcon = {
                                if (viewModel.searchQuery.isNotBlank()) IconButton(onClick = {
                                    viewModel.onEvent(MainUiEvents.OnClearSearchQueryClicked)
                                }) {
                                    Icon(Icons.Default.Close, null)
                                }
                            },
                            active = state.isActive,
                            onActiveChange = {
                                viewModel.onEvent(
                                    MainUiEvents.OnSearchbarActiveChange(
                                        it
                                    )
                                )
                            }
                        ) {
                            Button(
                                onClick = { viewModel.onEvent(MainUiEvents.OnSearchClicked) },
                                modifier = Modifier
                                    .padding(6.dp)
                                    .align(Alignment.End)
                            ) {
                                Text("Search")
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            LazyColumn {
                                items(state.searchSuggestions) {
                                    SuggestionItem(
                                        item = it,
                                        onClick = { suggestion ->
                                            viewModel.onEvent(
                                                MainUiEvents.OnSuggestionClicked(
                                                    suggestion
                                                )
                                            )
                                        }
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))

                        state.error?.let {
                            ErrorChip(
                                errorMessage = it, modifier = Modifier.align(
                                    Alignment.CenterHorizontally
                                )
                            )
                        }

                        LazyColumn {
                            itemsIndexed(state.repos) { index, repo ->
                                RepoItem(repo = repo, index = index)
                            }
                        }
                    }
                }
            }
        }
    }


}

@Preview
@Composable
fun SearchPreview() {
    GitApiTheme {
        SuggestionItem(item = "Amal") {}
    }
}