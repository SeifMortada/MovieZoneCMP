package com.gamezone.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.gameZone.models.Movie
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MovieCard(
    movie: Movie,
    onClick: (Int) -> Unit = {},
    addFavourite: (Movie) -> Unit = {},
    isFavourite: Boolean = false
) {
    Column(
        modifier = Modifier
            .width(140.dp)
            .padding(8.dp)
            .clickable { onClick(movie.id) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.height(200.dp)) { // Increased height for the image and icon
            LoadImage(movie.posterPath, modifier = Modifier.fillMaxSize())
            if (isFavourite) {
                var isFilled by rememberSaveable { mutableStateOf(false) } // State to track if the icon is filled
                Icon(
                    imageVector = if (isFilled) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favorite Icon",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .align(Alignment.TopEnd) // Align icon to the top right of the Box
                        .padding(8.dp) // Add some padding around the icon
                        .size(24.dp) // Set the size of the icon
                        .clickable {
                            addFavourite(movie)
                            isFilled = !isFilled // Toggle the filled state on click
                        }
                )
            }
        }


        Spacer(Modifier.height(8.dp))
        Text(
            text = movie.title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary,
            maxLines = 2
        )
        Spacer(Modifier.height(4.dp))
        MediumWhiteText(movie.overview, maxLines = 3)
        Spacer(Modifier.height(12.dp))
        SmallMagentaText(movie.releaseDate)
    }
}

@Composable
fun SmallMagentaText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun MediumWhiteText(text: String, maxLines: Int = Int.MAX_VALUE) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onBackground,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis
    )
}


@Preview
@Composable
fun MovieCardPreview() {
    MovieCard(
        movie = Movie(
            id = 1,
            title = "Movie Title",
            overview = "This is a brief overview of the movie. It describes the plot and main characters.",
            posterPath = "", // Add a placeholder image URL or leave empty
            releaseDate = "2023-01-01",
            genres = listOf(12)
        ),
        isFavourite = true
    )
}


