package ru.rustore.mvp.ui.common

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import ru.rustore.mvp.data.model.ImageRef

@Composable
fun AppImage(
    imageRef: ImageRef,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
) {
    when (imageRef) {
        is ImageRef.Resource -> {
            Image(
                painter = painterResource(imageRef.resId),
                contentDescription = contentDescription,
                modifier = modifier.size(size),
                contentScale = ContentScale.Crop,
            )
        }
        is ImageRef.Url -> {
            // Coil подключается в опции (ADR-005); для MVP URL не используются.
            Image(
                painter = painterResource(ru.rustore.mvp.R.drawable.ic_app_placeholder),
                contentDescription = contentDescription,
                modifier = modifier.size(size),
                contentScale = ContentScale.Crop,
            )
        }
    }
}
