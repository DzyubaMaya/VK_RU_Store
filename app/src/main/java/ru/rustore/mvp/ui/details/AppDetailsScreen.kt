package ru.rustore.mvp.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import kotlinx.coroutines.flow.Flow
import ru.rustore.mvp.ui.common.verticalScrollbar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.rustore.mvp.R
import ru.rustore.mvp.data.model.AppItem
import ru.rustore.mvp.data.model.ImageRef
import ru.rustore.mvp.data.model.Screenshot
import ru.rustore.mvp.data.repository.InstallState
import ru.rustore.mvp.ui.common.AppIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDetailsScreen(
    uiState: AppDetailsUiState,
    events: Flow<AppDetailsEvent>,
    onBack: () -> Unit,
    onInstall: () -> Unit,
    onUninstall: () -> Unit,
    onOpenInstalledApp: () -> Unit,
    onOpenInstalledAppById: (String) -> Unit,
    onOpenScreenshots: (appId: String, startIndex: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val app = uiState.app
    LaunchedEffect(events) {
        events.collect { event ->
            when (event) {
                is AppDetailsEvent.OpenInstalledApp -> onOpenInstalledAppById(event.appId)
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = app?.title.orEmpty(),
                        maxLines = 1,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.details_back),
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            )
        },
    ) { padding ->
        if (app == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = stringResource(R.string.details_not_found))
            }
        } else {
            AppDetailsContent(
                app = app,
                installState = uiState.installState,
                padding = padding,
                onInstall = onInstall,
                onUninstall = onUninstall,
                onOpenInstalledApp = onOpenInstalledApp,
                onScreenshotClick = { index -> onOpenScreenshots(app.id, index) },
            )
        }
    }
}

@Composable
private fun AppDetailsContent(
    app: AppItem,
    installState: InstallState,
    padding: PaddingValues,
    onInstall: () -> Unit,
    onUninstall: () -> Unit,
    onOpenInstalledApp: () -> Unit,
    onScreenshotClick: (Int) -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .verticalScrollbar(scrollState)
            .verticalScroll(scrollState),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AppIcon(app = app, size = 88.dp)
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = app.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = app.developer,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = app.category.displayName,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(app.category.brandColor),
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            StatBlock(
                value = stringResource(R.string.details_rating, app.rating),
                label = stringResource(R.string.details_downloads, app.downloads),
                leading = {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFB300),
                        modifier = Modifier.size(18.dp),
                    )
                },
                modifier = Modifier.weight(1f),
            )
            StatBlock(
                value = app.ageRating.label,
                label = stringResource(R.string.details_info_age),
                modifier = Modifier.weight(1f),
            )
            StatBlock(
                value = stringResource(R.string.details_size_mb, app.sizeMb),
                label = stringResource(R.string.details_info_size),
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        InstallActions(
            state = installState,
            onInstall = onInstall,
            onUninstall = onUninstall,
            onOpen = onOpenInstalledApp,
        )

        SectionTitle(text = stringResource(R.string.details_section_about))
        Text(
            text = app.fullDescription,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        SectionTitle(text = stringResource(R.string.details_section_screenshots))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
        ) {
            itemsIndexed(app.screenshots) { index, screenshot ->
                ScreenshotCard(
                    screenshot = screenshot,
                    index = index,
                    onClick = { onScreenshotClick(index) },
                )
            }
        }

        SectionTitle(text = stringResource(R.string.details_section_info))
        InfoRow(
            label = stringResource(R.string.details_info_version),
            value = app.version,
        )
        InfoRow(
            label = stringResource(R.string.details_info_updated),
            value = app.updatedAt,
        )
        InfoRow(
            label = stringResource(R.string.details_info_category),
            value = app.category.displayName,
        )
        InfoRow(
            label = stringResource(R.string.details_info_age),
            value = app.ageRating.label,
        )
        InfoRow(
            label = stringResource(R.string.details_info_size),
            value = stringResource(R.string.details_size_mb, app.sizeMb),
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun InstallActions(
    state: InstallState,
    onInstall: () -> Unit,
    onUninstall: () -> Unit,
    onOpen: () -> Unit,
) {
    when (state) {
        is InstallState.NotInstalled -> {
            Button(
                onClick = onInstall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
            ) {
                Text(
                    text = stringResource(R.string.details_install),
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }

        is InstallState.Installing -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            ) {
                Button(
                    onClick = {},
                    enabled = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(
                            R.string.details_installing,
                            (state.progress * 100).toInt(),
                        ),
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { state.progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(50)),
                )
            }
        }

        is InstallState.Installed -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Button(
                    onClick = onOpen,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.details_open),
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                OutlinedButton(
                    onClick = onUninstall,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.details_uninstall),
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp),
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = stringResource(R.string.details_installed_badge),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }
    }
}

@Composable
private fun StatBlock(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    leading: (@Composable () -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (leading != null) {
                leading()
                Spacer(modifier = Modifier.width(4.dp))
            }
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
        )
    }
}

@Composable
private fun SectionTitle(text: String) {
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
    )
}

@Composable
private fun ScreenshotCard(
    screenshot: Screenshot,
    index: Int,
    onClick: () -> Unit,
) {
    val shape = RoundedCornerShape(16.dp)
    Column(
        modifier = Modifier
            .width(180.dp)
            .clickable(onClick = onClick),
    ) {
        Box(
            modifier = Modifier
                .size(width = 180.dp, height = 320.dp)
                .clip(shape),
        ) {
            val resId = (screenshot.image as? ImageRef.Resource)?.resId
                ?: R.drawable.ic_screenshot_blue
            Image(
                painter = painterResource(resId),
                contentDescription = stringResource(
                    R.string.screenshot_content_description,
                    index + 1,
                ),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = screenshot.caption,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
        )
        Text(
            text = "#${index + 1}",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
        )
    }
    HorizontalDivider(
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.padding(horizontal = 16.dp),
    )
}
