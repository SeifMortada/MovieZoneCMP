package home.presentation

import com.gameZone.models.Movie
import com.gameZone.models.TvShow

data class HomeUiState(
    val popularMovies: List<Movie> = emptyList(),
    val topRatedMovies: List<Movie> = emptyList(),
    val popularTvShows: List<TvShow> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
