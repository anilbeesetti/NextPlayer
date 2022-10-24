package com.arcticoss.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayerPreferences(
    val saveBrightnessLevel: Boolean = true,
    val savePlayBackSpeed: Boolean = false,
    val brightnessLevel: Int = 15,
    val resume: Resume = Resume.Always,
    val playbackSpeed: Int = 100,
    val fastSeeking: Boolean = true,
    val aspectRatio: AspectRatio = AspectRatio.FitScreen
)


enum class Resume(val title: String) {
    Always(title = "Always"),
    Never(title = "Never"),
    Ask(title = "Ask at startup")
}

enum class AspectRatio(val title: String) {
    FitScreen(title = "Fit to Screen"),
    Stretch(title = "Stretch"),
    Crop(title = "Crop"),
    HundredPercent(title = "100%")
}
