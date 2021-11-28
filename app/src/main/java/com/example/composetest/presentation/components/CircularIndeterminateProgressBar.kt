package com.example.composetest.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout


@ExperimentalComposeUiApi
@Composable
fun CircularIndeterminateProgressBar(display: Boolean, verticalBias: Float){

    if(display)
    {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize(),
        ) {
            val (cProgress) = createRefs()
            val topBias = createGuidelineFromTop(verticalBias)

            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .constrainAs(cProgress){
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(topBias)
                    }
            )
        }
    }
}

