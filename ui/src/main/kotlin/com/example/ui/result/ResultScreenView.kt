package com.example.ui.result

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.model.Book
import com.example.ui.result.state.BookSearchResultState
import com.example.ui.result.viewmodel.ResultViewModel
import com.example.ui.views.BookImageView
import com.example.ui.views.LoadingView
import com.example.ui.views.spaceS

@Composable
fun ResultScreenView(
    viewModel: ResultViewModel,
    goBack: () -> Unit,
) {
    val viewState by viewModel.bookSearchResultState.collectAsStateWithLifecycle()

    when (viewState) {
        is BookSearchResultState.Loading -> LoadingView()
        is BookSearchResultState.Error -> ShowErrorMessage(
            errorMessage = (viewState as BookSearchResultState.Error).errorMessage,
            doAfterErrorIsShown = goBack,
        )

        is BookSearchResultState.EmptyResult -> NoResultView(onButtonClick = goBack)
        is BookSearchResultState.Success -> ResultListView(
            (viewState as BookSearchResultState.Success).books,
            viewModel::getAuthorText,
        )
    }
}

@Composable
private fun ShowErrorMessage(errorMessage: String, doAfterErrorIsShown: () -> Unit) {
    Toast.makeText(
        LocalContext.current,
        errorMessage,
        Toast.LENGTH_LONG,
    ).show()

    doAfterErrorIsShown()
}

@Composable
private fun NoResultView(onButtonClick: () -> Unit) {
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
            onClick = onButtonClick,
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
    books: List<Book>,
    getAuthorText: (List<String>) -> String,
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
                    getAuthorText(book.authors),
                    book.imageUrl,
                )
            }
        }
    }
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
        Row {
            BookImageView(imageUrl)
            Content(title, authorsList)
        }
    }
}

@Composable
private fun Content(
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
    ResultListView(
        listOf(
            Book("Title", listOf("Author"), ""),
        ),
    ) { "Author" }
}

@Preview(showBackground = true)
@Composable
private fun EmptyListPreview() {
    NoResultView {}
}
