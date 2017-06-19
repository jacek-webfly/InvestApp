package Algorithm;

import Fund.*;
import Unit.InvalidValueOfPercentageRatio;
import Unit.Money;
import Unit.Ratio;
import Basket.InvalidBasketState;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculationAlgorithm implements IAlgorithm {

    public Map<Integer, CalculatedFund> calculate(List<FundEntity> fundEntities, Money investmentAmount, InvestmentStyle investmentStyle)
            throws InvalidFundsCollectionForInvestmentStyle, InvalidValueOfPercentageRatio, InvalidBasketState {

        Map<FundType, Integer> quantityFundTypesInBasket = getQuantityFundTypes(fundEntities);

        validateInvestmentStyleForFundTypes(investmentStyle, quantityFundTypesInBasket);

        Map<FundType, Ratio> ratios = new HashMap<>();
        Map<FundType, Ratio> restsOfRatioDivision = new HashMap<>();
        for (FundType entry : quantityFundTypesInBasket.keySet()) {
            Ratio dividend = investmentStyle.getFunds().get(entry);
            int divisor = quantityFundTypesInBasket.get(entry);
            Ratio ratio = dividend.divide(divisor);
            Ratio restOfDivision = dividend.subtract(ratio.multiply(divisor));

            ratios.put(entry, ratio);
            restsOfRatioDivision.put(entry, restOfDivision.getValue().compareTo(BigDecimal.ZERO) != 0 ? restOfDivision : null);
        }

        return getIntegerCalculatedFundMap(fundEntities, investmentAmount, ratios, restsOfRatioDivision);
    }

    private Map<Integer, CalculatedFund> getIntegerCalculatedFundMap(
            List<FundEntity> fundEntities, Money investmentAmount,
            Map<FundType, Ratio> ratios, Map<FundType, Ratio> restsOfRatioDivision) throws InvalidValueOfPercentageRatio {

        Map<Integer, CalculatedFund> calculatedFundsCollection = new HashMap<>();
        for (FundEntity fundEntity : fundEntities) {
            Ratio ratio = ratios.get(fundEntity.getFundType());
            if (restsOfRatioDivision.get(fundEntity.getFundType()) != null) {
                ratio = ratio.add(restsOfRatioDivision.get(fundEntity.getFundType()));
                restsOfRatioDivision.put(fundEntity.getFundType(), null);
            }

            Money value = investmentAmount.multiply(ratio);
            calculatedFundsCollection.put(fundEntity.getId(), new CalculatedFund(fundEntity, value, ratio));
        }
        return calculatedFundsCollection;
    }

    private Map<FundType, Integer> getQuantityFundTypes(List<FundEntity> fundEntities) {
        Map<FundType, Integer> quantityFundTypesInBasket = new HashMap<>();
        for (FundEntity fundEntity : fundEntities) {
            quantityFundTypesInBasket.merge(fundEntity.getFundType(), 1, (a, b) -> a + b);
        }

        return quantityFundTypesInBasket;
    }

    private void validateInvestmentStyleForFundTypes(
            InvestmentStyle investmentStyle, Map<FundType, Integer> quantityFundTypesInBasket)
            throws InvalidFundsCollectionForInvestmentStyle {

        if (!investmentStyle.getFunds().keySet().equals(quantityFundTypesInBasket.keySet())) {
            throw new InvalidFundsCollectionForInvestmentStyle();
        }
    }
}
