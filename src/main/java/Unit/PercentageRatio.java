package Unit;

import java.math.BigDecimal;

public class PercentageRatio extends Number implements Ratio {
    private static final int SCALE = 2;
    private static final int MULTIPLICAND_SCALE = 4;
    static final int ROUNDING_MODE = BigDecimal.ROUND_DOWN;
    private static final BigDecimal MIN_VALUE = BigDecimal.ZERO;
    private static final BigDecimal MAX_VALUE = BigDecimal.valueOf(100);
    private static final BigDecimal PERCENTAGE_TOTALITY = MAX_VALUE;

    public PercentageRatio(BigDecimal value) throws InvalidValueOfPercentageRatio {
        super(value, SCALE, ROUNDING_MODE);
        if (value.compareTo(MIN_VALUE) == -1 || value.compareTo(MAX_VALUE) == 1) {
            throw new InvalidValueOfPercentageRatio();
        }
    }

    public static PercentageRatio of(double value) throws InvalidValueOfPercentageRatio {
        return new PercentageRatio(BigDecimal.valueOf(value));
    }

    @Override
    public Ratio add(Ratio augend) throws InvalidValueOfPercentageRatio {
        return new PercentageRatio(this.getValue().add(augend.getValue()));
    }

    @Override
    public Ratio divide(int divisor) throws InvalidValueOfPercentageRatio {
        return new PercentageRatio(this.getValue().divide(BigDecimal.valueOf(divisor), ROUNDING_MODE));
    }

    @Override
    public Ratio multiply(int multiplicand) throws InvalidValueOfPercentageRatio {
        return new PercentageRatio(this.getValue().multiply(BigDecimal.valueOf(multiplicand)));
    }

    @Override
    public Ratio subtract(Ratio subtrahend) throws InvalidValueOfPercentageRatio {
        return new PercentageRatio(this.getValue().subtract(subtrahend.getValue()));
    }

    @Override
    public BigDecimal getAsMultiplicand() {
        BigDecimal percentageMultiplicand = this.getValue().setScale(MULTIPLICAND_SCALE, ROUNDING_MODE);
        return percentageMultiplicand.divide(PERCENTAGE_TOTALITY, ROUNDING_MODE);
    }

    @Override
    public BigDecimal getRatioSum() {
        return PERCENTAGE_TOTALITY;
    }

    @Override
    public String renderValue() {
        return this.render() + "%";
    }
}