package com.xbeastop.gitApi.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xbeastop.gitApi.domain.models.RepoEntity
import com.xbeastop.gitApi.presentation.ui.theme.GitApiTheme
import java.time.LocalDateTime

@Composable
fun RepoItem(repo: RepoEntity, index: Int) {
    ListItem(headlineContent = { Text(repo.name) }, supportingContent = {
        Text(
            repo.description ?: "No description"
        )
    }, leadingContent = {
        Box(
            Modifier
                .size(35.dp)
                .background(
                    MaterialTheme.colorScheme.primaryContainer, shape = CircleShape
                ), contentAlignment = Alignment.Center
        ) {
            Text(text = (index + 1).toString().padStart(2, '0'))
        }
    })
}


@Preview
@Composable
private fun RepoItemPreview() {
    GitApiTheme {
        Surface {
            RepoItem(
                repo = RepoEntity(
                    userName = "userName",
                    name = "Name",
                    "Description",
                    id = 1,
                    createdAt = LocalDateTime.now().toString()
                ), index = 0
            )
        }
    }
}
