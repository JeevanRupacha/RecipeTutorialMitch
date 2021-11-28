package com.example.composetest.presentation.components

import android.graphics.Color
import android.graphics.Color.RED
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun GenericDialog(
    modifier: Modifier = Modifier,
    onDismiss: ()-> Unit,
    title: String,
    description: String?,
    positiveAction: PositiveAction?,
    negativeAction: NegativeAction?,
    ) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = {Text(text = title)},
        text = {
            if(description != null) Text(text = description)
        },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                negativeAction?.let {
                    Button(
                        modifier = Modifier.padding(end = 8.dp),
                        onClick = it.onNegativeBtnAction
                    ) {
                        Text(text = it.buttonText)
                    }
                }

                positiveAction?.let {
                    Button(
                        modifier = Modifier.padding(end = 8.dp),
                        onClick = it.onPositiveBtnAction
                    ) {
                        Text(text = it.buttonText)
                    }
                }
            }



        }
    )
}

data class PositiveAction(
    val buttonText :String,
    val onPositiveBtnAction: () -> Unit,
)

data class NegativeAction(
    val buttonText: String,
    val onNegativeBtnAction: () -> Unit,
)

class GenericDialogInfo
private constructor(builder: GenericDialogInfo.Builder)
{
    val title: String
    val onDismiss: () -> Unit
    val descrition: String?
    val positiveAction: PositiveAction?
    val negativeAction: NegativeAction?

    init {

        if(builder.title == null)
        {
            throw Exception("GenericDialog Title can't be Empty ")
        }

        if(builder.onDismiss == null){
            throw Exception("GenericDialog onDismiss can't be null")
        }

        this.title = builder.title!!
        this.onDismiss = builder.onDismiss!!
        this.descrition =  builder.description
        this.positiveAction = builder.positiveAction
        this.negativeAction = builder.negativeAction
    }


    class Builder{
        var title: String? = null
            private set

        var onDismiss: (()-> Unit)? = null
            private set

        var description: String? = null
            private set

        var positiveAction: PositiveAction? = null
            private set

        var negativeAction: NegativeAction? = null
            private set

        fun title(title: String): Builder
        {
            this.title = title
            return this
        }

        fun description(description: String): Builder
        {
            this.description = description
            return this
        }

        fun onDismiss(onDismiss: ()-> Unit): Builder
        {
            this.onDismiss = onDismiss
            return this
        }

        fun positiveAction(positiveAction: PositiveAction): Builder
        {
            this.positiveAction = positiveAction
            return this
        }

        fun negativeAction(negativeAction: NegativeAction): Builder
        {
            this.negativeAction = negativeAction
            return this
        }

        fun build() = GenericDialogInfo(this)
    }
}

