package com.rajesh.practice.currency_exchange_service.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("currency-conversion")
public interface CurrencyConversionProxy {
    @GetMapping("/currency-conversion-test/from/{from}/to/{to}")
    public String getCurrencyConversion(@PathVariable("from") String from,
                                        @PathVariable("to") String to);
}
