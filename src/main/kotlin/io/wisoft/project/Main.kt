import io.wisoft.project.Device
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

fun main(args: Array<String>) = runBlocking {
    val result = withTimeoutOrNull(30000) {
        repeat(950) { i ->
            launch(Dispatchers.IO) {
                Device("kotlin-device-$i")
                    .on(sendTimes = 10)
            }
        }
        "Well Done!"
    }
    println(result)
}
