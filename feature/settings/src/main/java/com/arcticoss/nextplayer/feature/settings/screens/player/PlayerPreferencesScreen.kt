package com.arcticoss.nextplayer.feature.settings.screens.player

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arcticoss.nextplayer.feature.settings.R
import com.arcticoss.nextplayer.feature.settings.composables.ClickablePreferenceItem
import com.arcticoss.nextplayer.feature.settings.composables.PreferenceSwitch
import com.arcticoss.nextplayer.feature.settings.screens.display.PreferenceGroupTitle

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLifecycleComposeApi::class)
@Composable
fun PlayerPreferencesScreen(
    onBackClick: () -> Unit,
    viewModel: PlayerPreferencesViewModel = hiltViewModel()
) {

    val preferences by viewModel.preferencesFlow.collectAsStateWithLifecycle()

    val scrollBehaviour = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehaviour.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.player)
                    )
                },
                scrollBehavior = scrollBehaviour,
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = ""
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                PreferenceGroupTitle(text = stringResource(id = R.string.playback))
            }
            item {
                ClickablePreferenceItem(
                    title = stringResource(id = R.string.resume),
                    description = stringResource(id = R.string.resume_description),
                    onClick = {}
                )
            }
            item {
                PreferenceSwitch(
                    title = stringResource(id = R.string.save_brightness),
                    description = stringResource(id = R.string.save_brightness_description),
                    isChecked = preferences.saveBrightnessLevel,
                    onClick = viewModel::toggleSaveBrightnessLevel
                )
            }
            item {
                PreferenceSwitch(
                    title = stringResource(id = R.string.fast_seeking),
                    description = stringResource(id = R.string.fast_seeking_description),
                    isChecked = preferences.fastSeeking,
                    onClick = viewModel::toggleFastSeeking
                )
            }
        }
    }
}