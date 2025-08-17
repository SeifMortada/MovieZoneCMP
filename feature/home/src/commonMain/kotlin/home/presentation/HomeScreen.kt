package home.presentation


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue
import com.gameZone.models.Movie
import com.gameZone.models.TvShow
import com.gamezone.ui.composables.LoadImage
import com.gamezone.ui.composables.MediumWhiteText
import com.gamezone.ui.composables.MovieCard
import com.gamezone.ui.composables.SmallMagentaText
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = koinViewModel(),
    onMovieClick: (Int) -> Unit,
    paddingValues: PaddingValues
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(
        state = uiState,
        onAction = { action ->
            when (action) {
                is HomeActions.OnMovieClick -> onMovieClick(action.movieId)
                else -> viewModel.onAction(action)
            }
        },
        paddingValues = paddingValues
    )

}

@Composable
fun HomeScreen(
    state: HomeUiState,
    onAction: (HomeActions) -> Unit,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when {
            state.error != null -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = state.error,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(Modifier.height(16.dp))
                    ElevatedButton(onClick = { onAction(HomeActions.Retry) }) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Retry")
                        Spacer(Modifier.width(8.dp))
                        Text("Retry")
                    }
                }
            }

            state.isLoading -> CircularProgressIndicator()

            else -> {
                HomeContent(
                    state = state,
                    onAction = onAction
                )
            }
        }
    }
}

@Composable
fun HomeContent(
    state: HomeUiState,
    onAction: (HomeActions) -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(vertical = 8.dp, horizontal = 12.dp)) {
        item {
            Text(
                text = "Popular movies",
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 8.dp)
            )
        }
        item {
            LazyRow(
                modifier = Modifier
                    .padding(bottom = 24.dp)
            ) {
                items(state.popularMovies) {
                    MovieCard(
                        movie = it,
                        onClick = { movieId ->
                            onAction(HomeActions.OnMovieClick(movieId))
                        },
                        showFavoriteIcon = true,
                        addFavourite = { movie ->
                            onAction(HomeActions.AddMovieToFavourites(movie))
                        },
                        removeFavourite = { movie ->
                            onAction(HomeActions.RemoveMovieFromFavourites(movie))
                        }
                    )
                    Spacer(Modifier.width(12.dp))
                }
            }
        }

        item {
            Text(
                text = "Top Rated movies",
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 8.dp)
            )
        }
        item {
            LazyRow {
                items(state.topRatedMovies) {
                    MovieCard(it, { movieId -> onAction(HomeActions.OnMovieClick(movieId)) })
                    Spacer(Modifier.width(12.dp))
                }
            }
        }
        item {
            Text(
                text = "Popular TV Shows",
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 8.dp)
            )
            LazyRow {
                items(state.popularTvShows) {
                    PopularTvShowCard(
                        it,
                        { tvShow -> onAction(HomeActions.AddTvShowToFavourites(tvShow)) })
                    Spacer(Modifier.width(12.dp))
                }
            }
        }
    }
}

@Composable
fun PopularTvShowCard(
    tvShow: TvShow,
    onTvShowSaved: (TvShow) -> Unit
) {
    Column(
        modifier = Modifier
            .width(140.dp)
            .padding(8.dp)
            .clickable {
                onTvShowSaved(tvShow)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoadImage(tvShow.posterPath)
        Spacer(Modifier.height(8.dp))
        Text(
            text = tvShow.name,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(4.dp))
        MediumWhiteText(tvShow.overview, maxLines = 3)
        Spacer(Modifier.height(12.dp))
        SmallMagentaText(tvShow.firstAirDate)
        Spacer(Modifier.height(4.dp))
        SmallMagentaText(tvShow.originalLanguage.toString())
    }
}

@Preview
@Composable
private fun CharacterScreenPreview() {
    MaterialTheme {
        HomeScreen(
            state = HomeUiState(
                popularMovies = List(5) {
                    Movie(it, "Movie $it", "/$it.jpg", "Overview $it", "2023-01-01", listOf(100))
                },
                topRatedMovies = List(5) {
                    Movie(
                        it + 5,
                        "Top Rated Movie ${it + 5}",
                        "/${it + 5}.jpg",
                        "Top Rated Overview ${it + 5}",
                        "2023-02-01",
                        listOf(100)
                    )
                },
                popularTvShows = List(5) {
                    TvShow(
                        it,
                        "TV Show $it",
                        "/tv$it.jpg",
                        "TV Overview $it",
                        "2023-03-01",
                        "es",
                        listOf(1, 2, 3),
                    )
                },
                isLoading = false,
                error = null
            ),
            onAction = {},
            paddingValues = PaddingValues(0.dp)
        )
    }
}
