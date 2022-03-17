package com.scttech.samples.rabbitmq;

import org.slf4j.LoggerFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import com.scttech.samples.rabbitmq.client.Constants;

public class ContainerSetup {

	private static final RabbitMQContainer rabbitMqContainer = new RabbitMQContainer("rabbitmq:3.8-management")
			.withQueue(Constants.QUEUE_NAME)
			.withExposedPorts(5672);

	static public void initTestContainers(ConfigurableEnvironment configEnv) {
		rabbitMqContainer.addEnv("RABBITMQ_DEFAULT_VHOST", configEnv.getProperty("spring.rabbitmq.virtual-host"));
		rabbitMqContainer.addEnv("RABBITMQ_DEFAULT_USER", configEnv.getProperty("spring.rabbitmq.username"));
		rabbitMqContainer.addEnv("RABBITMQ_DEFAULT_PASS", configEnv.getProperty("spring.rabbitmq.password"));
		rabbitMqContainer.start();
		rabbitMqContainer.followOutput(new Slf4jLogConsumer(LoggerFactory.getLogger("container.RabbitMQ")));
	}

	public static String getRabbitMQContainerIPAddress() {
		return rabbitMqContainer.getContainerIpAddress();
	}
	
	public static String getHost() {
		return rabbitMqContainer.getHost();
	}

	public static int getRabbitMQContainerPort() {
		return rabbitMqContainer.getMappedPort(5672);
	}

}
