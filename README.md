# GoogleBookFinder

This practice project uses the [Google Book API](https://developers.google.com/books/docs/v1/using) to retrieve book lists.

The aim of this project is to share an example of writing testable and maintainable code for Android apps.

In this repository you'll find:

 - Kotlin
 - Multi Module
 - Model-View-ViewModel (MVVM)
 - A single-activity architecture, using Navigation Compose
 - Hilt for dependency injection.
 - Jetpack Compose for building UI
 - Retrofit for network calls
 - Coroutine for asynchronous operations
 - Flow for handling data asynchronously
 - Mockito for unit testings with a collection of unit tests for the repository, use case and ViewModel.
 - Dependency injection using Hilt.
 - Coil for Image loading.

 - App Architecture as per [Google's Recommendation](https://developer.android.com/topic/architecture)
   - A presentation layer that contains Compose Views and a ViewModel.
   - A domain layer with a use case.
   - A data layer with a repository and a network data source that uses retrofit for network calls.


