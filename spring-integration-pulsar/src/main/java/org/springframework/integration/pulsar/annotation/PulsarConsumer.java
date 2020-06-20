package org.springframework.integration.pulsar.annotation;

import org.springframework.integration.pulsar.bean.PulsarSchemaType;
import org.apache.pulsar.client.api.SubscriptionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PulsarConsumer {
    String topic();
    Class<?> clazz();
    PulsarSchemaType schemaType() default PulsarSchemaType.JSON;
    SubscriptionType type() default SubscriptionType.Exclusive;
}
