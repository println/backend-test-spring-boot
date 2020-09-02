package zup.hiring.debtcollector.configuration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import zup.hiring.debtcollector.support.util.CurrencyConverters;

import java.util.ArrayList;
import java.util.List;

@Configuration

public class MongoConfiguration {

	@Bean
	public MongoCustomConversions customConversions() {
		List<Converter<?, ?>> converters = new ArrayList<>();
		converters.add(CurrencyConverters.BigDecimalToDecimal128Converter.INSTANCE);
		converters.add(CurrencyConverters.Decimal128ToBigDecimalConverter.INSTANCE);
		return new MongoCustomConversions(converters);
	}
}
