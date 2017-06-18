package App.Fund.Algorithm;

import App.Fund.CalculatedFund;
import App.Fund.FundEntity;
import App.Fund.InvalidFundsCollectionForInvestmentStyle;
import App.Fund.InvestmentStyle;
import App.Fund.Unit.InvalidValueOfPercentageRatio;
import App.Fund.Unit.Money;
import App.InvalidBasketState;

import java.util.List;
import java.util.Map;

public interface IAlgorithm {
    Map<Integer, CalculatedFund> calculate(List<FundEntity> fundEntities, Money investmentAmount, InvestmentStyle investmentStyle)
            throws InvalidFundsCollectionForInvestmentStyle, InvalidValueOfPercentageRatio, InvalidBasketState;
}
