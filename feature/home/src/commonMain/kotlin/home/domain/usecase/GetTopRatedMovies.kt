package home.domain.usecase

import com.gameZone.repository.MoviesRepository

class GetTopRatedMovies (private val movieRepository: MoviesRepository){
    suspend operator fun invoke() = movieRepository.getTopRatedMovies()
}