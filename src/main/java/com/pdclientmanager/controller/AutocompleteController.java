package com.pdclientmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pdclientmanager.model.dto.ChargeDto;
import com.pdclientmanager.model.dto.ClientDto;
import com.pdclientmanager.service.ChargeService;
import com.pdclientmanager.service.ClientService;
import com.pdclientmanager.util.json.CustomChargeDtoSerializer;
import com.pdclientmanager.util.json.CustomClientDtoSerializer;
import com.pdclientmanager.util.json.JsonUtils;

@Controller
public class AutocompleteController {

    private ClientService clientService;
    private ChargeService chargeService;
    
    @Autowired
    public AutocompleteController(ClientService clientService, ChargeService chargeService) {
        this.clientService = clientService;
        this.chargeService = chargeService;
    }
    
    @GetMapping("/autocomplete/clientsByName")
    public ResponseEntity<String> getClientsByName(@RequestParam("q") final String input) {
        List<ClientDto> clients = clientService.findByName(input);

        String resp = JsonUtils.convertToJsonString(ClientDto.class, clients,
                new CustomClientDtoSerializer());
        
        return new ResponseEntity<String>(resp, HttpStatus.OK);
    }
    
    @GetMapping("/autocomplete/chargesByNameOrStatute")
    public ResponseEntity<String> getChargesByNameOrStatute(@RequestParam("q") final String input) {
        List<ChargeDto> charges = chargeService.findByNameOrStatute(input);

        String resp = JsonUtils.convertToJsonString(ChargeDto.class, charges,
                new CustomChargeDtoSerializer());
        
        return new ResponseEntity<String>(resp, HttpStatus.OK);
    }
}
