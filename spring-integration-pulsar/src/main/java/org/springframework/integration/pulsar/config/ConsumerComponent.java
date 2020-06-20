package org.springframework.integration.pulsar.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.pulsar.processor.ConsumerBeanProcessor;
import org.springframework.integration.pulsar.bean.ConsumerBean;
import org.apache.pulsar.client.api.*;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Log4j2
public class ConsumerComponent extends PulsarUtil {

    @Autowired
    private final ConsumerBeanProcessor consumerBeanProcessor;
    @Autowired
    private final PulsarClient pulsarClient;

    private List<Consumer> consumers;

    public ConsumerComponent(ConsumerBeanProcessor consumerBeanProcessor, PulsarClient pulsarClient) {
        this.consumerBeanProcessor = consumerBeanProcessor;
        this.pulsarClient = pulsarClient;
    }

    @PostConstruct
    private void init() {
        consumers = consumerBeanProcessor.getConsumers().entrySet().stream()
            .map(holder -> subscribe(holder.getKey(), holder.getValue()))
            .collect(Collectors.toList());
    }

    private Consumer<?> subscribe(String name, ConsumerBean consumerBean) {
        try {
            return pulsarClient
                .newConsumer(getSchema(consumerBean.getPulsarConsumer().schemaType(),consumerBean.getPulsarConsumer().clazz()))
                .consumerName(CONSUMER_NAME + name)
                .subscriptionName(SUBSCRIPTION_NAME + name)
                .subscriptionType(consumerBean.getPulsarConsumer().type())
                .topic(consumerBean.getPulsarConsumer().topic())
                .messageListener((consumer, msg) -> {
                    try {
                        final Method method = consumerBean.getHandler();
                        method.setAccessible(true);
                        method.invoke(consumerBean.getBean(), msg.getValue());
                        consumer.acknowledge(msg);
                    } catch (Exception e) {
                        consumer.negativeAcknowledge(msg);
                        throw new RuntimeException("TODO Custom Exception!", e);
                    }
                }).subscribe();
        } catch (PulsarClientException e) {
            throw new RuntimeException("TODO Custom Exception!", e);
        }
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

}
