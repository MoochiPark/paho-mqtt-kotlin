import io.wisoft.project.Device
import io.wisoft.project.on
import kotlinx.coroutines.*

fun main(args: Array<String>) = runBlocking {
    val result = withTimeoutOrNull(30000) {
        repeat(100) { i ->
            launch(Dispatchers.IO) {
                Device("kotlin-device-$i")
                    .on(sendTimes = 10)
            }
        }
        "Well Done!"
    }
    println(result)
}
