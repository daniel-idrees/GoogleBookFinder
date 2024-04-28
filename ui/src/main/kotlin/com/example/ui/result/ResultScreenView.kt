package com.example.ui.result

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.Book
import com.example.ui.result.action.ResultAction
import com.example.ui.result.state.ResultViewState
import com.example.ui.result.uievent.ResultUiEvent
import com.example.ui.result.viewmodel.ResultViewModel
import com.example.ui.views.BookImageView
import com.example.ui.views.LoadingView
import com.example.ui.views.spaceS

@Composable
internal fun ResultScreenView(
    viewModel: ResultViewModel,
    goBack: () -> Unit,
    navigateToDetails: () -> Unit
) {
    val context = LocalContext.current
    val viewState by viewModel.resultViewState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.events) {
        viewModel.events.collect { event ->
            when (event) {
                ResultUiEvent.NavigateToDetails -> navigateToDetails()
                ResultUiEvent.ShowError -> showErrorMessage(
                    context = context,
                    errorMessage = "Something went wrong ..."
                ) { viewModel.onAction(ResultAction.AfterErrorShownAction) }

                ResultUiEvent.ShowNoInternetConnectionError -> showErrorMessage(
                    context = context,
                    errorMessage = "No Internet Connection ..."
                ) { viewModel.onAction(ResultAction.AfterErrorShownAction) }

                ResultUiEvent.NavigateBack -> goBack()
            }
        }
    }

    MainView(viewState, viewModel::onAction)
}

@Composable
private fun MainView(viewState: ResultViewState, onAction: (ResultAction) -> Unit) {
    when (viewState) {
        ResultViewState.Loading -> LoadingView()
        ResultViewState.Error -> ErrorView(onAction = onAction)
        ResultViewState.NoInternetConnection -> NoInternetView(onAction = onAction)
        ResultViewState.Empty -> NoResultView(onAction = onAction)
        is ResultViewState.Success -> ResultListView(
            viewState.books,
        )
    }
}

@Composable
private fun ErrorView(onAction: (ResultAction) -> Unit) {
    onAction.invoke(ResultAction.ErrorAction)
    /**
     * No view for now
     */
}

@Composable
private fun NoInternetView(onAction: (ResultAction) -> Unit) {
    onAction.invoke(ResultAction.NoInternetConnectionAction)
    /**
     * No view for now
     */
}

private fun showErrorMessage(
    context: Context,
    errorMessage: String,
    doAfterErrorIsShown: () -> Unit
) {
    Toast.makeText(
        context,
        errorMessage,
        Toast.LENGTH_LONG,
    ).show()

    doAfterErrorIsShown()
}

@Composable
private fun NoResultView(onAction: (ResultAction) -> Unit) {
    Column(
        modifier = Modifier.padding(spaceS),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = spaceS,
            alignment = Alignment.CenterVertically,
        ),
    ) {
        Text(text = "No results found")
        Button(
            modifier = Modifier
                .padding(horizontal = 60.dp)
                .fillMaxWidth(),
            onClick = {
                onAction(ResultAction.GoBackButtonClicked)
            },
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = "Go Back",
                fontSize = 15.sp,
            )
        }
    }
}

@Composable
private fun ResultListView(
    books: List<Book>
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = spaceS)
            .fillMaxWidth(),
    ) {
        items(books) { book ->
            with(book) {
                ResultListItem(
                    title,
                    prepareAuthorText(book.authors),
                    book.imageUrl,
                )
            }
        }
    }
}

private fun prepareAuthorText(authorsList: List<String>): String {
    if (authorsList.isEmpty()) {
        return "Unknown"
    }

    var authors = ""
    authorsList.forEach { a ->
        authors += if (authors.isNotEmpty()) ", $a" else a
    }

    return authors.ifEmpty { "Unknown" }
}

@Composable
private fun ResultListItem(
    title: String,
    authorsList: String,
    imageUrl: String?,
) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(spaceS),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            BookImageView(imageUrl)
            BookDetail(title, authorsList)
        }
    }
}

@Composable
private fun BookDetail(
    title: String,
    authorText: String,
) {
    Column(
        modifier = Modifier.padding(spaceS),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = title, textAlign = TextAlign.Start)
        Text(
            text = authorText,
            textAlign = TextAlign.Start,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ResultListPreview() {
    MainView(
        viewState = ResultViewState.Success(
            listOf(
                com.example.data.model.Book("Title", listOf("Author"), ""),
            )
        )
    ) {}
}

@Preview(showBackground = true)
@Composable
private fun EmptyListPreview() {
    MainView(
        viewState = ResultViewState.Empty
    ) {}
}
