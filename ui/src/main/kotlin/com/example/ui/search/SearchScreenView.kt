package com.example.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchScreenView(
    navigateToResult: (String) -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Header()
        Spacer(modifier = Modifier.height(30.dp))
        SearchInputField(onSearchButtonClick = navigateToResult)
    }
}

@Composable
private fun Header() {
    Text(
        text = "BookGoogle",
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurface,
        fontSize = 45.sp,
    )
}

@Composable
private fun SearchInputField(onSearchButtonClick: (String) -> Unit) {
    val focusManager = LocalFocusManager.current
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Search a book") },
    )

    Spacer(modifier = Modifier.height(16.dp))
    Button(
        modifier = Modifier
            .padding(horizontal = 60.dp)
            .fillMaxWidth(),
        onClick = {
            onSearchButtonClick.invoke(text)
            focusManager.clearFocus()
        },
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = "Search",
            fontSize = 15.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
    SearchScreenView {}
}
