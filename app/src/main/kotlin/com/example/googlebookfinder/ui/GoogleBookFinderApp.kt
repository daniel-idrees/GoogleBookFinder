package com.example.googlebookfinder.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.googlebookfinder.ui.nav.MainNavHost
import com.example.ui.style.GoogleBookFinderTheme

@Composable
fun GoogleBookFinderApp() {
    GoogleBookFinderTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            MainNavHost()
        }
    }
}
