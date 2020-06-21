package org.springframework.integration.pulsar.config;

import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.client.api.schema.SchemaDefinition;
import org.apache.pulsar.client.api.schema.SchemaInfoProvider;
import org.apache.pulsar.client.impl.schema.AvroSchema;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.integration.pulsar.bean.ProducerBean;
import org.springframework.integration.pulsar.bean.PulsarSchemaType;

public  class PulsarUtil {

    public static final String CONSUMER_NAME = "consumer-";
    public static final String SUBSCRIPTION_NAME = "subscription-";


    public <T> Schema<?> getSchema(PulsarSchemaType schemaType, Class<T> clazz ) throws RuntimeException {
        if (PulsarSchemaType.JSON == schemaType) {
            return Schema.JSON(clazz);
        }else  if(PulsarSchemaType.AVRO == schemaType){
            AvroSchema<T> avroSchema = AvroSchema.of(SchemaDefinition.<T>builder()
                    .withPojo(clazz).withAlwaysAllowNull(false).build());
            return avroSchema;
        }
        throw new RuntimeException("Schema mapping not found");
    }

}
