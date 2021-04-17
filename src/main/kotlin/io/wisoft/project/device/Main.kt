package io.wisoft.project.device

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) = runBlocking {

    measureTimeMillis {

        val result = withTimeoutOrNull(50000) {
            repeat(1000) { i ->
                launch(Dispatchers.IO) {
                    Device("kotlin-device-$i").on(10)
                }
            }
            "Well Done!"
        }
        println(result)
    }
}



