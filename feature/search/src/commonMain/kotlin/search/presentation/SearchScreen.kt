package search.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.gameZone.models.Movie
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Backward
import compose.icons.fontawesomeicons.solid.Search
import compose.icons.fontawesomeicons.solid.SearchMinus
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchRoute(
    viewModel: SearchViewModel = koinViewModel(),
    onBackClick: () -> Unit,
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    SearchScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onSearchQueryChange = viewModel::onQueryChange,
        onSearchCleared = viewModel::onClearQuery
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreen(
    uiState: SearchUiState,
    onBackClick: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onSearchCleared: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Search") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(FeatherIcons.ArrowLeft, contentDescription = "Back")
                    }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.surface
                )
            )
        },
        containerColor = colors.background
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .background(colors.background)
        ) {
            // --- Search Bar
            SearchBar(
                query = uiState.query,
                onQueryChange = { onSearchQueryChange(it)},
                onClearQuery = { onSearchCleared()}
            )

            Spacer(Modifier.height(16.dp))

            // --- Recent Searches
            if (uiState.recentSearches.isNotEmpty()) {
                Text("Recent Searches", style = MaterialTheme.typography.titleMedium)
                RecentSearchesRow(
                    recentSearches = uiState.recentSearches,
                    onClick = { }
                )
            }

            Spacer(Modifier.height(16.dp))

            // --- Results or Empty / No Results
            when {
                uiState.isLoading -> CircularProgressIndicator()
                uiState.query.isEmpty() -> DefaultEmptyScreen()
                uiState.movies.isEmpty() -> NoResultsScreen()
                else -> MovieResultsGrid(
                    movies = uiState.movies,
                    onMovieClick = { }
                )
            }
        }
    }
}

@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit, onClearQuery: () -> Unit) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        leadingIcon = { Icon(FontAwesomeIcons.Solid.Search, contentDescription = "Search", modifier = Modifier.size(30.dp)) },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = onClearQuery) {
                    Icon(FontAwesomeIcons.Solid.SearchMinus, contentDescription = "Clear")
                }
            }
        },
        placeholder = { Text("Action") },
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}

@Composable
fun RecentSearchesRow(recentSearches: List<String>, onClick: (String) -> Unit) {
    FlowRow(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        recentSearches.forEach { term ->
            OutlinedButton(
                onClick = { onClick(term) },
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color.Gray),
                modifier = Modifier.padding(end = 8.dp, bottom = 8.dp)
            ) {
                Text(term)
            }

        }
    }
}

@Composable
fun MovieResultsGrid(movies: List<Movie>, onMovieClick: (Movie) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(movies) { movie ->
            MovieCard(movie, onClick = { onMovieClick(movie) })
        }
    }
}

@Composable
fun MovieCard(movie: Movie, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF222825))
            .clickable(onClick = onClick)
    ) {
        AsyncImage(
            model = movie.posterPath,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Text(
            movie.title,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun DefaultEmptyScreen() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Search for a movie", color = Color.Gray)
    }
}

@Composable
fun NoResultsScreen() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("No movies found", color = Color.Gray)
    }
}

@Preview()
@Composable
fun SearchScreenPreview() {

SearchScreen(uiState = SearchUiState(
    query= "asd"
),{},{}) {  }
}
