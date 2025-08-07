package com.gamezone.ui


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter

@Composable
fun CoreAsyncImage(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    loadingContent: @Composable (() -> Unit)? = null,
    errorContent: @Composable (() -> Unit)? = null,
    successContent: @Composable ((AsyncImagePainter.State.Success) -> Unit)? = null,
) {
    val painter = rememberAsyncImagePainter(imageUrl)
    val state = painter.state
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        when (state) {
            is AsyncImagePainter.State.Loading -> {
                loadingContent?.invoke() ?: CircularProgressIndicator()
            }
            is AsyncImagePainter.State.Error -> {
                errorContent?.invoke() ?: Text("Image failed to load", color = MaterialTheme.colorScheme.error)
            }
            is AsyncImagePainter.State.Success -> {
                if (successContent != null) {
                    successContent(state)
                } else {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = contentDescription,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            else -> {}
        }
    }
}
