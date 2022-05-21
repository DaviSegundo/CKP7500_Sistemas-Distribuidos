import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class CatService implements MqttCallback {
	static String subscribeTopic = "cauldron/temperature";
	static String highTemperatureTopic = "cauldron/highTemperature";
	static String temperatureShiftTopic = "cauldron/temperatureShift";
	static String broker = "tcp://mqtt.eclipseprojects.io:1883";
	static String clientId = "Sensors";
	
	static MqttClient client;

	static int lastTemperature = 0;

	public static void main(String[] args) {
		MemoryPersistence persistence = new MemoryPersistence();
        
        while (true) {
			try {
				client = Connect(broker, clientId, persistence);
				client.subscribe(subscribeTopic);
				break;
			} catch (MqttException e1) {
				continue;
			}
        }
    }
	
	private static MqttClient Connect(String broker, String clientId, MemoryPersistence persistence) throws MqttException {
		MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        System.out.println("Connecting to broker: "+broker);
        sampleClient.connect(connOpts);
        System.out.println("Connected");
        return sampleClient;
	}
	
	private static void UpdateTemperature(int temperature) throws MqttPersistenceException, MqttException {
		int average = lastTemperature + temperature;
		lastTemperature = temperature;
		if (temperature > 200) {
			MqttMessage message = new MqttMessage("temperatura alta".getBytes());
			client.publish(highTemperatureTopic, message);
		}
		
		if (Math.abs(temperature-lastTemperature) > 5) {
			MqttMessage message = new MqttMessage("aumento de temperatura repentina".getBytes());
			client.publish(temperatureShiftTopic, message);
		}
		System.out.println("Temperature: "+temperature+" | Average: "+average);
	}

	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub
		
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		if (topic == subscribeTopic) {
			String text = message.getPayload().toString();
			UpdateTemperature(Integer.parseInt(text));
		}
	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
		
	}
}
