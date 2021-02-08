package io.github.droidkaigi.confnews2021.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface UserDataStore {
    fun favorites(): Flow<Set<String>>
    suspend fun addFavorite(id: String)
    suspend fun removeFavorite(id: String)
}

fun fakeUserDataStore() = object : UserDataStore {
    private val stateFlow = MutableStateFlow(setOf<String>())
    override fun favorites(): Flow<Set<String>> {
        return stateFlow
    }

    override suspend fun addFavorite(id: String) {
        stateFlow.value = stateFlow.value + id
    }

    override suspend fun removeFavorite(id: String) {
        stateFlow.value = stateFlow.value - id
    }
}
