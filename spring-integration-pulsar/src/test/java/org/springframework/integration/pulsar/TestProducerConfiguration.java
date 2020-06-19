package org.springframework.integration.pulsar;

import org.springframework.integration.pulsar.constant.Serialization;
import org.springframework.integration.pulsar.producer.ProducerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestProducerConfiguration {

    @Bean
    public ProducerFactory producerFactory() {
        return new ProducerFactory()
            .addProducer("topic-one", MyMsg.class)
            .addProducer("topic-two", MyMsg2.class, Serialization.JSON);
    }
}
