package com.scttech.samples.rabbitmq;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class ComponentTestContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

        ContainerSetup.initTestContainers(configurableApplicationContext.getEnvironment());
        
        // alter spring boot system properties so that it will connect to test containers and wiremock
        TestPropertyValues values = TestPropertyValues.of(
                "spring.rabbitmq.host=" + ContainerSetup.getRabbitMQContainerIPAddress(),
                "spring.rabbitmq.port=" + ContainerSetup.getRabbitMQContainerPort()
        );

        values.applyTo(configurableApplicationContext);

        System.out.println("======= Customized properties settings =======");
        System.out.println("spring.rabbitmq.host=" + ContainerSetup.getRabbitMQContainerIPAddress());
        System.out.println("spring.rabbitmq.port=" + ContainerSetup.getRabbitMQContainerPort());
        System.out.println("==============");

    }
}