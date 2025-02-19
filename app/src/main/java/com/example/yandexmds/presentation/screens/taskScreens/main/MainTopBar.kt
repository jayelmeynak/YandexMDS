package com.example.yandexmds.presentation.screens.taskScreens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.yandexmds.R
import com.example.yandexmds.ui.theme.Blue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    countCompletedTasks: Int,
    filter: Boolean,
    onVisibilityClick: () -> Unit
) {

    LargeTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        title = {
            Row {
                Column(modifier = Modifier.weight(2F)) {
                    Text(
                        text = "Мои дела",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Выполнено - $countCompletedTasks",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                IconButton(
                    onClick = { onVisibilityClick() }) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = if(!filter) R.drawable.visibility else R.drawable.visibility_off),
                        contentDescription = null,
                        tint = Blue
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}