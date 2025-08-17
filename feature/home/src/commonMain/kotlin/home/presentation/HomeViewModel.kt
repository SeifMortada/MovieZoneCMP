package home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gameZone.models.Movie
import com.gameZone.models.TvShow
import com.gameZone.usecase.AddMovieToFavorites
import home.domain.usecase.GetPopularMoviesUseCase
import home.domain.usecase.GetPopularTvShowsUseCase
import home.domain.usecase.GetTopRatedMovies
import com.gameZone.usecase.AddTvShowToFavourites
import com.gameZone.usecase.RemoveMovieFromFavourites
import com.gameZone.usecase.RetrieveFavouriteMovies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMovies,
    private val getPopularTvShowsUseCase: GetPopularTvShowsUseCase,
    private val addTvShowToFavourites: AddTvShowToFavourites,
    private val addMovieToFavorites: AddMovieToFavorites,
    private val retrieveFavouriteMovies: RetrieveFavouriteMovies,
    private val removeMovieFromFavouritesUseCase: RemoveMovieFromFavourites
) : ViewModel() {

    private val favouriteMovies: StateFlow<List<Movie>> = retrieveFavouriteMovies()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = combine(favouriteMovies, _uiState) { favouriteMovies, uiState ->
        uiState.copy(
            popularMovies = uiState.popularMovies.map { movie ->
                movie.copy(isFavorite = favouriteMovies.any { it.id == movie.id })
            }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState())

    init {
        onAction(HomeActions.LoadData)
    }

    fun loadData() {
        resetUiState()
        getPopularMovies()
        getTopRatedMovies()
        getPopularTvShows()
    }

    fun onAction(action: HomeActions) {
        when (action) {
            HomeActions.LoadData -> loadData()
            is HomeActions.AddTvShowToFavourites -> saveTvShow(action.tvShow)
            is HomeActions.AddMovieToFavourites -> addMovieToFavourites(action.movie)
            is HomeActions.OnMovieClick -> Unit
            HomeActions.Retry -> loadData()
            is HomeActions.RemoveMovieFromFavourites -> removeMovieFromFavourites(action.movie)
        }
    }

    private fun resetUiState() {
        _uiState.value = HomeUiState()
    }

    private fun getPopularMovies() = viewModelScope.launch {
        setLoading(true)
        val popularMovies = getPopularMoviesUseCase()
        popularMovies.onSuccess { movies ->
            launch(Dispatchers.Default) {
                retrieveFavouriteMovies().collect { favouriteMovies ->
                    movies.forEach { movie ->
                        movie.isFavorite = favouriteMovies.any { it.id == movie.id }
                    }
                    _uiState.update {
                        it.copy(popularMovies = movies)
                    }
                }
            }
        }.onFailure { error ->
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
        }.onFailure { error ->
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
        }.onFailure { error ->
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

    private fun saveTvShow(tvShow: TvShow) = viewModelScope.launch {
        addTvShowToFavourites(tvShow)
    }

    private fun addMovieToFavourites(movie: Movie) = viewModelScope.launch {
        addMovieToFavorites(movie)
    }

    private fun removeMovieFromFavourites(movie: Movie) = viewModelScope.launch {
        removeMovieFromFavouritesUseCase(movie.id)
    }
}