package com.example

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.material3.Text

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Test Window") {
        Text("Hello, Compose for Desktop!")
    }
}
