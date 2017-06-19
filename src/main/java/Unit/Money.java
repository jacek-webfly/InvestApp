package Unit;

import java.math.BigDecimal;

public class Money extends Number {
    static final int ROUNDING_MODE = BigDecimal.ROUND_DOWN;

    public Money(BigDecimal value, int scale) {
        super(value, scale, ROUNDING_MODE);
    }

    public Money multiply(Ratio ratio) {
        BigDecimal multiplicand = ratio.getAsMultiplicand();
        return new Money(this.getValue().multiply(multiplicand), this.getValue().scale());
    }
}