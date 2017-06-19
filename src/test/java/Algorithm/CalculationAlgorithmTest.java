package Algorithm;

import Fund.*;
import Unit.*;
import Basket.InvalidBasketState;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculationAlgorithmTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldFailWhenFundEntitiesIsNull()
            throws InvalidValueOfPercentageRatio, InvalidFundsCollectionForInvestmentStyle, InvalidBasketState {
        //given
        CalculationAlgorithm calculationAlgorithm = new CalculationAlgorithm();

        //expect
        thrown.expect(NullPointerException.class);

        //when
        calculationAlgorithm.calculate(null, mock(Money.class), mock(InvestmentStyle.class));
    }

    @Test
    public void shouldFailWhenInvestmentAmountIsNull()
            throws InvalidValueOfPercentageRatio, InvalidFundsCollectionForInvestmentStyle, InvalidBasketState {
        //given
        CalculationAlgorithm calculationAlgorithm = new CalculationAlgorithm();

        //expect
        thrown.expect(NullPointerException.class);

        //when
        calculationAlgorithm.calculate(new ArrayList<>(), null, mock(InvestmentStyle.class));
    }

    @Test
    public void shouldFailWhenInvestmentStylesNull()
            throws InvalidValueOfPercentageRatio, InvalidFundsCollectionForInvestmentStyle, InvalidBasketState {
        //given
        CalculationAlgorithm calculationAlgorithm = new CalculationAlgorithm();

        //expect
        thrown.expect(NullPointerException.class);

        //when
        calculationAlgorithm.calculate(new ArrayList<>(), mock(Money.class), null);
    }

    @Test
    public void shouldFailWhenInvestmentStyleNotFitToSelectedFunds()
            throws InvalidValueOfPercentageRatio, InvalidFundsCollectionForInvestmentStyle, InvalidBasketState {
        //given
        CalculationAlgorithm calculationAlgorithm = new CalculationAlgorithm();

        java.util.List<FundEntity> fundEntities = new ArrayList<>();
        fundEntities.add(getMockFundEntityByType(FundType.POLISH));
        fundEntities.add(getMockFundEntityByType(FundType.FOREIGN));


        Map<FundType, Ratio> fundsForInvestmentStyle = new HashMap<>();
        fundsForInvestmentStyle.put(FundType.POLISH, mock(Ratio.class));
        InvestmentStyle investmentStyle = mock(InvestmentStyle.class);
        when(investmentStyle.getFunds()).thenReturn(fundsForInvestmentStyle);

        //expect
        thrown.expect(InvalidFundsCollectionForInvestmentStyle.class);

        //when
        calculationAlgorithm.calculate(fundEntities, mock(Money.class), investmentStyle);
    }

    @Test
    public void shouldSuccessCalculate()
            throws InvalidValueOfPercentageRatio, InvalidFundsCollectionForInvestmentStyle, InvalidBasketState,
            InvalidSumOfRatio {
        //given
        CalculationAlgorithm calculationAlgorithm = new CalculationAlgorithm();

        Map<FundType, Ratio> safeFundsTypes = new HashMap<>();
        safeFundsTypes.put(FundType.POLISH, PercentageRatio.of(20));
        safeFundsTypes.put(FundType.FOREIGN, PercentageRatio.of(75));
        safeFundsTypes.put(FundType.MONEY, PercentageRatio.of(5));
        InvestmentStyle investmentStyle = new InvestmentStyle("Bezpieczny", safeFundsTypes);

        Money investmentAmount = new Money(BigDecimal.valueOf(100), 0);

        List<FundEntity> fundEntities = new ArrayList<>();
        fundEntities.add(new FundEntity(1, "Fundusz Polski 1", FundType.POLISH));
        fundEntities.add(new FundEntity(2, "Fundusz zagraniczny 1", FundType.FOREIGN));
        fundEntities.add(new FundEntity(3, "Fundusz pieniężny 1", FundType.MONEY));

        //when
        Map<Integer, CalculatedFund> calculatedFunds = calculationAlgorithm.calculate(fundEntities, investmentAmount, investmentStyle);

        //then
        assertEquals("Fundusz Polski 1", calculatedFunds.get(1).getName());
        assertEquals("20", calculatedFunds.get(1).renderValue());
        assertEquals("20.00%", calculatedFunds.get(1).renderRatio());

        assertEquals("Fundusz zagraniczny 1", calculatedFunds.get(2).getName());
        assertEquals("75", calculatedFunds.get(2).renderValue());
        assertEquals("75.00%", calculatedFunds.get(2).renderRatio());

        assertEquals("Fundusz pieniężny 1", calculatedFunds.get(3).getName());
        assertEquals("5", calculatedFunds.get(3).renderValue());
        assertEquals("5.00%", calculatedFunds.get(3).renderRatio());
    }

    @Test
    public void shouldSuccessCalculateWhenRestOfRatioIsHappen()
            throws InvalidValueOfPercentageRatio, InvalidFundsCollectionForInvestmentStyle,
            InvalidBasketState, InvalidSumOfRatio {

        //given
        CalculationAlgorithm calculationAlgorithm = new CalculationAlgorithm();

        Map<FundType, Ratio> safeFundsTypes = new HashMap<>();
        safeFundsTypes.put(FundType.POLISH, PercentageRatio.of(24.95));
        safeFundsTypes.put(FundType.FOREIGN, PercentageRatio.of(75));
        safeFundsTypes.put(FundType.MONEY, PercentageRatio.of(0.05));
        InvestmentStyle investmentStyle = new InvestmentStyle("Bezpieczny", safeFundsTypes);

        Money investmentAmount = new Money(BigDecimal.valueOf(100), 2);

        List<FundEntity> fundEntities = new ArrayList<>();
        fundEntities.add(new FundEntity(1, "Fundusz Polski 1", FundType.POLISH));
        fundEntities.add(new FundEntity(2, "Fundusz zagraniczny 1", FundType.FOREIGN));
        fundEntities.add(new FundEntity(3, "Fundusz pieniężny 1", FundType.MONEY));
        fundEntities.add(new FundEntity(4, "Fundusz pieniężny 2", FundType.MONEY));

        //when
        Map<Integer, CalculatedFund> calculatedFunds = calculationAlgorithm.calculate(fundEntities, investmentAmount, investmentStyle);

        //then
        assertEquals("Fundusz Polski 1", calculatedFunds.get(1).getName());
        assertEquals("24.95", calculatedFunds.get(1).renderValue());
        assertEquals("24.95%", calculatedFunds.get(1).renderRatio());

        assertEquals("Fundusz zagraniczny 1", calculatedFunds.get(2).getName());
        assertEquals("75.00", calculatedFunds.get(2).renderValue());
        assertEquals("75.00%", calculatedFunds.get(2).renderRatio());

        assertEquals("Fundusz pieniężny 1", calculatedFunds.get(3).getName());
        assertEquals("0.03", calculatedFunds.get(3).renderValue());
        assertEquals("0.03%", calculatedFunds.get(3).renderRatio());

        assertEquals("Fundusz pieniężny 2", calculatedFunds.get(4).getName());
        assertEquals("0.02", calculatedFunds.get(4).renderValue());
        assertEquals("0.02%", calculatedFunds.get(4).renderRatio());
    }

    private FundEntity getMockFundEntityByType(FundType fundType) {
        FundEntity fundEntity = mock(FundEntity.class);
        when(fundEntity.getFundType()).thenReturn(fundType);
        return fundEntity;
    }
}