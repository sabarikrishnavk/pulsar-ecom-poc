package org.springframework.integration.pulsar.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProducerBean {
    String topic;
    Class<?> clazz;
    PulsarSchemaType schema;
}
