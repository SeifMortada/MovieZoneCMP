package favourites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gameZone.models.Movie
import com.gameZone.usecase.RemoveMovieFromFavourites
import com.gameZone.usecase.RetrieveFavouriteMovies
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class FavouritesViewModel(
    private val retrieveFavouriteMovies: RetrieveFavouriteMovies,
    private val removeMovieFromFavourites: RemoveMovieFromFavourites
) : ViewModel() {

    val uiState: StateFlow<List<Movie>> = retrieveFavouriteMovies()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())


    fun removeFromFavourites(movieId: Int) {
        viewModelScope.launch {
            removeMovieFromFavourites(movieId)
        }
    }
}
