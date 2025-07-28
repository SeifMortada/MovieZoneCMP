package home.domain.usecase

import com.gameZone.repository.SeriesRepository

class GetPopularTvShowsUseCase(private val repository: SeriesRepository)  {
    suspend operator fun invoke() = repository.getPopularTvShows()
}