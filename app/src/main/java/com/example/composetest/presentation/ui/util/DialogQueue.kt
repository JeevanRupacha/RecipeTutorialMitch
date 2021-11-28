package com.example.composetest.presentation.ui.util

import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.composetest.presentation.components.GenericDialogInfo
import com.example.composetest.presentation.components.PositiveAction
import java.util.*

class DialogQueue {

    val queue: MutableState<Queue<GenericDialogInfo>> = mutableStateOf(LinkedList())

    fun removeHeadMessage()
    {
        if(queue.value.isNotEmpty())
        {
            val update = queue.value
            update.remove()
            queue.value = ArrayDeque() // force to recompose manually because of LinkedList type dsa
            queue.value = update
        }
    }

    fun appendErrorMessage(title: String, message: String)
    {
        queue.value.offer(
            GenericDialogInfo.Builder()
                .title(title)
                .description(message)
                .onDismiss{removeHeadMessage()}
                .positiveAction(
                    positiveAction = PositiveAction(buttonText = "Ok", onPositiveBtnAction = this::removeHeadMessage)
                )
                .build()
        )
    }
}