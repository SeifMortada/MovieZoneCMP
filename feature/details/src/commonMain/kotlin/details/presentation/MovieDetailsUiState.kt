package details.presentation

import com.gameZone.models.MovieDetails

data class MovieDetailsUiState(
    val isLoading: Boolean = true,
    val movieDetails: MovieDetails? = null,
    val isFavorite: Boolean = false,
    val error: String? = null
)

