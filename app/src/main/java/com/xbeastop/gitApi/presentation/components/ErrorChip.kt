package com.xbeastop.gitApi.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xbeastop.gitApi.presentation.ui.theme.GitApiTheme

@Composable
fun ErrorChip(errorMessage: String, modifier: Modifier = Modifier) {
    SuggestionChip(onClick = { /*TODO*/ }, label = {
        Text(text = errorMessage, modifier = Modifier.padding(vertical = 6.dp))
    }, modifier = modifier, icon = {
        Icon(imageVector = Icons.Default.Warning, contentDescription = errorMessage)
    })
}


@Preview()
@Composable
private fun ErrorChipPreview() {
    GitApiTheme {
        Surface {
            Box(Modifier.fillMaxWidth()) {
                ErrorChip(
                    errorMessage = "Error preview",
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }
}