package io.github.droidkaigi.confnews2021.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DaggerKtorNewsApi @Inject constructor(firebaseAuthApi: FirebaseAuthApi) :
    KtorNewsApi(firebaseAuthApi)
