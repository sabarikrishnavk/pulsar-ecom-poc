package org.springframework.integration.pulsar.producer;

import org.springframework.integration.pulsar.constant.Serialization;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Map;

public interface PulsarProducerFactory {
    Map<String, ImmutablePair<Class<?>, Serialization>> getTopics();

}
