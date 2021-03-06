package org.springframework.integration.pulsar.processor;

import org.springframework.integration.pulsar.annotation.PulsarConsumer;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.pulsar.bean.ConsumerBean;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Configuration
public class ConsumerBeanProcessor implements BeanPostProcessor {

    private Map<String, ConsumerBean> consumers = new ConcurrentHashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        final Class<?> beanClass = bean.getClass();

        consumers.putAll(Arrays.stream(beanClass.getDeclaredMethods())
            .filter($ -> $.isAnnotationPresent(PulsarConsumer.class))
            .collect(Collectors.toMap(
                method -> beanClass.getName() + "#" + method.getName(),
                method -> new ConsumerBean(method.getAnnotation(PulsarConsumer.class), method, bean))));
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }

    public Map<String, ConsumerBean> getConsumers() {
        return consumers;
    }

    public Optional<ConsumerBean> getConsumer(String methodDescriptor) {
        return Optional.ofNullable(consumers.get(methodDescriptor));
    }
}
