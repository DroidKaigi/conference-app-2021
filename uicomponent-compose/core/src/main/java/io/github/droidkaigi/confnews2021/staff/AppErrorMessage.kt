package io.github.droidkaigi.confnews2021.staff

import android.content.Context
import io.github.droidkaigi.confnews2021.AppError
import io.github.droidkaigi.confnews2021.uicomponent.core.R

fun AppError.getReadableMessage(context: Context): String = when (this) {
    is AppError.ApiException.ServerException -> {
        context.getString(R.string.error_server)
    }
    is AppError.ApiException.NetworkException -> {
        context.getString(R.string.error_network)
    }
    is AppError.ApiException.TimeoutException -> {
        context.getString(R.string.error_network)
    }
    else -> {
        context.getString(R.string.error_unknown)
    }
}
