package com.pdclientmanager.util.json;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class JsonUtils {
    
    public static <T> String convertToJsonString(Class<T> targetClass, 
            List<T> resultsList, StdSerializer<T> customSerializer) {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = 
                new SimpleModule(customSerializer.getClass().getName(), new Version(1, 0, 0, null, null, null));
              module.addSerializer(targetClass, customSerializer);
              mapper.registerModule(module);
        String resp = "";

        try {
            resp = mapper.writeValueAsString(resultsList);
        } catch (JsonProcessingException e) {
            
        }
        
        return resp;
    }

}
