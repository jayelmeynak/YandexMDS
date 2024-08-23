package com.example.yandexmds.presentation.screens.main

import androidx.compose.foundation.layout.Column
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
fun TopBar(scrollBehavior: TopAppBarScrollBehavior, countCompletedTasks: Int) {

    LargeTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        title = {
            Column {
                Text(
                    //modifier = Modifier.padding(top = 82.dp, start = 60.dp, bottom = 4.dp),
                    text = "Мои дела",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    //modifier = Modifier.padding(start = 60.dp),
                    text = "Выполнено - $countCompletedTasks",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        },
        actions = {
            IconButton(
                //modifier = Modifier.padding(end = 24.dp),
                onClick = { /*TODO*/ }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.visibility),
                    contentDescription = null,
                    tint = Blue
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}