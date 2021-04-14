package io.wisoft.project

import kotlinx.coroutines.*
import org.eclipse.paho.mqttv5.client.MqttAsyncClient
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence

class Device(
    val clientId: String,
    private val broker: String = "tcp://broker.emqx.io:1883",
    val topic: String = "wstest",
    val qos: Int = 0,
    private val persistence: MemoryPersistence = MemoryPersistence(),
    val client: MqttAsyncClient = MqttAsyncClient(broker, clientId, persistence)
) {
    init {
        val options = MqttConnectionOptions()
        options.isCleanStart = true
        client.connect(options).waitForCompletion(5000)
        println("Connected")
    }
}

suspend fun Device.on(sendTimes: Int = 10) {
    var sendCount = 1
    repeat(sendTimes) {
        send("message $sendCount by $clientId")
        sendCount++
        delay(1000)
    }
}

fun Device.send(content: String) {
    client.publish(topic, content.toByteArray(), qos, true)
    println("Published message: $content")
}

fun Device.disconnect() {
    client.disconnect()
    println("Disconnected")
}
