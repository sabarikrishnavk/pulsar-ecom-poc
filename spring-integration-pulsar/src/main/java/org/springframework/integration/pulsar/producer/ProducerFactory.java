package org.springframework.integration.pulsar.producer;

import org.springframework.integration.pulsar.annotation.PulsarProducer;
import org.springframework.integration.pulsar.constant.Serialization;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.HashMap;
import java.util.Map;

@PulsarProducer
public class ProducerFactory implements PulsarProducerFactory {

    private final Map<String, ImmutablePair<Class<?>, Serialization>> topics = new HashMap<>();

    public ProducerFactory addProducer(String topic, Class<?> clazz) {
        topics.put(topic, new ImmutablePair<>(clazz, Serialization.JSON));
        return this;
    }

    public ProducerFactory addProducer(String topic, Class<?> clazz, Serialization serialization) {
        topics.put(topic, new ImmutablePair<>(clazz, serialization));
        return this;
    }

    public Map<String, ImmutablePair<Class<?>, Serialization>> getTopics() {
        return topics;
    }
}
