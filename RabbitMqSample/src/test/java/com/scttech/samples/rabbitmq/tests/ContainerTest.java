package com.scttech.samples.rabbitmq.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.Test;

import com.scttech.samples.rabbitmq.AbstractComponentTest;
import com.scttech.samples.rabbitmq.ContainerSetup;
import com.scttech.samples.rabbitmq.client.Client;

class ContainerTest extends AbstractComponentTest {

	@Test
	void sendReceiveMessageTest() throws IOException, TimeoutException {
		
		Client client = new Client.ClientBuilder()
				.setHost(ContainerSetup.getRabbitMQContainerIPAddress())
				.setPort(ContainerSetup.getRabbitMQContainerPort())
				.setUsername("guest")
				.setPassword("guest")
				.build();
		
		String msg = "Hello World";

		client.send(msg);
		assertEquals(msg, client.receive());
		
	}

}
