package zup.hiring.debtcollector.support.util;

import com.mongodb.lang.NonNull;
import org.bson.types.Decimal128;
import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;

public class CurrencyConverters {

	private CurrencyConverters() {
	}

	public static class Decimal128ToBigDecimalConverter implements Converter<Decimal128, BigDecimal> {

		public static final Decimal128ToBigDecimalConverter INSTANCE = new Decimal128ToBigDecimalConverter();

		private Decimal128ToBigDecimalConverter() {
		}

		@Override
		public BigDecimal convert(@NonNull Decimal128 source) {
			return source.bigDecimalValue();
		}

	}

	public static class BigDecimalToDecimal128Converter implements Converter<BigDecimal, Decimal128> {

		public static final BigDecimalToDecimal128Converter INSTANCE = new BigDecimalToDecimal128Converter();

		private BigDecimalToDecimal128Converter() {
		}

		@Override
		public Decimal128 convert(@NonNull BigDecimal source) {
			return new Decimal128(source);
		}

	}

}
