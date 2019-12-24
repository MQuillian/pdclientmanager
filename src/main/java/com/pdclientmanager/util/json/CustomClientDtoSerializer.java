package com.pdclientmanager.util.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.pdclientmanager.model.dto.ClientDto;

public class CustomClientDtoSerializer extends StdSerializer<ClientDto> {
    
    private static final long serialVersionUID = 100L;

    public CustomClientDtoSerializer() {
        this(null);
    }
 
    public CustomClientDtoSerializer(Class<ClientDto> t) {
        super(t);
    }
 
    @Override
    public void serialize(
      ClientDto client, JsonGenerator jsonGenerator, SerializerProvider serializer)
              throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("label", client.getName());
        jsonGenerator.writeStringField("value", client.getId().toString());
        jsonGenerator.writeEndObject();
    }

}
