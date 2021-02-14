# DroidKaigi 2021 official news app[WIP]xxxxx234234234

DroidKaigi 2021 official news app

# Contributing

TBD

# Features

# Development Environment

## Kotlin Multiplatform

TBD

## Compose

## Modern Development

* Jetpack Compose
* Kotlin Coroutines & Flows
* Dagger Hilt
* DataStore
* Kotlin Multiplatform

# Architecture

## Unidirectional data flow

Compose processing and ViewModel processing are performed by Unidirectional data flow.

![image](https://user-images.githubusercontent.com/1386930/103167463-a4000800-486e-11eb-87dd-29cbac2deafd.png)

### Compose unidirectional data flow

By performing [State hoisting](https://developer.android.com/jetpack/compose/state)(pass the value and receive the event), Jetpack Compose displays with unidirectional data flow.

```kotlin
/**
 * stateful
 */
@Composable
fun NewsScreen(
    onNavigationIconClick: () -> Unit,
    onDetailClick: (News) -> Unit,
) {
...

    val (
        state,
        effectFlow,
        dispatch,
    ) = use(newsViewModel())

    val context = AmbientContext.current
    effectFlow.collectInLaunchedEffect { effect ->
        when (effect) {
            is NewsViewModel.Effect.ErrorMessage -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    effect.appError.getReadableMessage(context)
                )
            }
        }
    }

    NewsScreen(
        // ...
        newsContents = state.filteredNewsContents,
        onFavoriteChange = {
            dispatch(NewsViewModel.Event.ToggleFavorite(news = it))
        },
        // ...
    )
}

/**
 * stateless
 */
@Composable
private fun NewsScreen(
    // ...
    newsContents: NewsContents,
    onFavoriteChange: (News) -> Unit,
    // ...
) {
    Column {
        BackdropScaffold(
            backLayerBackgroundColor = MaterialTheme.colors.primary,
            scaffoldState = scaffoldState,
            backLayerContent = {
                BackLayerContent(filters, onFavoriteFilterChanged)
            }
// ...
        )
// ...
    }
}
```

### ViewModel unidirectional data flow

This app handles the ViewModel with an MVI-like interface.

Compose

```kotlin
    val (
        state,
        effectFlow,
        dispatch,
    ) = use(newsViewModel())

    val context = AmbientContext.current
    effectFlow.collectInLaunchedEffect { effect ->
        when (effect) {
            is NewsViewModel.Effect.ErrorMessage -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    effect.appError.getReadableMessage(context)
                )
            }
        }
    }

    NewsScreen(
        // ...
        newsContents = state.filteredNewsContents,
        onFavoriteChange = {
            dispatch(NewsViewModel.Event.ToggleFavorite(news = it))
        },
        // ...
```

This `state`, `effectFlow` and `dispatch` are created from ViewModel.

`state` represents the state that the UI should display.
`effectFlow` represents a one-time event such as a Snackbar display.
And `dispatch` represents a change of state.


ViewModel Interface

```kotlin
interface UnidirectionalViewModel<EVENT, EFFECT, STATE> {
    val state: StateFlow<STATE>
    val effect: Flow<EFFECT>
    fun event(event: EVENT)
}
```

`use(viewModel)` function

```kotlin
@Composable
inline fun <reified STATE, EFFECT, EVENT> use(
    viewModel: UnidirectionalViewModel<EVENT, EFFECT, STATE>,
):
    StateEffectDispatch<STATE, EFFECT, EVENT> {
    val state by viewModel.state.collectAsState()

    val dispatch: (EVENT) -> Unit = { event ->
        viewModel.event(event)
    }
    return StateEffectDispatch(
        state = state,
        effectFlow = viewModel.effect,
        dispatch = dispatch
    )
}
```


## Testable & Previewable & Faster debug

Jetpack Compose is still a new technology. We are thinking of best practices.
We will try to improve testing, preview and build by using Fake.
For example, this app allows you to interact with the Android Studio Preview as if it were a real app.

![preview](https://user-images.githubusercontent.com/1386930/102705021-25332a00-42c6-11eb-9f6a-c675a2922b1f.gif)

This is possible by creating a Fake ViewModel and making it reliable.

### How to make Fake's ViewModel reliable

Do the same test for Fake and the real thing by testing against the interface. This makes Fake's
 ViewModel reliable.
This technique of doing the same test against Fake was introduced in "Build testable apps for
 Android" session(Google I/O'19).

![image](https://user-images.githubusercontent.com/1386930/102705934-51ec3f00-42d0-11eb-8da2-999534f9c15b.png)


You can also prevent forgetting to update by forcing the implementation with interface and .
Also, by forcing the implementation with interface and "(Exhaustive)[https://github.com/cashapp/exhaustive]", it is possible to prevent forgetting to update Fake's ViewModel.

```kotlin
override fun event(event: NewsViewModel.Event) {
    coroutineScope.launch {
        @Exhaustive
        when (event) {
            is NewsViewModel.Event.ChangeFavoriteFilter -> {
```

### How to make it previewable and testable

The way to do a preview is to distribute Fake's ViewModel.

```kotlin
@Preview(showBackground = true)
@Composable
fun NewsScreenPreview() {
    Conferenceapp2021newsTheme(false) {
        ProvideNewsViewModel(viewModel = fakeNewsViewModel()) {
            NewsScreen {
            }
        }
    }
}
```

### How to debug fast?

If you want to check the UI display, you can check it in the Preview of Android Studio.
In that case, the required task is `:uicomponent-compose:main:compileDebugKotlin`.
Therefore, there is no need to build the data module that contains the definitions such as API and the build of Android dex, so you can quickly build and check.
Also, changes to the data layer do not affect the ui module, so you can build faster.

![image](https://user-images.githubusercontent.com/1386930/107300623-3bd35180-6abd-11eb-94e8-0c5169b9f35d.png)

## Overall architecture

![image](https://user-images.githubusercontent.com/1386930/103167973-078c3480-4873-11eb-92a2-687314175450.png)

# Design

TBD

# Trouble Shooting

## Android Gradle plugin requires Java 11 to run. You are currently using Java 1.X

JDK 1.8 is a requirement to build an Android project basically, but AGP 7.0 requires JDK 11. Please make sure you are using Java 11. Please note that the java version of Gradle Daemon is determined at the launch-time, so `./gradlew --stop` might be required for you.

## The option 'android.enableBuildCache' is deprecated.

`android.enableBuildCache` is *REMOVED* in AGP 7.0. `android.enableBuildCache=false` has no issue but builds randomly succeed with *true* value. This may affect to those who declare `android.enableBuildCache=true` in their `$GRADLE_USER_HOME/gradle.properties`. 
