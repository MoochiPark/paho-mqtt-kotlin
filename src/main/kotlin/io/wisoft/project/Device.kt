package io.wisoft.project

import kotlinx.coroutines.delay
import org.eclipse.paho.mqttv5.client.IMqttToken
import org.eclipse.paho.mqttv5.client.MqttAsyncClient
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence

class Device(
    val clientId: String,
    private val broker: String = "tcp://localhost:1883",
//    private val broker: String = "tcp://broker.emqx.io:1883",
    val topic: String = "wstest",
    val qos: Int = 0,
    private val persistence: MemoryPersistence = MemoryPersistence(),
    val client: MqttAsyncClient = MqttAsyncClient(broker, clientId, persistence)
) {
    init {
        val options =
            MqttConnectionOptions().apply {
                isCleanStart = true
                keepAliveInterval = 15
                connectionTimeout = 30
            }
        client.connect(options)
            .waitForCompletion(15000)
        println("Connected")
    }

    suspend fun on(sendTimes: Int = 10) {
        var sendCount = 1
        repeat(sendTimes) {
            send("message $sendCount by $clientId")
            sendCount++
            delay(1000)
        }
    }

    fun disconnect(): IMqttToken = client.disconnect()

    private fun send(content: String) {
        client.publish(topic, content.toByteArray(), qos, false)
        println("Published message: $content")
    }
}
