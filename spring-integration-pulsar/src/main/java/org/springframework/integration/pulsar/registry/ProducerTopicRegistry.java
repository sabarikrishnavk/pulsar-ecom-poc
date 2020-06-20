package org.springframework.integration.pulsar.registry;

import org.springframework.integration.pulsar.annotation.PulsarProducer;
import org.springframework.integration.pulsar.bean.PulsarSchemaType;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.HashMap;
import java.util.Map;

@PulsarProducer
public class ProducerTopicRegistry {

    private final Map<String, ImmutablePair<Class<?>, PulsarSchemaType>> topics = new HashMap<>();

    public ProducerTopicRegistry registerTopic(String topic, Class<?> clazz) {
        topics.put(topic, new ImmutablePair<>(clazz, PulsarSchemaType.JSON));
        return this;
    }

    public ProducerTopicRegistry registerTopic(String topic, Class<?> clazz, PulsarSchemaType schemaType) {
        topics.put(topic, new ImmutablePair<>(clazz, schemaType));
        return this;
    }

    public Map<String, ImmutablePair<Class<?>, PulsarSchemaType>> getTopics() {
        return topics;
    }
}
