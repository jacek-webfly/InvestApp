package Basket;

import Algorithm.IAlgorithm;
import Fund.CalculatedFund;
import Fund.FundEntity;
import Unit.InvalidValueOfPercentageRatio;
import Unit.Money;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FundsBasket {
    private final List<FundEntity> fundEntities = new ArrayList<>();
    private Fund.InvestmentStyle investmentStyle;
    private Money investmentAmount;
    private Money unusedInvestmentAmount;
    private Map<Integer, CalculatedFund> calculatedFundsCollection;
    private int scale = 0;

    public void addFund(Fund.FundEntity fundEntity) {
        fundEntities.add(Objects.requireNonNull(fundEntity, "fundEntity must not be null"));
    }

    public void setInvestmentStyle(Fund.InvestmentStyle investmentStyle) {
        this.investmentStyle = Objects.requireNonNull(investmentStyle, "investmentStyle must not be null");
    }

    public void setInvestmentAmount(Money investmentAmount) {
        Objects.requireNonNull(investmentAmount, "investmentAmount must not be null");
        scale = investmentAmount.getValue().scale();
        this.investmentAmount = new Money(investmentAmount.getValue(), scale);
    }

    public Map<Integer, Fund.CalculatedFund> getCalculatedFundsCollection() {
        return calculatedFundsCollection;
    }

    public Money getUnusedInvestmentAmount() {
        return unusedInvestmentAmount;
    }

    public void calculate(IAlgorithm calculationAlgorithm) throws Fund.InvalidFundsCollectionForInvestmentStyle, InvalidValueOfPercentageRatio, InvalidBasketState {
        Objects.requireNonNull(calculationAlgorithm, "calculationAlgorithm must not be null");
        validateBasketStateBeforeCalculation();

        calculatedFundsCollection = calculationAlgorithm.calculate(fundEntities, investmentAmount, investmentStyle);
        BigDecimal sumValueOfCalculatedFunds = BigDecimal.ZERO;
        for (int fundId : calculatedFundsCollection.keySet()) {
            sumValueOfCalculatedFunds = sumValueOfCalculatedFunds.add(calculatedFundsCollection.get(fundId).getValue().getValue());
        }
        BigDecimal unusedInvestmentAmount = investmentAmount.getValue().subtract(sumValueOfCalculatedFunds);
        this.unusedInvestmentAmount = new Money(unusedInvestmentAmount, scale);
    }

    private void validateBasketStateBeforeCalculation() throws InvalidBasketState {
        if (investmentStyle == null) {
            throw new InvalidBasketState("investmentStyle must not be null");
        }
        if (investmentAmount == null) {
            throw new InvalidBasketState("investmentAmount must not be null");
        }
    }
}