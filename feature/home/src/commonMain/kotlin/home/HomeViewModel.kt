package home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gameZone.models.Movie
import com.gameZone.models.TvShow
import com.gameZone.usecase.AddMovieToFavorites
import home.domain.usecase.GetPopularMoviesUseCase
import home.domain.usecase.GetPopularTvShowsUseCase
import home.domain.usecase.GetTopRatedMovies
import com.gameZone.usecase.AddTvShowToFavourites
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val popularMovies: List<Movie> = emptyList(),
    val topRatedMovies: List<Movie> = emptyList(),
    val popularTvShows: List<TvShow> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class HomeViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMovies,
    private val getPopularTvShowsUseCase: GetPopularTvShowsUseCase,
    private val addTvShowToFavourites: AddTvShowToFavourites,
    private val addMovieToFavorites: AddMovieToFavorites
) : ViewModel() {
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getPopularMovies()
        getTopRatedMovies()
        getPopularTvShows()
    }

    private fun getPopularMovies() = viewModelScope.launch {
        setLoading(true)
        val popularMovies = getPopularMoviesUseCase()
        popularMovies.onSuccess { movies ->
            _uiState.update {
                it.copy(popularMovies = movies)
            }
        }
            .onFailure { error ->
                setError("Error getting episodes" + error.message)
            }
        setLoading(false)

    }

    private fun getTopRatedMovies() = viewModelScope.launch {
        setLoading(true)
        val topRatedMovies = getTopRatedMoviesUseCase()
        topRatedMovies.onSuccess { movies ->
            _uiState.update {
                it.copy(topRatedMovies = movies)
            }
        }
            .onFailure { error ->
                setError("Error getting movies" + error.message)
            }
        setLoading(false)
    }

    private fun getPopularTvShows() = viewModelScope.launch {
        setLoading(true)
        val popularTvShows = getPopularTvShowsUseCase()
        popularTvShows.onSuccess { tvShows ->
            _uiState.update {
                it.copy(popularTvShows = tvShows)
            }
        }
            .onFailure { error ->
                setError("Error getting tv shows" + error.message)
            }
        setLoading(false)
    }

    private fun setLoading(isLoading: Boolean) {
        _uiState.update {
            it.copy(isLoading = isLoading)
        }
    }

    private fun setError(error: String?) {
        _uiState.update {
            it.copy(error = error)
        }
    }

    fun saveTvShow(tvShow: TvShow) = viewModelScope.launch {
        addTvShowToFavourites(tvShow)
    }

    fun addMovieToFavourites(movie: Movie) = viewModelScope.launch {
        addMovieToFavorites(movie)
    }
}