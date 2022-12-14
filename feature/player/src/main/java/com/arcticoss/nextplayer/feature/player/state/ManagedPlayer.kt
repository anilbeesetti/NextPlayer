package com.arcticoss.nextplayer.feature.player.state

import android.content.Context
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player

@Composable
fun rememberManagedPlayer(
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    factory: (Context) -> Player
): State<Player?> {
    val currentContext = LocalContext.current.applicationContext
    val playerManager = remember { PlayerManager { factory(currentContext) } }
    DisposableEffect(lifecycle) {
        val observer = LifecycleEventObserver { _, event ->
            when {
                (event == Lifecycle.Event.ON_START && Build.VERSION.SDK_INT > 23)
                        || (event == Lifecycle.Event.ON_RESUME && Build.VERSION.SDK_INT <= 23) -> {
                    playerManager.initialize()
                }
                (event == Lifecycle.Event.ON_PAUSE && Build.VERSION.SDK_INT <= 23)
                        || (event == Lifecycle.Event.ON_STOP && Build.VERSION.SDK_INT > 23) -> {
                    playerManager.release()
                }
            }
        }
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
    return playerManager.player
}


internal class PlayerManager(
    private val factory: () -> Player
) : RememberObserver {
    var player = mutableStateOf<Player?>(null)

    internal fun initialize() {
        player.value = factory()
    }

    internal fun release() {
        player.value?.let { player ->
            player.release()
        }
        player.value = null
    }

    override fun onAbandoned() {
        release()
    }

    override fun onForgotten() {
        release()
    }

    override fun onRemembered() {}
}

@Composable
fun rememberManagedExoPlayer(): State<Player?> = rememberManagedPlayer { context ->
    val renderFactory = DefaultRenderersFactory(context)
        .setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON)
    ExoPlayer.Builder(context)
        .setRenderersFactory(renderFactory)
        .build()
}