package details.presentation

import com.gameZone.models.MovieDetails

sealed interface MovieDetailsAction {
    data class LoadMovie(val movieId: Int) : MovieDetailsAction
    data class AddToFavorites(val movieDetails: MovieDetails) : MovieDetailsAction
    data object NavigateBack : MovieDetailsAction
}