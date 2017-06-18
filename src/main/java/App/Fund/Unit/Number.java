package App.Fund.Unit;

import java.math.BigDecimal;
import java.util.Objects;

abstract class Number {
    private final BigDecimal value;

    Number(BigDecimal value, int scale, int rounding_mode) {
        Objects.requireNonNull(value, "value must not be null");
        this.value = value.setScale(scale, rounding_mode);
    }

    public BigDecimal getValue() {
        return value;
    }

    public String render() {
        return value.toString();
    }
}