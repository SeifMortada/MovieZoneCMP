package favourites.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.gameZone.models.Genre
import com.gameZone.models.Movie
import com.gameZone.models.MovieDetails
import com.gameZone.models.ProductionCompany
import com.gamezone.ui.composables.MovieCard
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FavouritesRoute(
    viewModel: FavouritesViewModel = koinViewModel(),
    onBackClick: () -> Unit,
) {
    val moviesState = viewModel.uiState.collectAsStateWithLifecycle().value
    FavouritesScreen(movies = moviesState, viewModel::removeFromFavourites)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FavouritesScreen(
    movies: List<Movie>,
    onDelete: (Int) -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favourites") },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.surface
                )
            )
        },
        containerColor = colors.background
    ) { padding ->
        when {
            movies.isNotEmpty() -> {
                MovieContent(
                    movies = movies,
                    padding = padding,
                    scrollBehavior = scrollBehavior.nestedScrollConnection,
                    onDelete = onDelete
                )
            }
            else -> EmptyScreen()

        }
    }
}

@Composable
fun EmptyScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("No movies found")
    }
}

@Composable
fun MovieContent(
    movies: List<Movie>,
    onDelete: (Int) -> Unit,
    padding: PaddingValues,
    scrollBehavior: NestedScrollConnection
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .nestedScroll(scrollBehavior),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(movies) { movie ->
            FavMovieCard(movie = movie, onDelete = onDelete)
        }
    }
}

@Composable
fun FavMovieCard(movie: Movie, onDelete: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = movie.posterPath,
            contentDescription = movie.title,
            modifier = Modifier
                .size(width = 100.dp, height = 150.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = movie.releaseDate,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        IconButton(onClick = { onDelete(movie.id) }) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Delete"
            )
        }
    }
}



@Preview()
@Composable
fun MovieDetailsScreenPreview() {
    // Dummy data for preview
    val dummyCompanies = listOf(
        ProductionCompany(
            id = 1,
            name = "DreamWorks",
            logoPath = "https://upload.wikimedia.org/wikipedia/commons/7/78/DreamWorks_Animation_SKG_logo_with_text.png",
            originCountry = "US"
        ),
        ProductionCompany(
            id = 2,
            name = "Pixar",
            logoPath = "https://upload.wikimedia.org/wikipedia/en/5/5d/Pixar_Animation_Studios_Logo.svg",
            originCountry = "US"
        )
    )

    val dummyMovie = MovieDetails(
        id = 123,
        title = "How to Train Your Dragon",
        overview = "On the rugged isle of Berk, where Vikings and dragons have been bitter enemies for generations, Hiccup stands apart, defying tradition when he befriends Toothless.",
        productionCompanies = dummyCompanies,
        releaseDate = "2025-06-06",
        revenue = 596554228,
        spokenLanguage = listOf("English", "Russian"),
        status = "Released",
        voteCount = 1248,
        voteAverage = 8.072,
        imageUrl = "https://image.tmdb.org/t/p/original/8J6UlIFcU7eZfq9iCLbgc8Auklg.jpg",
        homepage = "https://www.welcometoberk.com/",
        genres = listOf<Genre>(Genre(1, "Animation"), Genre(2, "Adventure")),
        budget = 20000
    )
    val movies = listOf(
        Movie(
            id = 123,
            title = "How to Train Your Dragon",
            overview = "On the rugged isle of Berk, where Vikings and dragons have been bitter enemies for generations, Hiccup stands apart, defying tradition when he befriends Toothless.",
            releaseDate = "2025-06-06",
            posterPath = "",
            genres = listOf(123)
        )
    )
    FavouritesScreen(movies = movies,   {})
}
