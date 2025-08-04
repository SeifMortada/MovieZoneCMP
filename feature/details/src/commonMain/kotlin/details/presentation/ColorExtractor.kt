package details.presentation

import androidx.compose.ui.graphics.Color

expect suspend fun extractDominantColor(imageUrl: String?, context: Any?): Color?
