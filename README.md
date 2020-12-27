# DroidKaigi 2021 official news app[WIP]

DroidKaigi 2021 official news app

# Contributing

TBD

# Features

# Development Environment

## Multi module project

TBD

## Kotlin Multiplatform

TBD

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
) {
    val scaffoldState = rememberBackdropScaffoldState(BackdropValue.Concealed)
    var selectedTab by remember<MutableState<NewsTabs>> { mutableStateOf(NewsTabs.Home) }

    val (
        state,
        effectFlow,
        dispatch,
    ) = use(newsViewModel())

    LaunchedEffect(subject = effectFlow) {
        effectFlow.collect {
            when (it) {
                is NewsViewModel.Effect.OpenDetail -> {
                  // ...
                }
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

    LaunchedEffect(subject = effectFlow) {
        effectFlow.collect {
            when (it) {
                is NewsViewModel.Effect.OpenDetail -> {
                  // ...
                }
            }
        }
    }
```

ViewModel Interface

```kotlin
interface UnidirectionalViewModel<EVENT, EFFECT, STATE> {
    val state: StateFlow<STATE>
    val effect: Flow<EFFECT>
    fun event(event: EVENT)
}
```

`use(viewModel)` function for dispatching events and subscribing to states

```kotlin
@Composable
inline fun <reified EVENT, EFFECT, STATE> use(viewModel: UnidirectionalViewModel<EVENT, EFFECT, STATE>):
    Triple<STATE, Flow<EFFECT>, (EVENT) -> Unit> {
    val state by viewModel.state.collectAsState()

    val dispatch: (EVENT) -> Unit = { event ->
        viewModel.event(event)
    }
    return Triple(state, viewModel.effect, dispatch)
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
In that case, the required task is `:compose-uicomponent:compileDebugKotlin`. 
Therefore, there is no need to build the data module that contains the definitions such as API and the build of Android dex, so you can quickly build and check.

![image](https://user-images.githubusercontent.com/1386930/102029518-0f1ef880-3df2-11eb-91cc-c52adfbbde3e.png)

## Overall architecture

![image](https://user-images.githubusercontent.com/1386930/103167973-078c3480-4873-11eb-92a2-687314175450.png)

# Design

TBD