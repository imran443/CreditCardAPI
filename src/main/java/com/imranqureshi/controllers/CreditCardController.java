package com.imranqureshi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imranqureshi.pojos.CreditCard;
import com.imranqureshi.pojos.CreditCardResponse;
import com.imranqureshi.pojos.BlackList;
import com.imranqureshi.services.CreditCardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CreditCardController {
    // Store the black list
    String blackListJSON = "{\"blacklist\":[ \"4788 3845 3855 2446\", \"5144 3854 3852 3845\" , \"4512 2386 2090 0208\" ]}";

    // This class will hold create a object from the Blacklist JSON
    BlackList blackList;

    // The service class which holds all of the credit card validation
    CreditCardService creditCardService = new CreditCardService();

    @RequestMapping(value = "/validatecard", method = RequestMethod.POST)
    public ResponseEntity<List<CreditCardResponse>> validate(@RequestBody List<CreditCard> body) {

        List<CreditCardResponse> newBody = null;
        // Convert the JSON for the black list to a Java object
        try {
            ObjectMapper mapper = new ObjectMapper();
            blackList = mapper.readValue(blackListJSON, BlackList.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        newBody = body.stream()
                .map(card -> creditCardService.validate(card, blackList))
                .collect(Collectors.toList());

        return new ResponseEntity<>(newBody, HttpStatus.OK);
    }

}
