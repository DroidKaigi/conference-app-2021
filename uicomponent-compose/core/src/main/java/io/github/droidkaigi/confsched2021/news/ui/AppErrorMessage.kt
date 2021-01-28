package io.github.droidkaigi.confsched2021.news.ui

import android.content.Context
import io.github.droidkaigi.confsched2021.news.AppError
import io.github.droidkaigi.confsched2021.news.uicomponent.core.R

fun AppError.getReadableMessage(context: Context): String = when (this) {
    is AppError.ApiException.ServerException -> {
        context.getString(R.string.error_server)
    }
    is AppError.ApiException.NetworkException -> {
        context.getString(R.string.error_network)
    }
    else -> {
        context.getString(R.string.error_unknown)
    }
}
