package org.springframework.integration.pulsar.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.integration.pulsar.annotation.PulsarConsumer;

import java.lang.reflect.Method;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsumerBean {
    PulsarConsumer pulsarConsumer;
    Method handler;
    Object bean;
}
