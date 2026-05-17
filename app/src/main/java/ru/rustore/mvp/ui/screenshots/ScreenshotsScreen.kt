package ru.rustore.mvp.ui.screenshots

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.rustore.mvp.R
import ru.rustore.mvp.data.model.AppItem
import ru.rustore.mvp.data.model.ImageRef

@Composable
fun ScreenshotsScreen(
    uiState: ScreenshotsUiState,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val app = uiState.app
    if (app == null) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(R.string.details_not_found),
                color = Color.White,
            )
        }
        return
    }
    Content(
        app = app,
        startIndex = uiState.startIndex,
        onClose = onClose,
        modifier = modifier,
    )
}

@Composable
private fun Content(
    app: AppItem,
    startIndex: Int,
    onClose: () -> Unit,
    modifier: Modifier,
) {
    val pagerState = rememberPagerState(
        initialPage = startIndex.coerceIn(0, (app.screenshots.size - 1).coerceAtLeast(0)),
        pageCount = { app.screenshots.size },
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
        ) { page ->
            val shot = app.screenshots[page]
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .clip(RoundedCornerShape(20.dp)),
                ) {
                    val resId = (shot.image as? ImageRef.Resource)?.resId
                        ?: R.drawable.ic_screenshot_blue
                    Image(
                        painter = painterResource(resId),
                        contentDescription = stringResource(
                            R.string.screenshot_content_description,
                            page + 1,
                        ),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = shot.caption,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(
                        R.string.screenshots_position,
                        page + 1,
                        app.screenshots.size,
                    ),
                    color = Color.White.copy(alpha = 0.7f),
                )
            }
        }

        IconButton(
            onClick = onClose,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .size(44.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.White.copy(alpha = 0.15f),
                contentColor = Color.White,
            ),
        ) {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = stringResource(R.string.details_back),
            )
        }

        PageIndicators(
            count = app.screenshots.size,
            currentPage = pagerState.currentPage,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
        )
    }
}

@Composable
private fun PageIndicators(
    count: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
) {
    androidx.compose.foundation.layout.Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(count) { index ->
            val isActive = index == currentPage
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(if (isActive) 10.dp else 8.dp)
                    .clip(CircleShape)
                    .background(
                        if (isActive) Color.White else Color.White.copy(alpha = 0.4f),
                    ),
            )
        }
    }
}
