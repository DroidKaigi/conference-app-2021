package io.github.droidkaigi.confsched2021.news.data

import io.github.droidkaigi.confsched2021.news.AppError
import io.ktor.client.features.ResponseException
import io.ktor.util.cio.ChannelReadException
import kotlinx.coroutines.TimeoutCancellationException

fun Throwable?.toAppError(): AppError? {
    return when (this) {
        null -> null
        is AppError -> this
        is ResponseException ->
            return AppError.ApiException.ServerException(this)
        is ChannelReadException ->
            return AppError.ApiException.NetworkException(this)
        is TimeoutCancellationException -> AppError.ApiException.NetworkException(this)
        else -> AppError.UnknownException(this)
    }
}