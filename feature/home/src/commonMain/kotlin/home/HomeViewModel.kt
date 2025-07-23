package home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gameZone.usecase.GetPopularMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
): ViewModel() {
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Idle)
    val uiState = _uiState.asStateFlow()

    init {
        getPopularMovies()
    }
    private fun getPopularMovies() {
        viewModelScope.launch {
            val popularMovies = getPopularMoviesUseCase()
            popularMovies.onSuccess { episodes ->
                _uiState.update { HomeUiState.Success(episodes) }
            }
                .onFailure { error ->
                    _uiState.update { HomeUiState.Error("Error getting episodes ${error.message}") }
                }
        }
    }
}