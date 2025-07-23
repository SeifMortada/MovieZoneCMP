package home


import com.gameZone.models.Movie


sealed interface HomeUiState {
    data object Loading : HomeUiState
    data object Idle : HomeUiState
    data class Error(val message: String) : HomeUiState
    data class Success(
        val popularMovies: List<Movie> = emptyList()
    ) : HomeUiState
}