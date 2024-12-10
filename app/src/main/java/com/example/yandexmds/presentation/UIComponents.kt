package com.example.yandexmds.presentation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.yandexmds.R
import com.example.yandexmds.ui.theme.Blue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTopBar(
    onCancelClickListener: () -> Unit,
    onSaveClickListener: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = {
                onCancelClickListener()
            }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.close),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        actions = {
            Button(
                colors = ButtonDefaults.buttonColors()
                    .copy(containerColor = MaterialTheme.colorScheme.background),
                onClick = {
                    onSaveClickListener()
                }) {
                Text(text = "Сохранить", color = Blue, style = MaterialTheme.typography.titleSmall)
            }
        },
        title = { }
    )
}
