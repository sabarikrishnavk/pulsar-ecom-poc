import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.impl.schema.AvroSchema;

import java.util.HashMap;
import java.util.Map;

public class PostMessage {
    public static void main(String[] args) throws PulsarClientException {
        PulsarClient client = PulsarClient.builder()
                .serviceUrl("pulsar://localhost:6650")
                .build();

        Map<String,String> updateMap = new HashMap<>();
        updateMap.put("ACTION","UPDATE");

        Producer<Test> updateProducer = client.newProducer(AvroSchema.of(Test.class))
                .topic("pulsar-postgres-jdbc-sink-topic")
                .properties(updateMap)
                .create();
        updateProducer.send(new Test( 3,"test-3"));

        updateProducer.close();
        client.close();
    }
}
