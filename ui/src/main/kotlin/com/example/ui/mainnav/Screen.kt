package com.example.ui.mainnav

import android.net.Uri
import com.example.ui.result.nav.RESULT_ROUTE
import com.example.ui.search.nav.SEARCH_ROUTE

sealed class Screen(val route: String)
object ResultScreen : Screen(RESULT_ROUTE)

object SearchScreen : Screen(SEARCH_ROUTE)

fun createRouteWithPathArguments(route: String, vararg arguments: String): String {
    val builder = Uri.parse(route).buildUpon()
    arguments.forEach {
        builder.appendEncodedPath("{$it}")
    }
    return builder.build().toString()
}
