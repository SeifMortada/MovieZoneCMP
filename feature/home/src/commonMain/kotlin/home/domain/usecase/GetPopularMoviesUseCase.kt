package home.domain.usecase

import com.gameZone.repository.MoviesRepository

class GetPopularMoviesUseCase(private val repository: MoviesRepository) {
    suspend operator fun invoke(page: Int?=null) = repository.getPopularMovies()
}