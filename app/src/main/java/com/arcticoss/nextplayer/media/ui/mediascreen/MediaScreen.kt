package com.arcticoss.nextplayer.media.ui.mediascreen

import android.os.Build
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.arcticoss.nextplayer.R
import com.arcticoss.nextplayer.media.ui.mediascreen.composables.MediaLargeTopAppBar
import com.arcticoss.nextplayer.media.ui.mediascreen.composables.ShowContentForMarshMellow
import com.arcticoss.nextplayer.media.ui.mediascreen.composables.ShowContentForRedVelvet
import com.arcticoss.nextplayer.media.ui.mediascreen.composables.ShowVideoFiles
import com.arcticoss.nextplayer.player.ui.playerscreen.composables.AddLifecycleEventObserver


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaScreen(
    viewModel: MediaScreenViewModel = hiltViewModel(),
) {
    val scrollBehaviour = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val lifecycleOwner = LocalLifecycleOwner.current

    AddLifecycleEventObserver(lifecycleOwner = lifecycleOwner,) { event ->
        if (event == Lifecycle.Event.ON_START) {
            viewModel.syncMedia()
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehaviour.nestedScrollConnection),
        topBar = {
            MediaLargeTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.next_player),
                        modifier = Modifier.padding()
                    )
                },
                scrollBehavior = scrollBehaviour,
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = stringResource(id = R.string.settings))
                    }
                }
            )
        }
    ) { innerPadding ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            ShowContentForRedVelvet(contentPadding = innerPadding)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ShowContentForMarshMellow(contentPadding = innerPadding)
        } else {
            ShowVideoFiles(contentPadding = innerPadding)
        }
    }
}


