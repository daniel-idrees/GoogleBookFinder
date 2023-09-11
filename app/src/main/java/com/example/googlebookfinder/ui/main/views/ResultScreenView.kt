package com.example.googlebookfinder.ui.main.views

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.googlebookfinder.R
import com.example.googlebookfinder.domain.model.Book
import com.example.googlebookfinder.ui.main.state.BookSearchResultState

@Composable
fun ResultScreenView(
    viewState: BookSearchResultState,
    resetState: () -> Unit,
    goBack: () -> Unit,
) {
    BackHandler(onBack = resetState)
    when (viewState) {
        is BookSearchResultState.Success -> {
            if (viewState.books.isEmpty()) {
                NoResultView(goBack)
            } else {
                ResultListView(viewState.books)
            }
        }

        else -> goBack()
    }
}

@Composable
private fun NoResultView(goBack: () -> Unit) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = "No results found")
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .padding(horizontal = 60.dp)
                .fillMaxWidth(),
            onClick = goBack,
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
private fun ResultListView(books: List<Book>) {
    Spacer(modifier = Modifier.height(16.dp))
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
    ) {
        items(books) { book ->
            with(book) {
                if (title != null) {
                    ResultListItem(
                        title,
                        book.authors,
                        book.imageUrl,
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun ResultListItem(
    title: String,
    authorsList: List<String>?,
    imageUrl: String?,
) {
    val image = imageUrl?.replace("http", "https") ?: ""

    Card(Modifier.padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = image,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .padding(10.dp),

                error = painterResource(id = R.drawable.book_placeholder),
                placeholder = painterResource(id = R.drawable.book_placeholder),
            )

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(text = "Title: $title", textAlign = TextAlign.Start)
                Text(
                    text = getAuthorText(authorsList),
                    textAlign = TextAlign.Start,
                )
            }
        }
    }
}

private fun getAuthorText(authorsList: List<String>?): String {
    val authorLabel =
        "Author" + (if (authorsList != null && authorsList.size > 1) "s" else "") + ":"
    var authors = ""
    authorsList?.forEach { a ->
        authors += if (authors.isNotEmpty()) ", $a" else a
    }
    if (authors.isEmpty()) {
        authors = "Unknown"
    }

    return "$authorLabel $authors"
}

@Preview(showBackground = true)
@Composable
private fun EmptyListPreview() {
    ResultScreenView(
        BookSearchResultState.Success(listOf()),
        {},
    ) {}
}

@Preview(showBackground = true)
@Composable
private fun ResultListPreview() {
    ResultScreenView(
        BookSearchResultState.Success(
            listOf(
                Book("Title", listOf("Author"), ""),
            ),
        ),
        {},
    ) {}
}
