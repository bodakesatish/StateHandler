package com.bodakesatish.statehandler

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

class MainViewModel (
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

//    private val _color = MutableStateFlow(0xFFFFFFFF)
//    val color = _color.asStateFlow()

    val color = savedStateHandle.getStateFlow("color", 0xFFFFFFFF)

    var composeColor by mutableStateOf(0xFFFFFFFF)
        private set

    fun generateNewColor() {
        val color = Random.nextLong(0xFFFFFFFF)
        savedStateHandle["color"] = color
        composeColor = color
    }
}