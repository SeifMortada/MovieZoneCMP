package details.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.gameZone.models.Genre
import com.gameZone.models.Movie
import com.gameZone.models.MovieDetails
import com.gameZone.models.ProductionCompany
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MovieDetailsRoute(
    movieId: Int,
    viewModel: MovieDetailsViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    paddingValues: PaddingValues
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    LaunchedEffect(movieId) {
        viewModel.load(movieId)
    }
    MovieDetailsScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        addToFavoritesClicked = viewModel::addToFavorites,
        paddingValues = paddingValues
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieDetailsScreen(
    uiState: MovieDetailsUiState,
    onBackClick: () -> Unit,
    addToFavoritesClicked: (MovieDetails) -> Unit,
    paddingValues: PaddingValues
) {
    val colors = MaterialTheme.colorScheme
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }
    var showSheet by remember { mutableStateOf(true) } // open by default
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.movieDetails?.title ?: "") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.surface
                )
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = colors.background,
        modifier = Modifier.fillMaxSize().padding(bottom = paddingValues.calculateBottomPadding())
    ) { padding ->
        when {
            uiState.isLoading -> Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = colors.primary)
            }

            uiState.error != null -> Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Error: ${uiState.error}",
                    color = colors.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            uiState.movieDetails != null -> {
                ScreenContent(
                    uiState = uiState,
                    addToFavoritesClicked = addToFavoritesClicked,
                    snackbarHostState = snackbarHostState,
                    sheetState = sheetState,
                    scrollBehavior = scrollBehavior,
                    onDialogDismiss = onBackClick
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(
    uiState: MovieDetailsUiState,
    addToFavoritesClicked: (MovieDetails) -> Unit,
    snackbarHostState: SnackbarHostState,
    sheetState: SheetState,
    onDialogDismiss: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    Column {
        CollapsibleImage(imageUrl = uiState.movieDetails?.imageUrl)
        ModalBottomSheet(
            contentWindowInsets = { WindowInsets(0) },
            onDismissRequest = onDialogDismiss,
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxHeight()
        ) {
            MovieContent(
                details = uiState.movieDetails!!,
                scrollBehavior = scrollBehavior,
                addToFavoritesClicked = addToFavoritesClicked,
                snackbarHostState = snackbarHostState
            )

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieContent(
    details: MovieDetails,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    addToFavoritesClicked: (MovieDetails) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val colors = MaterialTheme.colorScheme
    val coroutineScope = rememberCoroutineScope()
    val uriHandler = LocalUriHandler.current


    LazyColumn(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize()
            .background(colors.background)
    ) {
        item {
            Row {
                Text(
                    text = details.title,
                    style = MaterialTheme.typography.headlineLarge,
                    color = colors.primary,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
        item {
            MovieGenreRow(details.genres)
        }
        item { SectionDivider() }
        item { MovieOverviewSection(details.overview) }
        item { SectionDivider() }
        item { MovieStatsSection(details) }
        item { SectionDivider() }
        item { ProductionCompaniesRow(details.productionCompanies) }
        item { SectionDivider() }
        item {
            ButtonRow(
                movieDetails = details,
                snackbarHostState = snackbarHostState,
                uriHandler = uriHandler,
                coroutineScope = coroutineScope,
                addToFavoritesClicked = addToFavoritesClicked
            )
        }
    }
}

@Composable
fun MovieGenreRow(genres: List<Genre>) {
    LazyRow(modifier = Modifier.fillMaxWidth().padding(start = 16.dp, top = 4.dp)) {
        items(genres) {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .wrapContentSize()
                    .clip(CircleShape)
                    .padding(horizontal = 4.dp)
                    .background(color = MaterialTheme.colorScheme.surface)
            ) {
                Text(
                    it.name ?: "",
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun CollapsibleImage(imageUrl: String?) {
    val initial = Color(0xFF1B1B1B)
    var backgroundColor by remember { mutableStateOf(initial) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
            .background(backgroundColor)
            .clip(RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun MovieStatsSection(details: MovieDetails) {
    val colors = MaterialTheme.colorScheme
    Column(
        Modifier
            .padding(horizontal = 16.dp)
            .background(colors.surface, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "Details",
            style = MaterialTheme.typography.titleLarge,
            color = colors.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        DetailText("Release Date", details.releaseDate)
        DetailText("Status", details.status)
        DetailText("Revenue", details.revenue.toString())
        DetailText("Vote Average", "${details.voteAverage} (${details.voteCount} votes)")
        DetailText("Languages", details.spokenLanguage.joinToString())
    }
}

@Composable
fun DetailText(label: String, value: String) {
    val colors = MaterialTheme.colorScheme
    Row(modifier = Modifier.padding(vertical = 2.dp)) {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodyMedium,
            color = colors.primary.copy(alpha = 0.82f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = colors.onSurface
        )
    }
}

@Composable
fun ProductionCompaniesRow(productionCompanies: List<ProductionCompany>) {
    val colors = MaterialTheme.colorScheme
    if (productionCompanies.isEmpty()) return

    Column(
        Modifier
            .padding(horizontal = 16.dp)
            .background(colors.surface, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Text(
            text = "Production Companies",
            style = MaterialTheme.typography.titleMedium,
            color = colors.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyRow(horizontalArrangement = Arrangement.Center) {
            items(productionCompanies) { company ->
                if (!company.logoPath.isNullOrEmpty())
                    Card(
                        modifier = Modifier
                            .size(80.dp)
                            .padding(end = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = colors.surfaceVariant),
                        elevation = CardDefaults.cardElevation(4.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        AsyncImage(
                            model = company.logoPath,
                            contentDescription = company.name,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
            }
        }
    }
}

@Composable
fun MovieOverviewSection(overview: String) {
    val colors = MaterialTheme.colorScheme
    Column(
        Modifier
            .padding(horizontal = 16.dp)
            .background(colors.surface, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Text(
            text = "Overview",
            style = MaterialTheme.typography.titleMedium,
            color = colors.primary,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = overview,
            style = MaterialTheme.typography.bodyMedium,
            color = colors.onSurface
        )
    }
}

@Composable
fun SectionDivider() {
    Divider(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .height(1.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    )
}

@Composable
fun ButtonRow(
    movieDetails: MovieDetails,
    snackbarHostState: SnackbarHostState,
    uriHandler: UriHandler,
    coroutineScope: CoroutineScope,
    addToFavoritesClicked: (MovieDetails) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = {
                if (movieDetails.homepage.isNotEmpty()) {
                    uriHandler.openUri(movieDetails.homepage)
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .weight(1f)
        ) {
            Text("Watch Now", color = MaterialTheme.colorScheme.onPrimary)
        }
        OutlinedButton(
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            onClick = {
                addToFavoritesClicked(movieDetails)
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Movie has been added to your favorites")
                }
            },
            modifier = Modifier.weight(1f)
        ) {
            Text("Add to Favorites")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SlidingBottomSheetDemo() {
    var showSheet by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false // allows half-expanded state
    )

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showSheet = true }) {
                Icon(Icons.Default.Add, contentDescription = "Open Sheet")
            }
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text("Main Content")
        }

        if (showSheet) {
            ModalBottomSheet(
                onDismissRequest = { showSheet = false },
                sheetState = sheetState
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        "This is a sliding bottom sheet!",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(Modifier.height(12.dp))
                    Button(onClick = { showSheet = false }) {
                        Text("Close")
                    }
                }
            }
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
    MovieDetailsScreen(
        uiState = MovieDetailsUiState(movieDetails = dummyMovie),
        onBackClick = {},
        paddingValues = PaddingValues(),
        addToFavoritesClicked = {}
    )
}
