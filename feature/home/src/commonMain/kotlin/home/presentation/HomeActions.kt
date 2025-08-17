package home.presentation

import com.gameZone.models.Movie
import com.gameZone.models.TvShow

sealed interface HomeActions {
    object LoadData : HomeActions
    data class AddTvShowToFavourites(val tvShow: TvShow) : HomeActions
    data class AddMovieToFavourites(val movie: Movie) : HomeActions

    data class RemoveMovieFromFavourites(val movie: Movie) : HomeActions
    data class OnMovieClick(val movieId: Int) : HomeActions
    data object Retry : HomeActions
}