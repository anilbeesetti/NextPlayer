package com.arcticoss.nextplayer.core.datastore.datasource

import androidx.datastore.core.DataStore
import com.arcticoss.model.InterfacePreferences
import com.arcticoss.model.SortBy
import com.arcticoss.model.SortOrder
import com.arcticoss.model.Theme
import javax.inject.Inject

class InterfacePreferencesDataSource @Inject constructor(
    private val interfacePreferences: DataStore<InterfacePreferences>
) {

    val interfacePreferencesStream = interfacePreferences.data

    suspend fun updateInterfacePreferences(interfacePref: InterfacePreferences) {
        interfacePreferences.updateData { interfacePref }
    }

    suspend fun updateTheme(theme: Theme) {
        interfacePreferences.updateData {
            it.copy(theme = theme)
        }
    }
    suspend fun updateSortBy(sortBy: SortBy) {
        interfacePreferences.updateData {
            it.copy(sortBy = sortBy)
        }
    }
    suspend fun updateSortOrder(sortOrder: SortOrder) {
        interfacePreferences.updateData {
            it.copy(sortOrder = sortOrder)
        }
    }
    suspend fun toggleShowFloatingButton() {
        interfacePreferences.updateData {
            it.copy(showFloatingButton = !it.showFloatingButton)
        }
    }
    suspend fun toggleShowHidden() {
        interfacePreferences.updateData {
            it.copy(showHidden = !it.showHidden)
        }
    }
    suspend fun toggleGroupVideos() {
        interfacePreferences.updateData {
            it.copy(groupVideos = !it.groupVideos)
        }
    }
}