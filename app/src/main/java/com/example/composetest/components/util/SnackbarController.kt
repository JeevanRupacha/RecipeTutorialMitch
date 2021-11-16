package com.example.composetest.components.util

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * If a snackbar is visible and the user triggers a second snackbar to show, it will remove
 * the first one and show the second. Likewise with a third, fourth, ect...
 *
 * If a mechanism like this is not used, snackbar get added to the Scaffolds "queue", and will
 * show one after another. I don't like that.
 *
 */

class SnackbarController(
    private val scope: CoroutineScope
){
    private var snackbarJob: Job? = null

    fun showSnackbar(
        scaffoldSate: ScaffoldState,
        message: String,
        actionLabel: String
    )
    {
      if(snackbarJob == null)
      {
          snackbarJob = scope.launch {
              scaffoldSate.snackbarHostState.showSnackbar(
                  message = message,
                  actionLabel = actionLabel,
                  duration = SnackbarDuration.Short
              )

              cancelJob()
          }

      }else{
          cancelJob()
          snackbarJob = scope.launch {
              scaffoldSate.snackbarHostState.showSnackbar(
                  message = message,
                  actionLabel = actionLabel,
                  duration = SnackbarDuration.Short
              )

              cancelJob()

          }

      }
    }

    fun getScope() = scope

    private fun cancelJob()
    {
      snackbarJob?.let { job ->
          job.cancel()
      }
    }
}