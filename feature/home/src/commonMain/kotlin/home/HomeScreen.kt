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
import com.gameZone.models.Movie
import com.gameZone.models.TvShow
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = koinViewModel(),
    onMovieClick: (Int) -> Unit,
    paddingValues: PaddingValues
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    HomeScreen(uiState,onMovieClick,paddingValues)

}

@Composable
fun HomeScreen(
    state: HomeUiState,
    onMovieClick: (Int) -> Unit,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when {
            state.error != null -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(state.error)
                }
            }

            state.isLoading -> CircularProgressIndicator()

            else -> {
                HomeContent(state,onMovieClick)
            }
        }
    }
}

@Composable
fun HomeContent(state: HomeUiState, onMovieClick: (Int) -> Unit){
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
                    PopularMovieCard(it,onMovieClick)
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
                    PopularMovieCard(it,onMovieClick)
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
                    PopularTvShowCard(it)
                    Spacer(Modifier.width(12.dp))
                }
            }
        }
    }
}

@Composable
fun PopularMovieCard(
    movie: Movie,
    onClick: (Int) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .width(140.dp)
            .padding(8.dp)
            .clickable { onClick(movie.id) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MovieImage(movie.posterPath)
        Spacer(Modifier.height(8.dp))
        Text(
            text = movie.title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 2
        )
        Spacer(Modifier.height(4.dp))
        MediumWhiteText(movie.overview, maxLines = 3)
        Spacer(Modifier.height(12.dp))
        SmallMagentaText(movie.releaseDate)
    }
}

@Composable
fun PopularTvShowCard(
    tvShow: TvShow
) {
    Column(
        modifier = Modifier
            .width(140.dp)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MovieImage(tvShow.posterPath)
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

@Composable
fun SmallMagentaText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun MediumWhiteText(text: String, maxLines: Int = Int.MAX_VALUE) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onBackground,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun MovieImage(imageUrl: String) {
    Box(
        modifier = Modifier
            .width(140.dp)
            .aspectRatio(2f / 3f)
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(18.dp)),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = imageUrl,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(18.dp))
        )
    }
}


@Preview
@Composable
private fun CharacterScreenPreview() {
}
