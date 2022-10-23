package com.arcticoss.nextplayer.feature.media.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.arcticoss.model.MediaFolder

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FolderItem(
    mediaFolder: MediaFolder,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    Surface(
        modifier = Modifier
            .combinedClickable(
                onClick = { onClick() },
                onLongClick = { haptic.performHapticFeedback(HapticFeedbackType.LongPress) }
            )
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.Folder,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier
                    .sizeIn(maxWidth = 250.dp)
                    .fillMaxWidth(0.45f)
                    .aspectRatio(1f)
            )
            Column(
                modifier = Modifier
                    .padding(start = 12.dp, end = 12.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = mediaFolder.name,
                    maxLines = 2,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val vidText = if (mediaFolder.mediaItems.size == 1) "video" else "videos"
                    FieldChip(text = "${mediaFolder.mediaItems.size} $vidText")
                }
            }
        }
    }
}