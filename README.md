# DroidKaigi 2021 official app[WIP]

DroidKaigi 2021 official app

# Guides

- [Development](./#Development)
  - [Features](./#Features)
  - [Design](./#Design)
  - [Try it out](./#Try_it_out)
  - [Contributing](./#Contributing)
  - [Requirements](./#Requirements)
- [Tech Stacks](./#Tech_Stacks)
  - [Kotlin Multiplatform](./#Kotlin_Multiplatform)
  - [Jetpack Compose](./#Jetpack_Compose)
- [Architecture](./#Architecture)
- [Trouble Shooting](./#Trouble_Shooting)

# Development

## Features

Now this app has the Feed feature. We will deliver Feed and events related to DroidKaigi.

<img src="https://user-images.githubusercontent.com/1386930/108440402-662fc680-7296-11eb-948d-a259957a9c53.png" width="240" />

## Design

https://www.figma.com/file/IFlrbfmBSdYvUz7VmSzfLV/DroidKaigi_2021_official_app

## Try it out

The builds being distributed through mobile app distribution services.

- Production (TBR)
- Try the latest staging through [<img src="https://dply.me/o701pp/button/small" alt="Download to device">](https://dply.me/o701pp#install)
- and more... (tbw)

## Contributing

We always welcome any and all contributions! See [CONTRIBUTING.md](CONTRIBUTING.md) for more information

For Japanese speakers, please see [CONTRIBUTING.ja.md](CONTRIBUTING.ja.md)

## Requirements

Latest Android Studio Arctic Fox and higher. You can download it from [this page](https://developer.android.com/studio/preview).

# Tech Stacks

## Kotlin Multiplatform

TBD

## Jetpack Compose

## Modern Development

* Jetpack Compose
* Kotlin Coroutines & Flows
* Dagger Hilt
* DataStore
* Kotlin Multiplatform

# Architecture

## Unidirectional data flow

Compose processing and ViewModel processing are performed by Unidirectional data flow.

<img width="480" src="https://user-images.githubusercontent.com/1386930/108588098-bf811e00-739a-11eb-8bf9-1cfa8bea9464.png" />

### Compose unidirectional data flow

By performing [State hoisting](https://developer.android.com/jetpack/compose/state)(pass the value and receive the event), Jetpack Compose displays with unidirectional data flow.

```kotlin
/**
 * stateful
 */
@Composable
fun FeedScreen(
    onNavigationIconClick: () -> Unit,
    onDetailClick: (Feed) -> Unit,
) {
...

    val (
        state,
        effectFlow,
        dispatch,
    ) = use(FeedViewModel())

    val context = LocalContext.current
    effectFlow.collectInLaunchedEffect { effect ->
        when (effect) {
            is FeedViewModel.Effect.ErrorMessage -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    effect.appError.getReadableMessage(context)
                )
            }
        }
    }

    FeedScreen(
        // ...
        FeedContents = state.filteredFeedContents,
        onFavoriteChange = {
            dispatch(FeedViewModel.Event.ToggleFavorite(Feed = it))
        },
        // ...
    )
}

/**
 * stateless
 */
@Composable
private fun FeedScreen(
    // ...
    FeedContents: FeedContents,
    onFavoriteChange: (Feed) -> Unit,
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
    ) = use(FeedViewModel())

    val context = LocalContext.current
    effectFlow.collectInLaunchedEffect { effect ->
        when (effect) {
            is FeedViewModel.Effect.ErrorMessage -> {
                scaffoldState.snackbarHostState.showSnackbar(
                    effect.appError.getReadableMessage(context)
                )
            }
        }
    }

    FeedScreen(
        // ...
        FeedContents = state.filteredFeedContents,
        onFavoriteChange = {
            dispatch(FeedViewModel.Event.ToggleFavorite(Feed = it))
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

![preview](https://user-images.githubusercontent.com/1386930/108589503-ea22a500-73a1-11eb-8f71-d323a8628e7d.gif)

This is possible by creating a Fake ViewModel and making it reliable.

### How to make Fake's ViewModel reliable

Do the same test for Fake and the real thing by testing against the interface. This makes Fake's
 ViewModel reliable.
This technique of doing the same test against Fake was introduced in "Build testable apps for
 Android" session(Google I/O'19).

<img width="480" src="https://user-images.githubusercontent.com/1386930/108588041-79c45580-739a-11eb-966d-321ddd58db74.png" />


You can also prevent forgetting to update by forcing the implementation with interface and .
Also, by forcing the implementation with interface and " [Exhaustive](https://github.
com/cashapp/exhaustive) ", it is possible to prevent forgetting to update Fake's ViewModel.

```kotlin
override fun event(event: FeedViewModel.Event) {
    coroutineScope.launch {
        @Exhaustive
        when (event) {
            is FeedViewModel.Event.ChangeFavoriteFilter -> {
```

### How to make it previewable and testable

This app uses fake's ViewModel to enable preview.

<img width="480" src="https://user-images.githubusercontent.com/1386930/108588048-85178100-739a-11eb-8c4b-780b7f5d6dbd.png" />

```kotlin
@Preview(showBackground = true)
@Composable
fun FeedScreenPreview() {
    Conferenceapp2021FeedTheme(false) {
        ProvideFeedViewModel(viewModel = fakeFeedViewModel()) {
            FeedScreen {
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

<img width="480" src="https://user-images.githubusercontent.com/1386930/108615484-0b3dd100-7448-11eb-9ffb-87c38192deea.png" />

## Overall architecture

<img width="480" src="https://user-images.githubusercontent.com/1386930/108588059-91034300-739a-11eb-8fdb-55d79861b4b3.png" />

# Trouble Shooting

## Android Gradle plugin requires Java 11 to run. You are currently using Java 1.X

JDK 1.8 is a requirement to build an Android project basically, but AGP 7.0 requires JDK 11. Please make sure you are using Java 11. Please note that the java version of Gradle Daemon is determined at the launch-time, so `./gradlew --stop` might be required for you.

## The option 'android.enableBuildCache' is deprecated.

`android.enableBuildCache` is *REMOVED* in AGP 7.0. `android.enableBuildCache=false` has no issue but builds randomly succeed with *true* value. This may affect to those who declare `android.enableBuildCache=true` in their `$GRADLE_USER_HOME/gradle.properties`.
