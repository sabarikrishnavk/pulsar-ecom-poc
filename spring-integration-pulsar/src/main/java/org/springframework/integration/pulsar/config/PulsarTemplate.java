package org.springframework.integration.pulsar.config;

import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.integration.pulsar.processor.ProducerBeanProcessor;
import org.springframework.stereotype.Component;

@Component
public class PulsarTemplate<T> {
    private final ProducerBeanProcessor producerBeanProcessor;

    public PulsarTemplate(ProducerBeanProcessor producerBeanProcessor) {
        this.producerBeanProcessor = producerBeanProcessor;
    }
    public MessageId send(String topic, T msg) throws PulsarClientException {
        return producerBeanProcessor.getProducers().get(topic).send(msg);
    }
}
