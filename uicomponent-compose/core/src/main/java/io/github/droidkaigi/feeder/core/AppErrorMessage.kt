package io.github.droidkaigi.feeder.core

import android.content.Context
import io.github.droidkaigi.feeder.AppError
import io.github.droidkaigi.feeder.Theme

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
