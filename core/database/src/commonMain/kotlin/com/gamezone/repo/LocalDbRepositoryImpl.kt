package com.gamezone.repo


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.gameZone.models.Movie
import com.gameZone.models.TvShow
import com.gameZone.repository.LocalDbRepository
import com.gamezone.db.MovieZoneDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

class LocalDbRepositoryImpl(
    private val database: MovieZoneDB
) : LocalDbRepository {
    private val movieQuires = database.moviesQueries
    private val tvShowsQueries = database.tvShowsQueries
    // Movies
    override suspend fun insertMovie(movie: Movie) {
        movieQuires.insertMovie(
            id = movie.id.toLong(),
            title = movie.title,
            overview = movie.overview,
            poster_path = movie.posterPath,
            release_date = movie.releaseDate,
            genres = movie.genres.joinToString(",")
        )
    }

    override suspend fun deleteMovieById(id: Int) {
        movieQuires.deleteMovieById(id.toLong())
    }

    override fun getAllMovies(): Flow<List<Movie>> {
        return movieQuires.selectAllMovies()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { list ->
                list.map {
                    Movie(
                        id = it.id.toInt(),
                        title = it.title,
                        overview = it.overview,
                        posterPath = it.poster_path,
                        releaseDate = it.release_date,
                        genres = it.genres.split(",").map(String::toInt)
                    )
                }
            }
    }

    override suspend fun getMovieById(id: Int): Movie? {
        return movieQuires.selectMovieById(id.toLong())
            .executeAsOneOrNull()
            ?.let {
                Movie(
                    id = it.id.toInt(),
                    title = it.title,
                    overview = it.overview,
                    posterPath = it.poster_path,
                    releaseDate = it.release_date,
                    genres = it.genres.split(",").map(String::toInt)
                )
            }
    }

    // TV Shows
    override suspend fun insertTvShow(tvShow: TvShow) {
        tvShowsQueries.insertTvShow(
            id = tvShow.id.toLong(),
            name = tvShow.name,
            overview = tvShow.overview,
            poster_path = tvShow.posterPath,
            original_language = tvShow.originalLanguage,
            first_air_date = tvShow.firstAirDate,
            genre_ids = tvShow.genreIds.joinToString(",")
        )
    }

    override suspend fun deleteTvShowById(id: Int) {
        tvShowsQueries.deleteTvShowById(id.toLong())
    }

    override fun getAllTvShows(): Flow<List<TvShow>> {
        return tvShowsQueries.selectAllTvShows()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { list ->
                list.map {
                    TvShow(
                        id = it.id.toInt(),
                        name = it.name,
                        overview = it.overview,
                        posterPath = it.poster_path,
                        originalLanguage = it.original_language,
                        firstAirDate = it.first_air_date,
                        genreIds = it.genre_ids.split(",").map(String::toInt)
                    )
                }
            }
    }

    override suspend fun getTvShowById(id: Int): TvShow? {
        return tvShowsQueries.selectTvShowById(id.toLong())
            .executeAsOneOrNull()
            ?.let {
                TvShow(
                    id = it.id.toInt(),
                    name = it.name,
                    overview = it.overview,
                    posterPath = it.poster_path,
                    originalLanguage = it.original_language,
                    firstAirDate = it.first_air_date,
                    genreIds = it.genre_ids.split(",").map(String::toInt)
                )
            }
    }
}
