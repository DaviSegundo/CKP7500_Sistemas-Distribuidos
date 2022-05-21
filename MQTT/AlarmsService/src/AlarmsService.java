import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class AlarmsService implements MqttCallback {
	static String highTemperatureTopic = "cauldron/highTemperature";
	static String temperatureShiftTopic = "cauldron/temperatureShift";
	static String broker = "tcp://mqtt.eclipseprojects.io:1883";
	static String clientId = "Sensors";

	public static void main(String[] args) {
		MemoryPersistence persistence = new MemoryPersistence();
        MqttClient client;
        while (true) {
			try {
				client = Connect(broker, clientId, persistence);
				client.subscribe(highTemperatureTopic);
				client.subscribe(temperatureShiftTopic);
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
	
	public static void PrintMessage(String message) {
		System.out.println("Alarm: "+message);
	}

	@Override
	public void connectionLost(Throwable cause) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		// TODO Auto-generated method stub
		if (topic == highTemperatureTopic || topic == temperatureShiftTopic) {
			PrintMessage(message.getPayload().toString());
		}
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		// TODO Auto-generated method stub
		
	}
}
