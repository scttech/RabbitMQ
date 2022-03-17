package com.scttech.samples.rabbitmq.client;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

public class Client {

	private String host;
	private int port;
	private String username;
	private String password;
	ConnectionFactory factory = new ConnectionFactory();

	public Client(ClientBuilder builder) {
		this.host = builder.host;
		this.port = builder.port;
		this.username = builder.username;
		this.password = builder.password;
		
		factory.setHost(host);
		factory.setPort(port);
		factory.setUsername(username);
		factory.setPassword(password);
		
	}

	public void send(String message) throws IOException, TimeoutException {
		try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
			channel.queueDeclare(Constants.QUEUE_NAME, true, false, false, null);
			channel.basicPublish("", Constants.QUEUE_NAME, null, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");
		}
	}

	public String receive() throws IOException, TimeoutException {
		
		String message = "";
		try(Connection connection = factory.newConnection();
				Channel channel = connection.createChannel()){
			channel.queueDeclare(Constants.QUEUE_NAME, true, false, false, null);

			GetResponse getResponse = channel.basicGet(Constants.QUEUE_NAME, true);

			message = new String(getResponse.getBody());			

		}
		
		return message;
		
	}
	
	public String receiveAsync() throws IOException, TimeoutException {
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(Constants.QUEUE_NAME, true, false, false, null);

		/*
		 * DeliverCallback deliverCallback = (consumerTag, delivery) -> { String message
		 * = new String(delivery.getBody(), "UTF-8");
		 * System.out.println(" [x] Received '" + message + "'"); };
		 * 
		 * channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
		 */

		GetResponse getResponse = channel.basicGet(Constants.QUEUE_NAME, true);

		String message = new String(getResponse.getBody());

		return message;

	}	

	public static class ClientBuilder {
		private String host;
		private int port;
		private String username;
		private String password;

		public ClientBuilder setHost(String host) {
			this.host = host;
			return this;
		}

		public ClientBuilder setPort(int port) {
			this.port = port;
			return this;
		}

		public ClientBuilder setUsername(String username) {
			this.username = username;
			return this;
		}

		public ClientBuilder setPassword(String password) {
			this.password = password;
			return this;
		}

		public Client build() {
			return new Client(this);
		}
	}

}
