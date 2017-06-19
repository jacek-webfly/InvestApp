package Algorithm;

import Fund.CalculatedFund;
import Fund.FundEntity;
import Fund.InvalidFundsCollectionForInvestmentStyle;
import Fund.InvestmentStyle;
import Unit.InvalidValueOfPercentageRatio;
import Unit.Money;
import Basket.InvalidBasketState;

import java.util.List;
import java.util.Map;

public interface IAlgorithm {
    Map<Integer, CalculatedFund> calculate(List<FundEntity> fundEntities, Money investmentAmount, InvestmentStyle investmentStyle)
            throws InvalidFundsCollectionForInvestmentStyle, InvalidValueOfPercentageRatio, InvalidBasketState;
}
