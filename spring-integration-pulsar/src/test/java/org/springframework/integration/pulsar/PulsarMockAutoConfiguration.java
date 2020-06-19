package org.springframework.integration.pulsar;

import org.springframework.integration.pulsar.mock.MockPulsarClient;
import org.apache.pulsar.client.api.PulsarClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PulsarMockAutoConfiguration {

    @Bean
    public PulsarClient pulsarClient() {
        return new MockPulsarClient();
    }
}
