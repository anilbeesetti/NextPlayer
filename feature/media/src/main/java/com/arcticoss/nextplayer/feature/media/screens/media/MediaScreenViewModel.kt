package com.arcticoss.nextplayer.feature.media.screens.media

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arcticoss.nextplayer.core.datastore.datasource.UiPreferencesDataSource
import com.arcticoss.nextplayer.core.domain.GetSortedFoldersStreamUseCase
import com.arcticoss.nextplayer.core.domain.GetSortedMediaItemsStreamUseCase
import com.arcticoss.nextplayer.core.domain.models.Folder
import com.arcticoss.nextplayer.core.model.UiPreferences
import com.arcticoss.nextplayer.core.model.Media
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class MediaScreenViewModel @Inject constructor(
    preferencesDataSource: UiPreferencesDataSource,
    getSortedFoldersStream: GetSortedFoldersStreamUseCase,
    getSortedMediaItemsStream: GetSortedMediaItemsStreamUseCase
) : ViewModel() {

    val preferencesStateFlow = preferencesDataSource
        .uiPreferencesFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UiPreferences()
        )

    val folderUiState: StateFlow<FolderUiState> =
        getSortedFoldersStream().map { FolderUiState.Success(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = FolderUiState.Loading
            )

    val mediaUiState: StateFlow<MediaUiState> =
        getSortedMediaItemsStream().map { MediaUiState.Success(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = MediaUiState.Loading
            )
}

sealed interface FolderUiState {
    data class Success(val folders: List<Folder>) : FolderUiState
    object Loading : FolderUiState
}

sealed interface MediaUiState {
    data class Success(val mediaItems: List<Media>) : MediaUiState
    object Loading : MediaUiState
}