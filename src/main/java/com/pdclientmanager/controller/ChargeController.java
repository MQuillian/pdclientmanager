package com.pdclientmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.pdclientmanager.model.dto.ChargeDto;
import com.pdclientmanager.service.ChargeService;
import com.pdclientmanager.util.CustomChargeDtoSerializer;

@Controller
public class ChargeController {

    private ChargeService chargeService;
    
    @Autowired
    public ChargeController(ChargeService chargeService) {
        this.chargeService = chargeService;
    }
    
    @GetMapping("/charges/autocomplete_req")
    public ResponseEntity<String> chargeTest(@RequestParam("q") final String input) {
        List<ChargeDto> charges = chargeService.findByPartialNameOrStatute(input);

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = 
                new SimpleModule("CustomChargeDtoSerializer", new Version(1, 0, 0, null, null, null));
              module.addSerializer(ChargeDto.class, new CustomChargeDtoSerializer());
              mapper.registerModule(module);
        String resp = "";

        try {
            resp = mapper.writeValueAsString(charges);
            System.out.println(resp);
        } catch (JsonProcessingException e) {
            
        }
        
        return new ResponseEntity<String>(resp, HttpStatus.OK);
    }
}
