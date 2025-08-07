package search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gameZone.models.ApiOperation
import com.gameZone.models.Movie
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import search.domain.SearchUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SearchUiState(
    val isLoading: Boolean = false,
    val query: String = "",
    val movies: List<Movie> = emptyList(),
    val recentSearches: List<String> = listOf("Test1", "test 2 ", "test 3"),
    val error: String? = null
)

class SearchViewModel(
    private val searchMoviesUseCase: SearchUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState

    fun onQueryChange(newQuery: String) {
        _uiState.update { it.copy(query = newQuery, error = null) }
        search()
    }

    fun onClearQuery() {
        _uiState.update { it.copy(query = "", movies = emptyList(), error = null) }
    }

    var searchJob: Job? = null
    fun search() {
        searchJob = null
        searchJob = viewModelScope.launch {
            delay(500)
            val query = _uiState.value.query.trim()
            if (query.isBlank()) return@launch
            _uiState.update { it.copy(isLoading = true, error = null) }

            when (val result = searchMoviesUseCase(query)) {
                is ApiOperation.Failure -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = it.error ?: "Error occurred"
                        )
                    }
                }

                is ApiOperation.Success -> handleSearchResult(result.data, query)

            }
        }
    }

    private fun handleSearchResult(movies: List<Movie>, query: String) {
        if (movies.isNotEmpty())
            _uiState.update {
                it.copy(
                    movies = movies,
                    isLoading = false,
                    error = null,
                    recentSearches = updateRecentSearches(it.recentSearches, query)
                )
            }
        else _uiState.update {
            it.copy(
                isLoading = false,
                error = "List is empty",
                movies = emptyList()
            )
        }
    }

    fun onRecentSearchSelected(recent: String) {
        _uiState.update { it.copy(query = recent, error = null) }
        search()
    }

    private fun updateRecentSearches(
        current: List<String>,
        newQuery: String,
        max: Int = 3
    ): List<String> {
        val filtered = current.filterNot { it.equals(newQuery, ignoreCase = true) }
        return (listOf(newQuery) + filtered).take(max)
    }
}
