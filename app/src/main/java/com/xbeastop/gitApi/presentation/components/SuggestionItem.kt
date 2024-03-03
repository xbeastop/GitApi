package com.xbeastop.gitApi.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xbeastop.gitApi.presentation.ui.theme.GitApiTheme

@Composable
fun SuggestionItem(item: String, onClick: (String) -> Unit) {
    val updatedOnClick by rememberUpdatedState(newValue = onClick)
    Surface(onClick = { updatedOnClick(item) }, shape = RoundedCornerShape(8.dp)) {
        Row(modifier = Modifier.padding(12.dp)) {
            Icon(
                imageVector = Icons.Default.Search, contentDescription = null
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = item)
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null
            )

        }
    }
}

@Preview
@Composable
private fun SuggestionItemPreview() {
    GitApiTheme {
        Surface {
            SuggestionItem(item = "Suggestion item", onClick = {})
        }
    }
}

