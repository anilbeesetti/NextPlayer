package com.arcticoss.nextplayer.feature.player.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Audiotrack
import androidx.compose.material.icons.rounded.Subtitles
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun PlayerUIHeader(
    title: String,
    onBackClick: () -> Unit,
    onAudioIconClick: () -> Unit,
    onSubtitleIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "Back button",
            )
        }
        Text(
            text = title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )
        IconButton(onClick = onSubtitleIconClick) {
            Icon(
                imageVector = Icons.Rounded.Subtitles,
                contentDescription = Icons.Rounded.Subtitles.name
            )
        }
        IconButton(onClick = onAudioIconClick) {
            Icon(
                imageVector = Icons.Rounded.Audiotrack,
                contentDescription = Icons.Rounded.Audiotrack.name
            )
        }
    }
}