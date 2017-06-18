package App.Fund;

import App.Fund.Unit.InvalidValueOfPercentageRatio;
import App.Fund.Unit.PercentageRatio;
import App.Fund.Unit.Ratio;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InvestmentStyle {
    private final String name;
    private Map<FundType, Ratio> funds = new HashMap<>();

    public InvestmentStyle(String name, Map<FundType, Ratio> funds) throws InvalidSumOfRatio, InvalidValueOfPercentageRatio {
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.funds = Objects.requireNonNull(funds, "funds must not be null");
        validateRatioSum(funds);
    }

    private void validateRatioSum(Map<FundType, Ratio> fundTypeRatioMap) throws InvalidSumOfRatio, InvalidValueOfPercentageRatio {
        Ratio ratio = new PercentageRatio(BigDecimal.ZERO);
        for (Map.Entry<FundType, Ratio> ratioEntry : fundTypeRatioMap.entrySet()) {
            ratio = ratio.add(Objects.requireNonNull(ratioEntry.getValue(), "ratioEntry value must not be null"));
        }
        BigDecimal availableSumOfRatio = ratio.getRatioSum().setScale(ratio.getValue().scale(), BigDecimal.ROUND_DOWN);
        if (!ratio.getValue().equals(availableSumOfRatio)) {
            throw new InvalidSumOfRatio(availableSumOfRatio, ratio.getValue());
        }
    }

    public Map<FundType, Ratio> getFunds() {
        return funds;
    }
}