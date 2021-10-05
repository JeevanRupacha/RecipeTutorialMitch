package com.example.composetest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return ComposeView(requireContext()).apply {
            setContent {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "First Fragment ")
                    Spacer(modifier = Modifier.padding(bottom = 16.dp))
                    Button(onClick = {
                        Toast.makeText(requireContext(), "clicked", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_firstFragment_to_secondFragment)
                    }) {
                        Text(
                            text = "GO NEXT"
                            )
                    }
                }
            }
        }
    }
}