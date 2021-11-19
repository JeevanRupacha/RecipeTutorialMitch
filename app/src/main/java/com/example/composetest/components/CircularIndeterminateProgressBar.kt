package com.example.composetest.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
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

