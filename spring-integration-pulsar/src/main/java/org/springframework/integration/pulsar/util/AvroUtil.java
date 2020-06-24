package org.springframework.integration.pulsar.util;

import lombok.extern.log4j.Log4j2;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.*;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Log4j2
public class AvroUtil {

    public static <A extends GenericRecord> byte[] serializeBinary(A avropojo, Schema schema) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
            DatumWriter<A> writer = new SpecificDatumWriter<>(schema);
            writer.write(avropojo, encoder);
            encoder.flush();
            out.flush();
            return out.toByteArray();
        }
    }

    /**
     * Convert byte[] to AvroPOJO
     *
     * @param bytes
     * @return
     * @throws IOException
     */
    public static <A extends GenericRecord> A deSerializeBinary(byte[] bytes, Schema schema) throws IOException {
        Decoder decoder = DecoderFactory.get().binaryDecoder(bytes, null);
        DatumReader<A> reader = new SpecificDatumReader<>(schema);
        return reader.read(null, decoder);
    }
}
