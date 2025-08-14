package home.domain.usecase

import com.gameZone.models.Movie
import com.gameZone.models.TvShow
import com.gameZone.repository.LocalDbRepository

class SaveTvShowUseCase(private val localDbRepository: LocalDbRepository) {

    suspend operator fun invoke(tvShow: TvShow) {
        localDbRepository.insertTvShow(tvShow)
    }
}