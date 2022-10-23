package com.arcticoss.nextplayer.feature.media.composables

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import com.arcticoss.nextplayer.feature.media.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CheckPermissionAndSetContent(
    topBar: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val permissions = listOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    var hasPermission by remember { mutableStateOf(false) }
    val multiplePermissionsState = rememberMultiplePermissionsState(permissions = permissions)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        AddLifecycleEventObserver(lifecycleOwner = lifecycleOwner) { event ->
            if (event == Lifecycle.Event.ON_START) {
                hasPermission = Environment.isExternalStorageManager()
            }
        }
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        AddLifecycleEventObserver(lifecycleOwner = lifecycleOwner) { event ->
            if (event == Lifecycle.Event.ON_START) {
                multiplePermissionsState.launchMultiplePermissionRequest()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = topBar,
        floatingActionButton = floatingActionButton
    ) { innerPadding ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !hasPermission) {
            ShowPermissionInfo(
                modifier = Modifier.padding(innerPadding),
                rationaleMessage = R.string.permission_info_red,
                actionButton = {
                    IconTextButton(
                        title = stringResource(id = R.string.open_settings),
                        icon = Icons.Rounded.Settings,
                        onClick = {
                            val intent = Intent(
                                Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                                Uri.fromParts("package", context.packageName, null)
                            )
                            context.startActivity(intent)
                        }
                    )
                }
            )
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
            Build.VERSION.SDK_INT < Build.VERSION_CODES.R &&
            !multiplePermissionsState.allPermissionsGranted
        ) {
            ShowPermissionInfo(modifier = Modifier.padding(innerPadding),
                rationaleMessage = R.string.permission_info,
                actionButton = {
                    if (multiplePermissionsState.shouldShowRationale) {
                        IconTextButton(
                            title = stringResource(id = R.string.grant_permission),
                            onClick = { multiplePermissionsState.launchMultiplePermissionRequest() }
                        )
                    } else {
                        IconTextButton(
                            title = stringResource(id = R.string.open_settings),
                            icon = Icons.Rounded.Settings,
                            onClick = {
                                val intent = Intent(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package", context.packageName, null)
                                )
                                context.startActivity(intent)
                            }
                        )
                    }
                }
            )
        }

        val permissionGranted = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && hasPermission) ||
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && multiplePermissionsState.allPermissionsGranted) ||
                (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        if (permissionGranted) {
            content(innerPadding)
        }
    }
}