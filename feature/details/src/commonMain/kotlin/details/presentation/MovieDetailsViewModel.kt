package details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gameZone.models.MovieDetails
import details.domain.GetMovieDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MovieDetailsUiState(
    val isLoading: Boolean = true,
    val movieDetails: MovieDetails? = null,
    val error: String? = null
)

class MovieDetailsViewModel(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailsUiState())
    val uiState: StateFlow<MovieDetailsUiState> = _uiState

    fun load(movieId: Int) = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        try {
            val result = getMovieDetailsUseCase(movieId)
            result.onSuccess { details ->
                _uiState.update { it.copy(movieDetails = details) }
            }.onFailure { error ->
                _uiState.value = MovieDetailsUiState(error = error.message)
            }
        } catch (e: Exception) {
            _uiState.value = MovieDetailsUiState(error = e.message)
        }
        _uiState.update { it.copy(isLoading = false) }
    }
}
