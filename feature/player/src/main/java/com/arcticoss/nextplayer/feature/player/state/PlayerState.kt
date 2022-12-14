package com.arcticoss.nextplayer.feature.player.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.DeviceInfo
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Format
import com.google.android.exoplayer2.MediaMetadata
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.PositionInfo
import com.google.android.exoplayer2.Timeline
import com.google.android.exoplayer2.Tracks
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.text.CueGroup
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters
import com.google.android.exoplayer2.video.VideoSize


private const val TAG = "PlayerState"

/**
 * Create a instance of [PlayerState] and register a [listener][Player.Listener] to the [Player] to
 * observe its states.
 *
 * NOTE: Should call [dispose][PlayerState.dispose] to unregister the listener to avoid leaking this
 * instance when it is no longer used.
 */
fun Player.state(): PlayerState {
    return PlayerStateImpl(this as ExoPlayer)
}

/**
 * A state object that can be used to observe the [player]'s states.
 */
interface PlayerState {
    val player: Player

    val firstFrameRendered: Boolean

    val timeline: Timeline

    val mediaItemIndex: Int

    val mediaMetadata: MediaMetadata

    val playlistMetadata: MediaMetadata

    val oldPosition: PositionInfo?

    val videoFormat: Format?

    val audioFormat: Format?

    val audioTracks: List<Tracks.Group>

    val subtitleTracks: List<Tracks.Group>

    val isLoading: Boolean

    val availableCommands: Player.Commands

    val trackSelectionParameters: TrackSelectionParameters

    @get:Player.State
    val playbackState: Int

    val playWhenReady: Boolean

    @get:Player.PlaybackSuppressionReason
    val playbackSuppressionReason: Int

    val isPlaying: Boolean

    @get:Player.RepeatMode
    val repeatMode: Int

    val shuffleModeEnabled: Boolean

    val playerError: PlaybackException?

    val playbackParameters: PlaybackParameters

    val seekBackIncrement: Long

    val seekForwardIncrement: Long

    val maxSeekToPreviousPosition: Long

    val audioAttributes: AudioAttributes

    val volume: Float

    val deviceInfo: DeviceInfo

    val deviceVolume: Int

    val isDeviceMuted: Boolean

    val videoSize: VideoSize

    val cueGroup: CueGroup

    fun dispose()
}

internal class PlayerStateImpl(
    override val player: ExoPlayer
) : PlayerState {

    override var firstFrameRendered: Boolean by mutableStateOf(false)
        private set

    override var timeline: Timeline by mutableStateOf(player.currentTimeline)
        private set

    override var mediaItemIndex: Int by mutableStateOf(player.currentMediaItemIndex)
        private set

    override var mediaMetadata: MediaMetadata by mutableStateOf(player.mediaMetadata)
        private set

    override var playlistMetadata: MediaMetadata by mutableStateOf(player.playlistMetadata)
        private set

    override var oldPosition: PositionInfo? by mutableStateOf(null)
        private set

    override var videoFormat: Format? by mutableStateOf(player.videoFormat)
        private set

    override var audioFormat: Format? by mutableStateOf(player.audioFormat)
        private set

    override var audioTracks: List<Tracks.Group> by mutableStateOf(emptyList())
        private set

    override var subtitleTracks: List<Tracks.Group> by mutableStateOf(emptyList())
        private set

    override var isLoading: Boolean by mutableStateOf(player.isLoading)
        private set

    override var availableCommands: Player.Commands by mutableStateOf(player.availableCommands)
        private set

    override var trackSelectionParameters: TrackSelectionParameters by mutableStateOf(player.trackSelectionParameters)
        private set

    @get:Player.State
    override var playbackState: Int by mutableStateOf(player.playbackState)
        private set

    override var playWhenReady: Boolean by mutableStateOf(player.playWhenReady)
        private set

    @get:Player.PlaybackSuppressionReason
    override var playbackSuppressionReason: Int by mutableStateOf(player.playbackSuppressionReason)
        private set

    override var isPlaying: Boolean by mutableStateOf(player.isPlaying)
        private set

    @get:Player.RepeatMode
    override var repeatMode: Int by mutableStateOf(player.repeatMode)
        private set

    override var shuffleModeEnabled: Boolean by mutableStateOf(player.shuffleModeEnabled)
        private set

    override var playerError: PlaybackException? by mutableStateOf(player.playerError)
        private set

    override var playbackParameters: PlaybackParameters by mutableStateOf(player.playbackParameters)
        private set

    override var seekBackIncrement: Long by mutableStateOf(player.seekBackIncrement)
        private set

    override var seekForwardIncrement: Long by mutableStateOf(player.seekForwardIncrement)
        private set

    override var maxSeekToPreviousPosition: Long by mutableStateOf(player.maxSeekToPreviousPosition)
        private set

    override var audioAttributes: AudioAttributes by mutableStateOf(player.audioAttributes)
        private set

    override var volume: Float by mutableStateOf(player.volume)
        private set

    override var deviceInfo: DeviceInfo by mutableStateOf(player.deviceInfo)
        private set

    override var deviceVolume: Int by mutableStateOf(player.deviceVolume)
        private set

    override var isDeviceMuted: Boolean by mutableStateOf(player.isDeviceMuted)
        private set

    override var videoSize: VideoSize by mutableStateOf(player.videoSize)
        private set

    override var cueGroup: CueGroup by mutableStateOf(player.currentCues)
        private set

    private val listener = object : Player.Listener {
        override fun onRenderedFirstFrame() {
            this@PlayerStateImpl.firstFrameRendered = true
        }

        override fun onTimelineChanged(timeline: Timeline, reason: Int) {
            this@PlayerStateImpl.timeline = timeline
            this@PlayerStateImpl.mediaItemIndex = player.currentMediaItemIndex
        }

        override fun onMediaMetadataChanged(mediaMetadata: MediaMetadata) {
            this@PlayerStateImpl.mediaMetadata = mediaMetadata
        }

        override fun onPlaylistMetadataChanged(mediaMetadata: MediaMetadata) {
            this@PlayerStateImpl.playlistMetadata = mediaMetadata
        }

        override fun onIsLoadingChanged(isLoading: Boolean) {
            this@PlayerStateImpl.isLoading = isLoading
        }

        override fun onAvailableCommandsChanged(availableCommands: Player.Commands) {
            this@PlayerStateImpl.availableCommands = availableCommands
        }

        override fun onTrackSelectionParametersChanged(parameters: TrackSelectionParameters) {
            this@PlayerStateImpl.trackSelectionParameters = parameters
        }

        override fun onPlaybackStateChanged(@Player.State playbackState: Int) {
            this@PlayerStateImpl.playbackState = playbackState
            this@PlayerStateImpl.videoFormat = player.videoFormat
            this@PlayerStateImpl.audioFormat = player.audioFormat
        }

        override fun onPlayWhenReadyChanged(
            playWhenReady: Boolean,
            @Player.PlayWhenReadyChangeReason reason: Int
        ) {
            this@PlayerStateImpl.playWhenReady = playWhenReady
        }

        override fun onPlaybackSuppressionReasonChanged(playbackSuppressionReason: Int) {
            this@PlayerStateImpl.playbackSuppressionReason = playbackSuppressionReason
        }

        override fun onIsPlayingChanged(isPlaying: Boolean) {
            this@PlayerStateImpl.isPlaying = isPlaying
        }

        override fun onRepeatModeChanged(repeatMode: Int) {
            this@PlayerStateImpl.repeatMode = repeatMode
        }

        override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
            this@PlayerStateImpl.shuffleModeEnabled = shuffleModeEnabled
        }

        override fun onPlayerErrorChanged(error: PlaybackException?) {
            this@PlayerStateImpl.playerError = error
        }

        override fun onPositionDiscontinuity(
            oldPosition: PositionInfo,
            newPosition: PositionInfo,
            reason: Int
        ) {
            if (oldPosition.mediaItemIndex != newPosition.mediaItemIndex)
                this@PlayerStateImpl.oldPosition = oldPosition
            this@PlayerStateImpl.mediaItemIndex = player.currentMediaItemIndex
        }

        override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {
            this@PlayerStateImpl.playbackParameters = playbackParameters
        }

        override fun onSeekBackIncrementChanged(seekBackIncrementMs: Long) {
            this@PlayerStateImpl.seekBackIncrement = seekBackIncrementMs
        }

        override fun onSeekForwardIncrementChanged(seekForwardIncrementMs: Long) {
            this@PlayerStateImpl.seekForwardIncrement = seekForwardIncrementMs
        }

        override fun onMaxSeekToPreviousPositionChanged(maxSeekToPreviousPositionMs: Long) {
            this@PlayerStateImpl.maxSeekToPreviousPosition = maxSeekToPreviousPositionMs
        }

        override fun onAudioAttributesChanged(audioAttributes: AudioAttributes) {
            this@PlayerStateImpl.audioAttributes = audioAttributes
        }

        override fun onVolumeChanged(volume: Float) {
            this@PlayerStateImpl.volume = volume
        }

        override fun onDeviceInfoChanged(deviceInfo: DeviceInfo) {
            this@PlayerStateImpl.deviceInfo = deviceInfo
        }

        override fun onDeviceVolumeChanged(volume: Int, muted: Boolean) {
            this@PlayerStateImpl.deviceVolume = volume
            this@PlayerStateImpl.isDeviceMuted = muted
        }

        override fun onVideoSizeChanged(videoSize: VideoSize) {
            this@PlayerStateImpl.videoSize = videoSize
        }

        override fun onCues(cueGroup: CueGroup) {
            this@PlayerStateImpl.cueGroup = cueGroup
        }

        override fun onTracksChanged(tracks: Tracks) {
            this@PlayerStateImpl.audioTracks =
                tracks.groups.filter { it.type == C.TRACK_TYPE_AUDIO }
            this@PlayerStateImpl.subtitleTracks =
                tracks.groups.filter { it.type == C.TRACK_TYPE_TEXT }
        }

    }

    init {
        player.addListener(listener)
    }

    override fun dispose() {
        player.removeListener(listener)
    }
}