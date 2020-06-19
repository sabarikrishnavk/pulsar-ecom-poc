package org.springframework.integration.pulsar.consumer;

import org.springframework.integration.pulsar.collector.ConsumerCollector;
import org.springframework.integration.pulsar.collector.ConsumerHolder;
import org.apache.pulsar.client.api.*;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

@Component
@DependsOn({"pulsarClient", "consumerCollector"})
public class ConsumerBuilder {

    private final ConsumerCollector consumerCollector;
    private final PulsarClient pulsarClient;

    private List<Consumer> consumers;

    public ConsumerBuilder(ConsumerCollector consumerCollector, PulsarClient pulsarClient) {
        this.consumerCollector = consumerCollector;
        this.pulsarClient = pulsarClient;
    }

    @PostConstruct
    private void init() {
        consumers = consumerCollector.getConsumers().entrySet().stream()
            .map(holder -> subscribe(holder.getKey(), holder.getValue()))
            .collect(Collectors.toList());
    }

    private Consumer<?> subscribe(String name, ConsumerHolder holder) {
        try {
            return pulsarClient
                .newConsumer(Schema.JSON(holder.getAnnotation().clazz()))
                .consumerName("consumer-" + name)
                .subscriptionName("subscription-" + name)
                .subscriptionType(holder.getAnnotation().type())
                .topic(holder.getAnnotation().topic())
                .messageListener((consumer, msg) -> {
                    try {
                        final Method method = holder.getHandler();
                        method.setAccessible(true);
                        method.invoke(holder.getBean(), msg.getValue());
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

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }
    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }
}
