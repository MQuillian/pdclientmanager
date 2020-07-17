package com.pdclientmanager.util.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.pdclientmanager.model.projection.ClientLightProjection;

public class CustomClientLightProjectionSerializer extends StdSerializer<ClientLightProjection> {
    
    private static final long serialVersionUID = 100L;

    public CustomClientLightProjectionSerializer() {
        this(null);
    }
 
    public CustomClientLightProjectionSerializer(Class<ClientLightProjection> t) {
        super(t);
    }
 
    @Override
    public void serialize(
      ClientLightProjection client, JsonGenerator jsonGenerator, SerializerProvider serializer)
              throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("label", client.getName());
        jsonGenerator.writeStringField("value", client.getId().toString());
        jsonGenerator.writeEndObject();
    }

}
