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
    static final String EXCEPTION_MSG_BASKET_IS_EMPTY = "There is not any fund in basket";
    static final String EXCEPTION_MSG_INVESTMENT_AMOUNT_IS_NULL = "investmentAmount must not be null";
    static final String EXCEPTION_MSG_INVESTMENT_STYLE_IS_NULL = "investmentStyle must not be null";
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
            throw new InvalidBasketState(EXCEPTION_MSG_INVESTMENT_STYLE_IS_NULL);
        }
        if (investmentAmount == null) {
            throw new InvalidBasketState(EXCEPTION_MSG_INVESTMENT_AMOUNT_IS_NULL);
        }
        if (fundEntities.isEmpty()) {
            throw new InvalidBasketState(EXCEPTION_MSG_BASKET_IS_EMPTY);
        }
    }
}