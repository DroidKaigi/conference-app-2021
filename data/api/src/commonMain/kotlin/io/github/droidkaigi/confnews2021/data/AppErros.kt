package io.github.droidkaigi.confnews2021.data

import io.github.droidkaigi.confnews2021.AppError
import io.ktor.client.features.ResponseException
import io.ktor.network.sockets.SocketTimeoutException
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
        is TimeoutCancellationException, is SocketTimeoutException -> {
            AppError.ApiException
                .TimeoutException(this)
        }
        else -> AppError.UnknownException(this)
    }
}
