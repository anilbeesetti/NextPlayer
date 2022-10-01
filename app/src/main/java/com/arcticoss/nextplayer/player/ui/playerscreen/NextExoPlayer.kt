package com.arcticoss.nextplayer.player.ui.playerscreen

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arcticoss.nextplayer.player.ui.playerscreen.composables.AddLifecycleEventObserver
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.io.File
import kotlin.time.Duration.Companion.seconds

private const val TAG = "NextExoPlayer"

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun NextExoPlayer(
    exoPlayer: ExoPlayer,
    mediaPath: String,
    viewModel: NextPlayerViewModel
) {
    val lastPlayedPosition by viewModel.lastPlayedPosition.collectAsStateWithLifecycle()
    val isPlaying by viewModel.isPlaying.collectAsStateWithLifecycle()

    LaunchedEffect(exoPlayer) {
        val mediaItem = MediaItem.fromUri(Uri.fromFile(File(mediaPath)))
        exoPlayer.addMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.seekTo(lastPlayedPosition)
    }

    if (isPlaying) {
        LaunchedEffect(Unit) {
            while (true) {
                viewModel.setCurrentPosition(exoPlayer.currentPosition)
                delay(1.seconds / 30)
            }
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    AddLifecycleEventObserver(
        lifecycleOwner = lifecycleOwner,
        onLifecycleEvent = { event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    exoPlayer.playWhenReady = false
                    viewModel.setLastPlayingPosition(exoPlayer.currentPosition)
                }
                Lifecycle.Event.ON_RESUME -> {
                    exoPlayer.playWhenReady = isPlaying
                }
                Lifecycle.Event.ON_START -> {}
                else -> {}
            }
        }
    )


    lateinit var playerView: StyledPlayerView
    lateinit var playbackStateListener: Player.Listener
    DisposableEffect(
        AndroidView(
            factory = { context ->
                playerView = StyledPlayerView(context).apply {
                    hideController()
                    useController = false
                    player = exoPlayer
                }
                playerView
            },
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        )
    ) {
        playbackStateListener = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    Player.STATE_BUFFERING -> {
                        /*TODO*/
                    }
                    Player.STATE_ENDED -> {
                        /*TODO*/
                    }
                    Player.STATE_IDLE -> {
                        /*TODO*/
                    }
                    Player.STATE_READY -> {
                        viewModel.setDuration(exoPlayer.duration)
                    }
                }
            }
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                viewModel.setIsPlaying(isPlaying)
            }
        }
        exoPlayer.addListener(playbackStateListener)
        onDispose {
            exoPlayer.removeListener(playbackStateListener)
            exoPlayer.release()
        }
    }
}