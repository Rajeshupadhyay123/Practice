package com.rajesh.practice.currency_conversioin_service.controller;

import com.rajesh.practice.currency_conversioin_service.bean.CurrencyConversion;
import com.rajesh.practice.currency_conversioin_service.proxy.CurrencyExchangeProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {

    @Autowired
    private CurrencyExchangeProxy proxy;

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversioin(
            @PathVariable("from") String from,
            @PathVariable("to") String to,
            @PathVariable("quantity") BigDecimal quantity
    ){
        CurrencyConversion conversion =null;

        String url="http://localhost:8000/currency-exchange/from/{from}/to/{to}";
        RestTemplate restTemplate = new RestTemplate();

        HashMap<String,Object> paramMap = new HashMap<>();
        paramMap.put("from",from);
        paramMap.put("to",to);

//        we have 2 methods of RestTemplate 1. getForObject 2. getForEntity
//        1.getForObject:
//        returnType          : Response body (T)
//        Includes Headers    : NO
//        Includes Status Code : NO
//        Complexity           : Simpler, directly returns the response body
//        Use Case             : When only the body is needed
        //CurrencyConversion conversion = restTemplate.getForObject(url, CurrencyConversion.class, paramMap);

//        2.getForEntity:
//        returnType          : ResponseEntity<T>
//        Includes Headers    : Yes
//        Includes Status Code : Yes
//        Complexity           : More detailed, provides full HTTP response
//        Use Case             : When status code or headers are needed

        ResponseEntity<CurrencyConversion> entity = restTemplate.getForEntity(url, CurrencyConversion.class, paramMap);

        if(entity.getStatusCode()== HttpStatus.OK && entity.getStatusCodeValue()==200){
            conversion = entity.getBody();
            assert conversion != null;
            conversion.setQuantity(quantity);
            conversion.setEnvironment(conversion.getEnvironment()+" Rest");
            conversion.setTotalCalculatedAmount(quantity.multiply(conversion.getConversionMultiple()));
        }
        return conversion;
    }


    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversioinFeign(
            @PathVariable("from") String from,
            @PathVariable("to") String to,
            @PathVariable("quantity") BigDecimal quantity
    ){
        CurrencyConversion conversion =null;

         conversion = proxy.retrivrExchangeValue(from, to);

        if(conversion!=null){
            conversion.setQuantity(quantity);
            conversion.setEnvironment(conversion.getEnvironment()+" Feign");
            conversion.setTotalCalculatedAmount(quantity.multiply(conversion.getConversionMultiple()));
        }
        return conversion;
    }

    @GetMapping("/currency-conversion-test/from/{from}/to/{to}")
    public String getCurrencyConversion(@PathVariable("from") String from,
                                        @PathVariable("to") String to){



        return "From is: "+from+" to is: "+to;
    }




}
