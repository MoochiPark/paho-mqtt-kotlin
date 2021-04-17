package io.wisoft.project.device

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions

class Device(
    val clientId: String,
//    private val broker: String = "tcp://wisoftlabs.ml:1883",
    private val broker: String = "tcp://localhost:1883",
    val topic: String = "wstest",
    private val client: MqttClient = MqttClient(broker, clientId, null),
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
            send("message $sendCount by $clientId")
            delay(1000)
            sendCount++
        }
        disconnect()
    }

    private suspend inline fun send(content: String) = withContext(Dispatchers.IO) {
        client.publish(topic, content.toByteArray(), 0, false)
        println("Published message: $content")
    }

    private fun connect() = client.connect(options)


    private fun disconnect() = client.disconnect()
}
