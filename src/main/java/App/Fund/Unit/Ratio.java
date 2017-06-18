package App.Fund.Unit;

import java.math.BigDecimal;

public interface Ratio {
    BigDecimal getValue();

    Ratio add(Ratio augend) throws InvalidValueOfPercentageRatio;

    Ratio divide(int divisor) throws InvalidValueOfPercentageRatio;

    Ratio multiply(int multiplicand) throws InvalidValueOfPercentageRatio;

    Ratio subtract(Ratio subtrahend) throws InvalidValueOfPercentageRatio;

    BigDecimal getAsMultiplicand();

    BigDecimal getRatioSum();

    String renderValue();
}