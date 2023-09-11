package com.example.googlebookfinder.ui.main.views

sealed class Screen(val route: String)
object ResultScreen : Screen("result")
object SearchScreen : Screen("search")
