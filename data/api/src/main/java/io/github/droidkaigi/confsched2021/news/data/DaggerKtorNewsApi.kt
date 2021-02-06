package io.github.droidkaigi.confsched2021.news.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DaggerKtorNewsApi @Inject constructor(firebaseAuthApi: FirebaseAuthApi) :
    KtorNewsApi(firebaseAuthApi)
