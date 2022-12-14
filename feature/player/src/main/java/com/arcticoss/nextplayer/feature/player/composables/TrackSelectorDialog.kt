package com.arcticoss.nextplayer.feature.player.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.arcticoss.nextplayer.core.ui.CenterDialog


/***
 * Dialog to select Track
 * @param onDismiss The callback to handle dismiss dialog
 * @param tracks The list of [Track]
 * @param onTrackClick Then callback to handle on track item click
 */
@Composable
fun TrackSelectorDialog(
    onDismiss: () -> Unit,
    title: @Composable () -> Unit,
    tracks: List<Track>,
    onTrackClick: (Track) -> Unit
) {
    CenterDialog(
        onDismiss = onDismiss,
        title = title,
        content = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                Column(Modifier.selectableGroup()) {
                    tracks.forEach { track ->
                        TrackChooser(
                            text = track.name,
                            selected = track.isSelected,
                            onClick = { onTrackClick(track) }
                        )
                    }
                }
            }
        }
    )
}


data class Track(
    val id: String?,
    val name: String,
    val isSelected: Boolean,
    val isSupported: Boolean
)


/***
 *  Single Track choice item
 *  @param text The title of the item
 *  @param selected [Boolean] is it selected or not
 *  @param onClick The callback to handle on item click
 */
@Composable
fun TrackChooser(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick,
            )
            .padding(horizontal = 24.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = null
        )
        Spacer(Modifier.width(8.dp))
        Text(text)
    }
}