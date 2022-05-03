package com.pdclientmanager.util.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.pdclientmanager.model.projection.CaseLightProjection;

public class CustomCaseLightProjectionSerializer extends StdSerializer<CaseLightProjection> {
    
    private static final long serialVersionUID = 100L;

    public CustomCaseLightProjectionSerializer() {
        this(null);
    }
 
    public CustomCaseLightProjectionSerializer(Class<CaseLightProjection> t) {
        super(t);
    }
 
    @Override
    public void serialize(
      CaseLightProjection caseProjection, JsonGenerator jsonGenerator, SerializerProvider serializer)
              throws IOException {
        String custodyStatus;
        if(caseProjection.getClient().getCustodyStatus().name().contentEquals("IN_CUSTODY")) {
            custodyStatus = "In custody";
        } else {
            custodyStatus = "Out of custody";
        }
        
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("label", caseProjection.getCaseNumber() + " - " + caseProjection.getClient().getName());
        jsonGenerator.writeStringField("id", caseProjection.getId().toString());
        jsonGenerator.writeStringField("caseNumber", caseProjection.getCaseNumber());
        jsonGenerator.writeStringField("clientName", caseProjection.getClient().getName());
        jsonGenerator.writeStringField("custodyStatus", custodyStatus);
        jsonGenerator.writeStringField("attorneyName", caseProjection.getAttorney().getName());
        jsonGenerator.writeEndObject();
    }
}
