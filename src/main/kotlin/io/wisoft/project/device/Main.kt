package io.wisoft.project.device

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.system.measureTimeMillis

fun main(args: Array<String>): Unit = runBlocking {
    measureTimeMillis {
        val result = withTimeoutOrNull(50000) {
            repeat(args[1].toInt()) { i ->
                launch(Dispatchers.IO) {
                    Device("kotlin-device-$i", broker = args[0]).on(args[2].toInt())
                }
            }
            "Well Done!"
        }
        println(result)
    }
}



