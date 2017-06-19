import Basket.FundsBasket;
import Basket.InvalidBasketState;
import Fund.*;
import Algorithm.CalculationAlgorithm;
import Algorithm.IAlgorithm;
import Unit.*;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class BehaviorTests {
    @Test
    public void example_1() throws InvalidSumOfRatio, InvalidFundsCollectionForInvestmentStyle, InvalidValueOfPercentageRatio, InvalidBasketState {
        //given
        List<FundEntity> fundEntities = new ArrayList<>();
        fundEntities.add(new FundEntity(1, "Fundusz Polski 1", FundType.POLISH));
        fundEntities.add(new FundEntity(2, "Fundusz Polski 2", FundType.POLISH));
        fundEntities.add(new FundEntity(3, "Fundusz zagraniczny 1", FundType.FOREIGN));
        fundEntities.add(new FundEntity(4, "Fundusz zagraniczny 2", FundType.FOREIGN));
        fundEntities.add(new FundEntity(5, "Fundusz zagraniczny 3", FundType.FOREIGN));
        fundEntities.add(new FundEntity(6, "Fundusz pieniężny 1", FundType.MONEY));

        FundsBasket fundsBasket = new FundsBasket();

        //when
        for (FundEntity fundEntity : fundEntities) {
            fundsBasket.addFund(fundEntity);
        }

        fundsBasket.setInvestmentStyle(getSafeInvestmentStyle());
        fundsBasket.setInvestmentAmount(new Money(BigDecimal.valueOf(10001), 0));
        IAlgorithm calculationAlgorithm = new CalculationAlgorithm();
        fundsBasket.calculate(calculationAlgorithm);
        Map<Integer, CalculatedFund> calculatedFundsCollection = fundsBasket.getCalculatedFundsCollection();

        //then
        assertEquals("Fundusz Polski 1", calculatedFundsCollection.get(1).getName());
        assertEquals("10.00%", calculatedFundsCollection.get(1).renderRatio());
        assertEquals("1000", calculatedFundsCollection.get(1).renderValue());

        assertEquals("Fundusz Polski 2", calculatedFundsCollection.get(2).getName());
        assertEquals("10.00%", calculatedFundsCollection.get(2).renderRatio());
        assertEquals("1000", calculatedFundsCollection.get(2).renderValue());

        assertEquals("Fundusz zagraniczny 1", calculatedFundsCollection.get(3).getName());
        assertEquals("25.00%", calculatedFundsCollection.get(3).renderRatio());
        assertEquals("2500", calculatedFundsCollection.get(3).renderValue());

        assertEquals("Fundusz zagraniczny 2", calculatedFundsCollection.get(4).getName());
        assertEquals("25.00%", calculatedFundsCollection.get(4).renderRatio());
        assertEquals("2500", calculatedFundsCollection.get(4).renderValue());

        assertEquals("Fundusz zagraniczny 3", calculatedFundsCollection.get(5).getName());
        assertEquals("25.00%", calculatedFundsCollection.get(5).renderRatio());
        assertEquals("2500", calculatedFundsCollection.get(5).renderValue());

        assertEquals("Fundusz pieniężny 1", calculatedFundsCollection.get(6).getName());
        assertEquals("5.00%", calculatedFundsCollection.get(6).renderRatio());
        assertEquals("500", calculatedFundsCollection.get(6).renderValue());

        assertEquals("1", fundsBasket.getUnusedInvestmentAmount().render());
    }

    @Test
    public void example_2() throws InvalidSumOfRatio, InvalidFundsCollectionForInvestmentStyle, InvalidValueOfPercentageRatio, InvalidBasketState {
        //given
        List<FundEntity> fundEntities = new ArrayList<>();
        fundEntities.add(new FundEntity(1, "Fundusz Polski 1", FundType.POLISH));
        fundEntities.add(new FundEntity(2, "Fundusz Polski 2", FundType.POLISH));
        fundEntities.add(new FundEntity(3, "Fundusz Polski 3", FundType.POLISH));
        fundEntities.add(new FundEntity(4, "Fundusz zagraniczny 1", FundType.FOREIGN));
        fundEntities.add(new FundEntity(5, "Fundusz zagraniczny 2", FundType.FOREIGN));
        fundEntities.add(new FundEntity(6, "Fundusz pieniężny 1", FundType.MONEY));

        FundsBasket fundsBasket = new FundsBasket();

        //when
        for (FundEntity fundEntity : fundEntities) {
            fundsBasket.addFund(fundEntity);
        }

        fundsBasket.setInvestmentStyle(getSafeInvestmentStyle());
        fundsBasket.setInvestmentAmount(new Money(BigDecimal.valueOf(10000), 0));
        IAlgorithm calculationAlgorithm = new CalculationAlgorithm();
        fundsBasket.calculate(calculationAlgorithm);
        Map<Integer, CalculatedFund> calculatedFundsCollection = fundsBasket.getCalculatedFundsCollection();

        //then
        assertEquals("Fundusz Polski 1", calculatedFundsCollection.get(1).getName());
        assertEquals("6.68%", calculatedFundsCollection.get(1).renderRatio());
        assertEquals("668", calculatedFundsCollection.get(1).renderValue());

        assertEquals("Fundusz Polski 2", calculatedFundsCollection.get(2).getName());
        assertEquals("6.66%", calculatedFundsCollection.get(2).renderRatio());
        assertEquals("666", calculatedFundsCollection.get(2).renderValue());

        assertEquals("Fundusz Polski 3", calculatedFundsCollection.get(3).getName());
        assertEquals("6.66%", calculatedFundsCollection.get(3).renderRatio());
        assertEquals("666", calculatedFundsCollection.get(3).renderValue());

        assertEquals("Fundusz zagraniczny 1", calculatedFundsCollection.get(4).getName());
        assertEquals("37.50%", calculatedFundsCollection.get(4).renderRatio());
        assertEquals("3750", calculatedFundsCollection.get(4).renderValue());

        assertEquals("Fundusz zagraniczny 2", calculatedFundsCollection.get(5).getName());
        assertEquals("37.50%", calculatedFundsCollection.get(5).renderRatio());
        assertEquals("3750", calculatedFundsCollection.get(5).renderValue());

        assertEquals("Fundusz pieniężny 1", calculatedFundsCollection.get(6).getName());
        assertEquals("5.00%", calculatedFundsCollection.get(6).renderRatio());
        assertEquals("500", calculatedFundsCollection.get(6).renderValue());
    }

    @Test
    public void example_3_MoreAccurateMoneyScale() throws InvalidSumOfRatio, InvalidFundsCollectionForInvestmentStyle, InvalidValueOfPercentageRatio, InvalidBasketState {
        //given
        int scale = 2;

        List<FundEntity> fundEntities = new ArrayList<>();
        fundEntities.add(new FundEntity(1, "Fundusz Polski 1", FundType.POLISH));
        fundEntities.add(new FundEntity(2, "Fundusz Polski 2", FundType.POLISH));
        fundEntities.add(new FundEntity(3, "Fundusz Polski 3", FundType.POLISH));
        fundEntities.add(new FundEntity(4, "Fundusz zagraniczny 1", FundType.FOREIGN));
        fundEntities.add(new FundEntity(5, "Fundusz zagraniczny 2", FundType.FOREIGN));
        fundEntities.add(new FundEntity(6, "Fundusz pieniężny 1", FundType.MONEY));

        FundsBasket fundsBasket = new FundsBasket();

        //when
        for (FundEntity fundEntity : fundEntities) {
            fundsBasket.addFund(fundEntity);
        }

        fundsBasket.setInvestmentStyle(getSafeInvestmentStyle());
        fundsBasket.setInvestmentAmount(new Money(BigDecimal.valueOf(100.01), scale));
        IAlgorithm calculationAlgorithm = new CalculationAlgorithm();
        fundsBasket.calculate(calculationAlgorithm);
        Map<Integer, CalculatedFund> calculatedFundsCollection = fundsBasket.getCalculatedFundsCollection();

        //then
        assertEquals("Fundusz Polski 1", calculatedFundsCollection.get(1).getName());
        assertEquals("6.68%", calculatedFundsCollection.get(1).renderRatio());
        assertEquals("6.68", calculatedFundsCollection.get(1).renderValue());

        assertEquals("Fundusz Polski 2", calculatedFundsCollection.get(2).getName());
        assertEquals("6.66%", calculatedFundsCollection.get(2).renderRatio());
        assertEquals("6.66", calculatedFundsCollection.get(2).renderValue());

        assertEquals("Fundusz Polski 3", calculatedFundsCollection.get(3).getName());
        assertEquals("6.66%", calculatedFundsCollection.get(3).renderRatio());
        assertEquals("6.66", calculatedFundsCollection.get(3).renderValue());

        assertEquals("Fundusz zagraniczny 1", calculatedFundsCollection.get(4).getName());
        assertEquals("37.50%", calculatedFundsCollection.get(4).renderRatio());
        assertEquals("37.50", calculatedFundsCollection.get(4).renderValue());

        assertEquals("Fundusz zagraniczny 2", calculatedFundsCollection.get(5).getName());
        assertEquals("37.50%", calculatedFundsCollection.get(5).renderRatio());
        assertEquals("37.50", calculatedFundsCollection.get(5).renderValue());

        assertEquals("Fundusz pieniężny 1", calculatedFundsCollection.get(6).getName());
        assertEquals("5.00%", calculatedFundsCollection.get(6).renderRatio());
        assertEquals("5.00", calculatedFundsCollection.get(6).renderValue());

        assertEquals("0.01", fundsBasket.getUnusedInvestmentAmount().render());
    }

    private InvestmentStyle getSafeInvestmentStyle() throws InvalidSumOfRatio, InvalidValueOfPercentageRatio {
        Map<FundType, Ratio> safeFundsTypes = new HashMap<>();
        safeFundsTypes.put(FundType.POLISH, PercentageRatio.of(20));
        safeFundsTypes.put(FundType.FOREIGN, PercentageRatio.of(75));
        safeFundsTypes.put(FundType.MONEY, PercentageRatio.of(5));

        return new InvestmentStyle("Bezpieczny", safeFundsTypes);
    }
}