package com.wsirius.mqttclient;

import org.fusesource.hawtbuf.Buffer;
import static org.fusesource.hawtbuf.Buffer.*;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.*;
import org.fusesource.mqtt.codec.MQTTFrame;

public class WMqttClient {

	public void mqttClientUliti() throws Exception {
		final Promise<Buffer> result = new Promise<Buffer>();
		MQTT mqtt = new MQTT();
		mqtt.setHost("localhost", 129);
		mqtt.setTracer(new Tracer(){
			@Override
			public void onReceive(MQTTFrame frame) {
				System.out.println("recv: "+frame);
			}

			@Override
			public void onSend(MQTTFrame frame) {
				System.out.println("send: "+frame);
			}

			@Override
			public void debug(String message, Object... args) {
				System.out.println(String.format("debug: "+message, args));
			}
		});

		final CallbackConnection connection = mqtt.callbackConnection();

		// Start add a listener to process subscription messages, and start the
		// resume the connection so it starts receiving messages from the socket.
		connection.listener(new Listener() {
			public void onConnected() {
				System.out.println("connected");
			}

			public void onDisconnected() {
				System.out.println("disconnected");
			}

			public void onPublish(UTF8Buffer topic, Buffer payload, Runnable onComplete) {
				result.onSuccess(payload);
				onComplete.run();
			}

			public void onFailure(Throwable value) {
				System.out.println("failure: "+value);
				result.onFailure(value);
				connection.disconnect(null);
			}
		});

		connection.connect(new Callback<Void>() {
			// Once we connect..
			public void onSuccess(Void v) {

				// Subscribe to a topic foo
				Topic[] topics = {new Topic(utf8("foo"), QoS.AT_LEAST_ONCE)};
				connection.subscribe(topics, new Callback<byte[]>() {
					public void onSuccess(byte[] value) {
						// Once subscribed, publish a message on the same topic.
						connection.publish("foo", "Hello".getBytes(), QoS.AT_LEAST_ONCE, false, null);
					}

					public void onFailure(Throwable value) {
						result.onFailure(value);
						connection.disconnect(null);
					}
				});

			}

			public void onFailure(Throwable value) {
				result.onFailure(value);
			}
		});

		//assertEquals("Hello", new String(result.await().toByteArray()));
	}
}
