package com.example.googlebookfinder.ui.main.views

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.googlebookfinder.ui.main.state.BookSearchResultState

@Composable
fun SearchScreenView(
    viewState: BookSearchResultState,
    searchBook: (String) -> Unit,
    navigateToResult: () -> Unit,
) {
    when (viewState) {
        is BookSearchResultState.Success -> {
            navigateToResult()
        }

        is BookSearchResultState.Loading -> LoadingView()
        is BookSearchResultState.Error -> Toast.makeText(
            LocalContext.current,
            "Something went wrong",
            Toast.LENGTH_LONG,
        ).show()

        else -> MainView(searchBook)
    }
}

@Composable
private fun MainView(searchBook: (String) -> Unit) {
    Column(
        Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Header()
        Spacer(modifier = Modifier.height(30.dp))
        SearchInputField(searchBook)
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
private fun SearchInputField(viewModel: (String) -> Unit) {
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
        onClick = { viewModel.invoke(text) },
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = "Search",
            fontSize = 15.sp,
        )
    }
}

@Composable
private fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
    SearchScreenView(BookSearchResultState.Empty, {}) {}
}
