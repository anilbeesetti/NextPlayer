package com.arcticoss.nextplayer.feature.player.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun PlayerUIMainControls(
    playPauseIcon: ImageVector,
    onPlayPauseClick: () -> Unit,
    onSkipNextClick: () -> Unit,
    onSkipPreviousClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconRippleButton(onClick = onSkipPreviousClick) {
            Icon(
                imageVector = Icons.Rounded.SkipPrevious,
                contentDescription = "",
                modifier = Modifier.size(32.dp)
            )
        }
        IconRippleButton(onClick = { onPlayPauseClick() }) {
            Icon(
                imageVector = playPauseIcon,
                contentDescription = "",
                modifier = Modifier.size(48.dp)
            )
        }
        IconRippleButton(onClick = onSkipNextClick) {
            Icon(
                imageVector = Icons.Rounded.SkipNext,
                contentDescription = "",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun IconRippleButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .clickable { onClick() }
            .padding(20.dp),
        content = { content() }
    )
}