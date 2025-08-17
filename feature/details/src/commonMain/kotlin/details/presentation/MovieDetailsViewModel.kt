package details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gameZone.models.Movie
import com.gameZone.models.MovieDetails
import com.gameZone.usecase.AddMovieToFavorites
import com.gameZone.usecase.RetrieveFavouriteMovies
import details.domain.GetMovieDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MovieDetailsViewModel(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val addMovieToFavorites: AddMovieToFavorites,
    private val retrieveFavouriteMovies: RetrieveFavouriteMovies
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailsUiState())
    val uiState: StateFlow<MovieDetailsUiState> = _uiState

    fun onAction(action: MovieDetailsAction) {
        when (action) {
            is MovieDetailsAction.AddToFavorites -> addToFavorites(action.movieDetails)
            is MovieDetailsAction.LoadMovie -> load(action.movieId)
            is MovieDetailsAction.NavigateBack -> Unit
        }
    }

    private fun load(movieId: Int) = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, error = null) }
        try {
            val result = getMovieDetailsUseCase(movieId)
            result.onSuccess { details ->
                launch {
                    retrieveFavouriteMovies().collectLatest { favorites ->
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                movieDetails = details,
                                isFavorite = favorites.any { it.id == movieId },
                                error = null
                            )
                        }
                    }
                }
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false, error = error.message) }
            }
        } catch (e: Exception) {
            _uiState.update { it.copy(isLoading = false, error = e.message) }
        }
    }

    private fun addToFavorites(movieDetails: MovieDetails) {
        viewModelScope.launch {
            addMovieToFavorites(mapToMovie(movieDetails))
            _uiState.update { it.copy(isFavorite = true) }
        }
    }

    private fun mapToMovie(movieDetails: MovieDetails): Movie {
        return Movie(
            id = movieDetails.id,
            title = movieDetails.title,
            overview = movieDetails.overview,
            posterPath = movieDetails.imageUrl,
            releaseDate = movieDetails.releaseDate,
            genres = movieDetails.genres.mapNotNull { it.id }
        )
    }
}
