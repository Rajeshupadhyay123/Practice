package com.rajesh.practice.currency_exchange_service.controller;

import com.rajesh.practice.currency_exchange_service.bean.CurrencyExchange;
import com.rajesh.practice.currency_exchange_service.repo.CurrencyExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {

    @Autowired
    private Environment environment;

    @Autowired
    private CurrencyExchangeRepository repository;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrivrExchangeValue(
            @PathVariable("from") String from,
            @PathVariable("to") String to
    ){
        CurrencyExchange currencyEXchange = repository.findByFromAndTo(from,to);

        if(currencyEXchange==null){
            throw new RuntimeException("Unable to Find the data");
        }
        currencyEXchange.setEnvironment(environment.getProperty("local.server.port"));
        return currencyEXchange;
    }
}
