package no.hvl.dat110.iotsystem;

import no.hvl.dat110.client.Client;
import no.hvl.dat110.common.TODO;

public class TemperatureDevice {

	private static final int COUNT = 10;

	public static void main(String[] args) {

		// simulated / virtual temperature sensor
		TemperatureSensor sn = new TemperatureSensor();


		// create a client object and use it to

		// - connect to the broker - user "sensor" as the user name
		// - publish the temperature(s)
		// - disconnect from the broker



		String user = "sensor";
		String topic= Common.TEMPTOPIC;
		
		Client klient = new Client(user, Common.BROKERHOST, Common.BROKERPORT);
		klient.connect();
		
		for(int i=0;i<COUNT;i++) {
			String msg= "temp: "+sn.read();
			klient.publish(topic, msg);
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
		
		System.out.println("Temperature device stopping ... ");
		klient.disconnect();

	}
}
