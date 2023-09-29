package com.example.ui.nav

import android.net.Uri

sealed class Screen(val route: String)
object ResultScreen : Screen("result/{$ResultScreenArgumentSearchQueryKey}")

object SearchScreen : Screen("search")

const val ResultScreenArgumentSearchQueryKey = "searchQuery"

fun createRouteWithPathArguments(route: String, vararg arguments: String): String {
    val builder = Uri.parse(route).buildUpon()
    arguments.forEach {
        builder.appendEncodedPath("{$it}")
    }
    return builder.build().toString()
}
