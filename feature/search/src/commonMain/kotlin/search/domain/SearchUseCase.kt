package search.domain

import com.gameZone.models.ApiOperation
import com.gameZone.models.Movie
import com.gameZone.repository.MoviesRepository

class SearchUseCase(private val repository: MoviesRepository) {
    suspend operator fun invoke(query: String): ApiOperation<List<Movie>> {
        return repository.searchMovies(query)
    }
}