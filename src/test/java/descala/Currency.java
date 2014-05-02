package descala;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Currency {

    public BigDecimal from(BigDecimal value) {
        return new BigDecimal(value.toString()).setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal fromStatic(BigDecimal value) {
        return new BigDecimal(value.toString()).setScale(2, RoundingMode.HALF_UP);
    }
}
