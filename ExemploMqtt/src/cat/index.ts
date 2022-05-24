import * as mqtt from "mqtt";

// compute-average-temperature
try {
  let count = 0;
  let tempValues: number[] = [];
  let avgValues: number[] = [];
  const client: mqtt.MqttClient = mqtt.connect("mqtt://test.mosquitto.org");
  client.on("connect", function () {
    console.log("Conectado ao broker MQTT");
    client.subscribe("temperature");
    console.log("Cliente inscrito no tópico 'temperature'!");
    console.log("Publicando mensagens de alta temperatura e aumento repentino de temperatura");
  });
  client.on("message", function (_topic, message) {
    if (message.toString() !== "") {
      count++;
      if (parseFloat(message.toString()) > 200) {
        // console.log("High temperature: ", message.toString(), "°C");
        client.publish("high-temperature", message.toString());
      }
      if (count === 2) {
        // console.log("Second received value: ", message.toString());
        tempValues.push(parseFloat(message.toString()));
        count = 0;
      }
      if (tempValues.length == 2) {
        const avgTemp = (tempValues[0] + tempValues[1]) / 2;
        // console.log("Average temperature: ", avgTemp, "°C");
        avgValues.push(avgTemp);
        tempValues = [];
      }

      if (avgValues.length == 2) {
        const avgDiff = avgValues[1] - avgValues[0];
        if (avgDiff > 5.0) {
          // console.log(
          //   `${avgValues[1]} - ${avgValues[0]}  = ${avgDiff} [Sudden temperature rise!]`
          // );
          client.publish("temperature-rise", avgDiff.toString());
        }
        avgValues[0] = avgValues[1];
        avgValues.pop();
      }
    }
  });
} catch ({ message }) {
  console.log(message);
}
