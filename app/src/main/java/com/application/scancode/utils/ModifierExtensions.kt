package com.application.scancode.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

// Extensions para animações
fun Modifier.scale(scale: Float) = this.then(
    graphicsLayer {
        scaleX = scale
        scaleY = scale
    }
)

fun Modifier.alpha(alpha: Float) = this.then(
    graphicsLayer {
        this.alpha = alpha
    }
)