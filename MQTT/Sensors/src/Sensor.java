import java.util.Random;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Sensor {
	static String topic = "cauldron/temperature";
	static String broker = "tcp://mqtt.eclipseprojects.io:1883";
	static String clientId = "Sensors";

	static Random random = new Random();

	public static void main(String[] args) {
        MemoryPersistence persistence = new MemoryPersistence();
        MqttClient client;
        while (true) {
			try {
				client = Connect(broker, clientId, persistence);
				break;
			} catch (MqttException e1) {
				continue;
			}
        }
        
        while (true) {
        	try {
        		UpdateTemperature(client, topic);
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
        
//        client.disconnect();
//        System.out.println("Disconnected");
//        System.exit(0);
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

	private static void UpdateTemperature(MqttClient sampleClient, String topic) {
		int temperature = random.nextInt(100, 300); // Simular a mudança de temperatura
		int qos = 2;

		try {
            System.out.println("Publishing message: "+temperature);
            MqttMessage message = new MqttMessage((temperature+"").getBytes());
            message.setQos(qos);
            sampleClient.publish(topic, message);
            System.out.println("Message published");
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
	}
}
