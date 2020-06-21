package io.sellaway.cart.util;

import io.sellaway.cart.objects.Cart;
import lombok.extern.log4j.Log4j2;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
@Log4j2
public class AvroUtil {


    public Cart deSerializeJson(byte[] data) {
        DatumReader<Cart> reader = new SpecificDatumReader<>(Cart.class);
        Decoder decoder = null;
        try {
            decoder = DecoderFactory.get()
                    .jsonDecoder(Cart.getClassSchema(), new String(data));
            return reader.read(null, decoder);
        } catch (IOException e) {
            log.error("Deserialization error" + e.getMessage());
        }
        return null;
    }

    public Cart deSerializeBinary(byte[] data) {
        DatumReader<Cart> employeeReader = new SpecificDatumReader<>(Cart.class);
        Decoder decoder = DecoderFactory.get()
                .binaryDecoder(data, null);
        try {
            return employeeReader.read(null, decoder);
        } catch (IOException e) {
            log.error("Deserialization error" + e.getMessage());
        }
        return null;
    }

    public byte[] serializeJson(Cart request) {
        DatumWriter<Cart> writer = new SpecificDatumWriter<>(Cart.class);
        byte[] data = new byte[0];
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Encoder jsonEncoder = null;
        try {
            jsonEncoder = EncoderFactory.get()
                    .jsonEncoder(Cart.getClassSchema(), stream);
            writer.write(request, jsonEncoder);
            jsonEncoder.flush();
            data = stream.toByteArray();
        } catch (IOException e) {
            log.error("Serialization error " + e.getMessage());
        }
        return data;
    }

    public byte[] serializeBinary(Cart request) {
        DatumWriter<Cart> writer = new SpecificDatumWriter<>(Cart.class);
        byte[] data = new byte[0];
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Encoder jsonEncoder = EncoderFactory.get()
                .binaryEncoder(stream, null);
        try {
            writer.write(request, jsonEncoder);
            jsonEncoder.flush();
            data = stream.toByteArray();
        } catch (IOException e) {
            log.error("Serialization error " + e.getMessage());
        }

        return data;
    }
}
