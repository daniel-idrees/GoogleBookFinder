package com.example.ui.search

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.search.action.SearchAction
import com.example.ui.search.uievent.SearchUiEvent
import com.example.ui.search.viewmodel.SearchViewModel
import com.example.ui.views.spaceL

@Composable
internal fun SearchScreenView(
    viewModel: SearchViewModel,
    navigateToResult: (String) -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(viewModel.events) {
        viewModel.events.collect { event ->
            when (event) {
                is SearchUiEvent.NavigateToResult -> navigateToResult(event.searchQuery)
                SearchUiEvent.EmptyTextError -> showErrorToast(context)
            }
        }
    }

    MainView(viewModel::onAction)
}

@Composable
private fun MainView(onAction: (SearchAction) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = spaceL,
            alignment = Alignment.CenterVertically,
        ),
    ) {
        Header()
        SearchInputField(onSearchButtonClick = onAction)
    }
}

@Composable
private fun Header() {
    Text(
        text = "Find your Book",
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurface,
        fontSize = 45.sp,
    )
}

@Composable
private fun SearchInputField(onSearchButtonClick: (SearchAction) -> Unit) {
    val focusManager = LocalFocusManager.current
    var text by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        label = { Text("Search a book") },
    )

    Button(
        modifier = Modifier
            .padding(horizontal = 60.dp)
            .fillMaxWidth(),
        onClick = {
            onSearchButtonClick.invoke(SearchAction.SearchButtonClicked(text))
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

private fun showErrorToast(context: Context) {
    Toast.makeText(
        context,
        "No text provided",
        Toast.LENGTH_SHORT,
    ).show()
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
    MainView {}
}
