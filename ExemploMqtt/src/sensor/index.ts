import * as mqtt from "mqtt"


try {
    console.log("Iniciando sensor...");
    const client : mqtt.MqttClient = mqtt.connect("mqtt://test.mosquitto.org");
    client.on("connect", function () {
        console.log("Conectado ao broker MQTT");
        client.publish("temperature", "");
        console.log("Publicando mensagens no tópico 'temperature'");
        setInterval(function() {
            let randomTemp = Math.random() * 400;
            console.log("Temperatura atual: ", randomTemp, "°C");
            client.publish("temperature", randomTemp.toString());
        } , 2000);
    });      
    
} catch ({message}) {
    console.log(message);
}
