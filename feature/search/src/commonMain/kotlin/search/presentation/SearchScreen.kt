package search.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.gameZone.models.Movie
import com.gamezone.ui.theme.MovieZoneTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchRoute(
    viewModel: SearchViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onMovieClick: (Int) -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    SearchScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onSearchQueryChange = viewModel::onQueryChange,
        onSearchCleared = viewModel::onClearQuery,
        onRecentSearchClick = viewModel::onRecentSearchSelected,
        onMovieClick = onMovieClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreen(
    uiState: SearchUiState,
    onBackClick: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onSearchCleared: () -> Unit,
    onRecentSearchClick: (String) -> Unit,
    onMovieClick: (Int) -> Unit
) {
    val colors = MaterialTheme.colorScheme
    Surface {
        Column(
            Modifier.fillMaxSize()
                .background(colors.background)
                .padding(12.dp),
            verticalArrangement = Arrangement.Top
        ) {

            SearchBar(
                query = uiState.query,
                onQueryChange = { onSearchQueryChange(it) },
                onClearQuery = { onSearchCleared() },
                result = uiState.movies,
                onMovieClick = { onSearchQueryChange(it.title) }
            )
            Spacer(Modifier.height(16.dp))

            // --- Recent Searches
            if (uiState.recentSearches.isNotEmpty()) {
                Text(
                    "Recent Searches",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(start = 12.dp),
                    fontWeight = FontWeight.Bold
                )
                RecentSearchesRow(
                    recentSearches = uiState.recentSearches,
                    onClick = { onRecentSearchClick(it) }
                )
            }

            Spacer(Modifier.height(16.dp))

            // --- Results or Empty / No Results
            when {
                uiState.isLoading -> CircularProgressIndicator()
                uiState.query.isEmpty() -> DefaultEmptyScreen()
                uiState.movies.isEmpty() -> NoResultsScreen()
                else -> {
                    Text(
                        "Search Results",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(start = 12.dp),
                        fontWeight = FontWeight.Bold
                    )
                    MovieResultsGrid(
                        movies = uiState.movies,
                        onMovieClick = onMovieClick
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
    result: List<Movie> = emptyList(),
    onMovieClick: (Movie) -> Unit = {}
) {
    var isExpanded by remember { mutableStateOf(false) }

    SearchBar(
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = {
                    onQueryChange(it)
                    isExpanded = true
                },
                onSearch = {
                    onQueryChange(query)
                    isExpanded = false
                },
                expanded = isExpanded,
                onExpandedChange = { isExpanded = it },
                placeholder = { Text("Search") },
                leadingIcon = {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "Search",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = {
                            onClearQuery()
                            isExpanded = false
                        }) {
                            Icon(Icons.Outlined.Clear, contentDescription = "Clear")
                        }
                    }
                }
            )
        },
        expanded = isExpanded,
        onExpandedChange = { isExpanded = it },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            items(result) { movie ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onMovieClick(movie)
                            isExpanded = false
                        }
                        .padding(8.dp)
                ) {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}

@Composable
fun RecentSearchesRow(recentSearches: List<String>, onClick: (String) -> Unit) {
    FlowRow(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        recentSearches.forEachIndexed { index, term ->
            OutlinedButton(
                onClick = { onClick(term) },
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Color(0xFF3D5245)),
                modifier = Modifier
                    .height(58.dp)
                    .width(174.dp)
                    .padding(end = 8.dp, bottom = 8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color(0xFF1C2621),
                    contentColor = Color.White
                )
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(term, color = Color.White, fontSize = 14.sp)
                }
            }

        }
    }
}

@Composable
fun MovieResultsGrid(movies: List<Movie>, onMovieClick: (Int) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        items(movies) { movie ->
            MovieCard(movie, onClick = onMovieClick)
        }
    }
}

@Composable
fun MovieCard(movie: Movie, onClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF222825))
            .clickable(onClick = { onClick(movie.id) })
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
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.ErrorOutline,
                contentDescription = "Not Found",
                modifier = Modifier.size(150.dp)
            )
            Text("No movies found", color = Color.Gray)
        }
    }
}

@Preview()
@Composable
fun SearchScreenPreview() {
    MovieZoneTheme(darkTheme = true) {
        SearchScreen(
            uiState = SearchUiState(
                query = "asd"
            ), {}, {}, {}, {}) { }
    }
}
