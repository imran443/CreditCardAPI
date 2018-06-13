package com.imranqureshi;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
