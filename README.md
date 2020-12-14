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

By using Fake, we are trying to make the development of Compose easier.
For example, this app allows you to interact with the Android Studio Preview as if it were a real app.
This is possible by creating a Fake ViewModel and making it reliable.

Preview GIF

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
