package details.domain

import com.gameZone.models.ApiOperation
import com.gameZone.models.MovieDetails
import com.gameZone.repository.MoviesRepository

class GetMovieDetailsUseCase(private val repository: MoviesRepository) {
    suspend operator fun invoke(movieId: Int): ApiOperation<MovieDetails> {
        return repository.getMovieDetails(movieId)
    }
}