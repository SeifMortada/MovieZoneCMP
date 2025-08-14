package home


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import coil3.compose.AsyncImage
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.ImageLoader
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
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    HomeScreen(
        state = uiState,
        onMovieClick = onMovieClick,
        onTvShowSaved = viewModel::saveTvShow,
        addFavourite = viewModel::addMovieToFavourites,
        paddingValues = paddingValues,
    )

}

@Composable
fun HomeScreen(
    state: HomeUiState,
    onMovieClick: (Int) -> Unit,
    onTvShowSaved: (TvShow) -> Unit,
    addFavourite: (Movie) -> Unit,
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
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(state.error)
                }
            }

            state.isLoading -> CircularProgressIndicator()

            else -> {
                HomeContent(state, onMovieClick, onTvShowSaved, addFavourite)
            }
        }
    }
}

@Composable
fun HomeContent(
    state: HomeUiState,
    onMovieClick: (Int) -> Unit,
    onTvShowSaved: (TvShow) -> Unit,
    addFavourite: (Movie) -> Unit
) {
    LazyColumn {
        item {
            Text(
                text = "Popular movies",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                items(state.popularMovies) {
                    MovieCard(it, onMovieClick, isFavourite = true, addFavourite = addFavourite)
                    Spacer(Modifier.width(12.dp))
                }
            }
        }
        // Title under the popular movies, for future Now Playing movies section
        item {
            Text(
                text = "Top Rated movies",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }
        item {
            LazyRow {
                items(state.topRatedMovies) {
                    MovieCard(it, onMovieClick)
                    Spacer(Modifier.width(12.dp))
                }
            }
        }
        item {
            Text(
                text = "Popular TV Shows",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            LazyRow {
                items(state.popularTvShows) {
                    PopularTvShowCard(it, onTvShowSaved)
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
            onMovieClick = {},
            onTvShowSaved = {},
            addFavourite = {},
            paddingValues = PaddingValues(0.dp)
        )
    }
}
