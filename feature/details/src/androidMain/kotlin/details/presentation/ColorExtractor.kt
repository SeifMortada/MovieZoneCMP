package details.presentation

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import androidx.compose.ui.graphics.Color
import androidx.palette.graphics.Palette
import coil3.ImageLoader
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.request.allowHardware
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

actual suspend fun extractDominantColor(imageUrl: String?, context: Any?): Color? {
    if (imageUrl == null) return null
    val ctx = context as? Context ?: return null
    return withContext(Dispatchers.IO) {
        val loader = ImageLoader(ctx)
        val request = ImageRequest.Builder(ctx)
            .data(imageUrl)
            .allowHardware(false)
            .build()
        val result = loader.execute(request)
        if (result is SuccessResult) {
            val bitmap = (result.image as? BitmapDrawable)?.bitmap
            bitmap?.let {
                Palette.from(it).generate()?.getDominantColor(0xFF1B1B1B.toInt())
            }?.let { Color(it) }
        } else null
    }
}
