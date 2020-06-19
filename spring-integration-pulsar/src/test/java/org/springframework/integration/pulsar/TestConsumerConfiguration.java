package org.springframework.integration.pulsar;

import org.springframework.integration.pulsar.annotation.PulsarConsumer;
import org.springframework.integration.pulsar.constant.Serialization;
import org.junit.jupiter.api.Assertions;
import org.springframework.stereotype.Service;

@Service
public class TestConsumerConfiguration {

    @PulsarConsumer(topic = "mock-topic", clazz = MyMsg.class, serialization = Serialization.JSON)
    public void mockTheListener(MyMsg myMsg) {
        Assertions.assertNotNull(myMsg);
    }
}
