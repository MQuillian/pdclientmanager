package com.pdclientmanager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pdclientmanager.model.projection.CaseLightProjection;
import com.pdclientmanager.model.projection.ChargeProjection;
import com.pdclientmanager.model.projection.ClientLightProjection;
import com.pdclientmanager.service.CaseService;
import com.pdclientmanager.service.ChargeService;
import com.pdclientmanager.service.ClientService;
import com.pdclientmanager.util.json.CustomCaseLightProjectionSerializer;
import com.pdclientmanager.util.json.CustomChargeProjectionSerializer;
import com.pdclientmanager.util.json.CustomClientLightProjectionSerializer;
import com.pdclientmanager.util.json.JsonUtils;

@Controller
public class AutocompleteController {

    private ClientService clientService;
    private ChargeService chargeService;
    private CaseService caseService;
    
    @Autowired
    public AutocompleteController(ClientService clientService, ChargeService chargeService, CaseService caseService) {
        this.clientService = clientService;
        this.chargeService = chargeService;
        this.caseService = caseService;
    }
    
    @GetMapping("/autocomplete/clientsByName")
    public ResponseEntity<String> getClientsByName(@RequestParam("q") final String input) {
        List<ClientLightProjection> clients = clientService.findByName(input);

        String resp = JsonUtils.convertToJsonString(ClientLightProjection.class, clients,
                new CustomClientLightProjectionSerializer());
        
        return new ResponseEntity<String>(resp, HttpStatus.OK);
    }
    
    @GetMapping("/autocomplete/chargesByNameOrStatute")
    public ResponseEntity<String> getChargesByNameOrStatute(@RequestParam("q") final String input) {
        List<ChargeProjection> charges = chargeService.findByNameOrStatute(input);

        String resp = JsonUtils.convertToJsonString(ChargeProjection.class, charges,
                new CustomChargeProjectionSerializer());
        
        return new ResponseEntity<String>(resp, HttpStatus.OK);
    }
    
    @GetMapping("/autocomplete/casesByCaseNumber")
    public ResponseEntity<String> getCasesByCaseNumber(@RequestParam("q") final String input) {
        List<CaseLightProjection> cases = caseService.findAllOpenWithCaseNumber(input, CaseLightProjection.class);

        String resp = JsonUtils.convertToJsonString(CaseLightProjection.class, cases,
                new CustomCaseLightProjectionSerializer());
        
        return new ResponseEntity<String>(resp, HttpStatus.OK);
    }
}
