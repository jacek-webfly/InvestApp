package Fund;

import Unit.Money;
import Unit.Ratio;

import java.util.Objects;

public class CalculatedFund {
    private final Money value;
    private final Ratio ratio;
    private final FundEntity fundEntity;

    public CalculatedFund(FundEntity fundEntity, Money value, Ratio ratio) {
        this.fundEntity = Objects.requireNonNull(fundEntity, "fundEntity must not be null");
        this.value = Objects.requireNonNull(value, "value must not be null");
        this.ratio = Objects.requireNonNull(ratio, "ratio must not be null");
    }

    public String getName() {
        return fundEntity.getName();
    }

    public Money getValue() {
        return value;
    }

    public String renderValue() {
        return value.getValue().toString();
    }

    public String renderRatio() {
        return ratio.renderValue();
    }
}