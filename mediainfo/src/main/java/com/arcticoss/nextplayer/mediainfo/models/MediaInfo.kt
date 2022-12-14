package com.arcticoss.nextplayer.mediainfo.models

data class MediaInfo(
    val size: Long,
    val title: String,
    val duration: Long,
    val filePath: String,
    val lastModified: Long,
    val width: Int,
    val height: Int,
    val frameRate: FrameRate,
    val fileFormatName: String,
    val videoStreams: List<VideoStream>,
    val audioStreams: List<AudioStream>,
    val subtitleStreams: List<SubtitleStream>
)
