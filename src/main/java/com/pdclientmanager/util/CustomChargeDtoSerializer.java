package com.pdclientmanager.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.pdclientmanager.model.dto.ChargeDto;

public class CustomChargeDtoSerializer extends StdSerializer<ChargeDto> {

    private static final long serialVersionUID = 100L;

    public CustomChargeDtoSerializer() {
        this(null);
    }
 
    public CustomChargeDtoSerializer(Class<ChargeDto> t) {
        super(t);
    }
 
    @Override
    public void serialize(
      ChargeDto charge, JsonGenerator jsonGenerator, SerializerProvider serializer)
              throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("label", charge.getStatute() + " - " + charge.getName());
        jsonGenerator.writeStringField("value", charge.getId().toString());
        jsonGenerator.writeEndObject();
    }
}
