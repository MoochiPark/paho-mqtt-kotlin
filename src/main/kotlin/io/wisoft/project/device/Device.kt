package io.wisoft.project.device

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions

class Device(
    val clientId: String,
    private val broker: String = "localhost",
    val topic: String = "wstest",
    private val client: MqttClient = MqttClient("tcp://$broker:1883", clientId, null),
    private val options: MqttConnectOptions = MqttConnectOptions().apply {
        isCleanSession = true
        isAutomaticReconnect = true
        connectionTimeout = 10
    }
) {

    suspend fun on(sendTimes: Int = Int.MAX_VALUE) {
        var sendCount = 1
        connect()
        repeat(sendTimes) {
            send(
                """120120120120120120120120120120
                |120120120120120120120120120120
                |120120120120120120120120120120
                |120120120120120120120120120120
            """.trimMargin()
            )
            println(
                "send $sendCount by $clientId. Thread count: ${Thread.activeCount()}, Free: ${
                    Runtime.getRuntime().freeMemory()
                }, Total: ${Runtime.getRuntime().totalMemory()}, Max: ${Runtime.getRuntime().maxMemory()}"
            )

            delay(1000)
            sendCount++
        }
        disconnect()
    }

    private suspend inline fun send(content: String) = withContext(Dispatchers.IO) {
        client.publish(topic, content.toByteArray(), 0, false)
    }

    private fun connect() = client.connect(options)


    private fun disconnect() = client.disconnect()
}
