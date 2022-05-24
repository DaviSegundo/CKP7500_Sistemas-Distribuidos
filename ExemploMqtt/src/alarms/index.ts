import * as mqtt from "mqtt";

// compute-average-temperature
try {
  const client: mqtt.MqttClient = mqtt.connect("mqtt://test.mosquitto.org");
  client.on("connect", function () {
    console.log("Conectado ao broker MQTT");
    client.subscribe("high-temperature");
    client.subscribe("temperature-rise");
    console.log("Cliente inscrito nos tópicos 'high-temperature' e 'temperature-rise'!");
  });
  client.on("message", function (topic, message) {
    const date = new Date();
    const formattedDate = `${date.getHours()}:${date.getMinutes()}:${date.getSeconds()}`;
    switch (topic) {
      case "high-temperature":
        console.log(`${formattedDate} -  Alta temperatura - acima de 200ºC: ${message}`);
        break;
      case "temperature-rise":
        console.log(`${formattedDate} -  Aumento repentino de temperatura - acima de 5ºC: ${message}`);
        break;
      default:
        console.log(`${formattedDate} - Tópico: ${topic} - Mensagem recebida: ${message}`); 
    }
  });
} catch ({ message }) {
  console.log(message);
}
