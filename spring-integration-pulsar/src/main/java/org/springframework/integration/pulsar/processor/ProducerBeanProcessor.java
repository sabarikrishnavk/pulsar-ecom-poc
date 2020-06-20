package org.springframework.integration.pulsar.processor;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.integration.pulsar.annotation.PulsarProducer;
import org.springframework.integration.pulsar.bean.ProducerBean;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.integration.pulsar.config.PulsarUtil;
import org.springframework.integration.pulsar.registry.ProducerTopicRegistry;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@Log4j2
public class ProducerBeanProcessor  extends PulsarUtil implements BeanPostProcessor {

    private final PulsarClient pulsarClient;
    public Map<String, Producer> getProducers() {
        return producers;
    }

    private Map<String, Producer> producers = new ConcurrentHashMap<>();

    public ProducerBeanProcessor(PulsarClient pulsarClient) {
        this.pulsarClient = pulsarClient;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        final Class<?> beanClass = bean.getClass();

        if(beanClass.isAnnotationPresent(PulsarProducer.class) && bean instanceof ProducerTopicRegistry) {
            producers.putAll(((ProducerTopicRegistry) bean).getTopics().entrySet().stream()
                .map($ -> new ProducerBean($.getKey(), $.getValue().left, $.getValue().right))
                .collect(Collectors.toMap(ProducerBean::getTopic, this::buildProducer)));
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }

    private Producer<?> buildProducer(ProducerBean producerBean) {
        try {
            return pulsarClient.newProducer(getSchema(producerBean.getSchema(),producerBean.getClazz()))
                .topic(producerBean.getTopic())
                .create();
        } catch(PulsarClientException e) {
            log.error("Producer build failed ");
            throw new RuntimeException("Producer build failed ", e);
        }
    }


}
