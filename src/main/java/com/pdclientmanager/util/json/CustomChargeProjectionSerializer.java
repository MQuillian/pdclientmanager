package com.pdclientmanager.util.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.pdclientmanager.model.projection.ChargeProjection;

public class CustomChargeProjectionSerializer extends StdSerializer<ChargeProjection> {

    private static final long serialVersionUID = 100L;

    public CustomChargeProjectionSerializer() {
        this(null);
    }
 
    public CustomChargeProjectionSerializer(Class<ChargeProjection> t) {
        super(t);
    }
 
    @Override
    public void serialize(
      ChargeProjection charge, JsonGenerator jsonGenerator, SerializerProvider serializer)
              throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("label", charge.toString());
        jsonGenerator.writeStringField("value", charge.getId().toString());
        jsonGenerator.writeEndObject();
    }
}
