package App.Fund;

import App.Fund.Unit.Money;
import App.Fund.Unit.Ratio;

public class CalculatedFund {
    private final Money value;
    private final Ratio ratio;
    private final FundEntity fundEntity;

    public CalculatedFund(FundEntity fundEntity, Money value, Ratio ratio) {
        this.fundEntity = fundEntity;
        this.value = value;
        this.ratio = ratio;
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