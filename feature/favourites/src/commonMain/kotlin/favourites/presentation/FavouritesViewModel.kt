package favourites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gameZone.models.Movie
import com.gameZone.usecase.RetrieveFavouriteMovies
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn


class FavouritesViewModel(
    private val retrieveFavouriteMovies: RetrieveFavouriteMovies
) : ViewModel() {

    val uiState: StateFlow<List<Movie>> = retrieveFavouriteMovies()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())


}
