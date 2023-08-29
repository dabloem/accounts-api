package org.acme;

import org.javamoney.moneta.CurrencyUnitBuilder;
import org.zalando.jackson.datatype.money.MoneyModule;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.jackson.ObjectMapperCustomizer;
import jakarta.inject.Singleton;

@Singleton
public class RegisterCustomModuleCustomizer implements ObjectMapperCustomizer {

    @Override
    public void customize(ObjectMapper mapper) {
        mapper.registerModule(new MoneyModule());

        CurrencyUnitBuilder.of("BTC", "BTCCurrencyProvider")
            .setDefaultFractionDigits(8)
            .build(true);
    }

}
