package com.scttech.samples.rabbitmq;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(
        initializers = { ComponentTestContextInitializer.class },
        classes = {ComponentTestContextConfig.class}
)

public abstract class AbstractComponentTest {
}