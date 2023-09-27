package com.example.googlebookfinder.ui.main.views

import android.net.Uri

sealed class Screen(val route: String)
object ResultScreen : Screen("result")
object SearchScreen : Screen("search")

fun createRouteWithPathArguments(route: String, vararg arguments: String): String {
    val builder = Uri.parse(route).buildUpon()
    arguments.forEach {
        builder.appendEncodedPath("{$it}")
    }
    return builder.build().toString()
}