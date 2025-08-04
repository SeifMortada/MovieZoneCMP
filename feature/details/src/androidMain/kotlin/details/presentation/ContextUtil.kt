package details.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun androidContextOrNull(): Any? = LocalContext.current
