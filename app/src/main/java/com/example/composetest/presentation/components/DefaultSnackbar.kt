package com.example.composetest.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp

@Composable
fun DefaultSnackbar(
    snackbarHostState: SnackbarHostState,
    onDismiss: () -> Unit?,
    modifier: Modifier = Modifier
)
{
    SnackbarHost(
        hostState = snackbarHostState,
        modifier = modifier,
        snackbar = {data ->
            Snackbar(
                modifier = Modifier.padding(8.dp),
                content = {
                    Text(
                        text = data.message,
                        style = MaterialTheme.typography.body2,
                        color = White
                        )
                },
                action = {
                    data.actionLabel?.let { actionLabel ->
                        TextButton(
                            onClick = {
                                onDismiss()
                            }
                        ) {
                            Text(
                                text = actionLabel,
                                style = MaterialTheme.typography.body2,
                                color = White
                            )
                        }
                    }
                }
            )
        }
    )
}