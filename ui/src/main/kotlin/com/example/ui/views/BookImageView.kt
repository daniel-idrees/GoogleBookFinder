package com.example.ui.views

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ui.R

private val imageSize = 100.dp

@Composable
fun BookImageView(imageUrl: String?) {
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        modifier = Modifier
            .size(imageSize)
            .padding(spaceXS),

        error = painterResource(id = R.drawable.book_placeholder),
        placeholder = painterResource(id = R.drawable.book_placeholder),
    )
}
