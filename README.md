# DroidKaigi 2021 official news app[WIP]

module graph

![image](https://user-images.githubusercontent.com/1386930/102029518-0f1ef880-3df2-11eb-91cc-c52adfbbde3e.png)

# Android

Invoke @Composable function in android module.
Distribute the ViewModel by Ambient.

```kotlin
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<RealNewsViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup(viewModel)
    }

    private fun ComponentActivity.setup(viewModel: INewsViewModel) {
        setContent {
            ProvideNewsViewModel(viewModel) {
                DroidKaigiApp()
            }
        }
    }
}
```

# Compose

Split Composable according to the following policy.
* [State hoisting](https://developer.android.com/jetpack/compose/state)(pass the value and receive the event) as much as possible.
* If there are too many arguments or the arguments know too much detail, Pass interface.



![image](https://user-images.githubusercontent.com/1386930/102029530-1a722400-3df2-11eb-9e41-50010f455f0e.png)

```kotlin
/**
 * stateful
 */
@Composable
fun NewsScreen(
   // ...
) {
    // ...
    val newsViewModel = newsViewModel()
    val newsContents: NewsContents by newsViewModel.filteredNewsContents.collectAsState(
        initial = newsViewModel.filteredNewsContents.value
    )
    val onFavoriteChange: (News) -> Unit = {
        newsViewModel.onToggleFavorite(it)
    }
    // ...
    NewsScreen(
        // ...
        newsContents = newsContents,
        onFavoriteChange = onFavoriteChange,
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
// ...
```

# Testable & Previewable & Faster Build

This year's DroidKaigi will try to improve testing, preview and build by using Fake.
For example, this app allows you to interact with the Android Studio Preview as if it were a real app.

![preview](https://user-images.githubusercontent.com/1386930/102705021-25332a00-42c6-11eb-9f6a-c675a2922b1f.gif)

This is possible by creating a Fake ViewModel and making it reliable. 

## How to make Fake's ViewModel reliable

Do the same test for Fake and the real thing by testing against the interface. This makes Fake's
 ViewModel reliable. 
This technique of doing the same test against Fake was introduced in Build testable apps for Android (Google I / O '19).

![image](https://user-images.githubusercontent.com/1386930/102705934-51ec3f00-42d0-11eb-8da2-999534f9c15b.png)

## How to make it previewable and testable

The way to do a preview is to distribute Fake's ViewModel.

```
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

![image](https://user-images.githubusercontent.com/1386930/102029537-1f36d800-3df2-11eb-86f7-e06324233dba.png)

## How to make the build faster

Dagger Hilt uses Module in the classpath to create a dependency graph. 
Therefore, change the Dagger module used in Fake and Real (this time we will use Product Flavor because it is small), 

In fake product flavor.

```kotlin
@InstallIn(ActivityComponent::class)
@Module
class ViewModelModule {
    @Provides
    fun provideNewsViewModel(): INewsViewModel {
        return fakeNewsViewModel()
    }
}
```

And only refer to the data module for the "real" product flavor.
In this way, you can reduce the build time by reducing the product flavor referenced during Fake.

```groovy
  realImplementation project (":data")
```
