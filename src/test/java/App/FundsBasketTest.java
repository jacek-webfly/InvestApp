package App;

import App.Fund.CalculatedFund;
import App.Fund.Algorithm.IAlgorithm;
import App.Fund.InvalidFundsCollectionForInvestmentStyle;
import App.Fund.InvestmentStyle;
import App.Fund.Unit.InvalidValueOfPercentageRatio;
import App.Fund.Unit.Money;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FundsBasketTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldFailWhenTryAddFundAsNull() throws InvalidValueOfPercentageRatio, InvalidFundsCollectionForInvestmentStyle, InvalidBasketState {
        //given
        FundsBasket fundsBasket = new FundsBasket();

        //expect
        thrown.expect(NullPointerException.class);

        //when
        fundsBasket.addFund(null);
    }

    @Test
    public void shouldFailWhenTrySetInvestmentAmountAsNull() throws InvalidValueOfPercentageRatio, InvalidFundsCollectionForInvestmentStyle, InvalidBasketState {
        //given
        FundsBasket fundsBasket = new FundsBasket();

        //expect
        thrown.expect(NullPointerException.class);

        //when
        fundsBasket.setInvestmentAmount(null);
    }

    @Test
    public void shouldFailWhenTrySetInvestmentStyleAsNull() throws InvalidValueOfPercentageRatio, InvalidFundsCollectionForInvestmentStyle, InvalidBasketState {
        //given
        FundsBasket fundsBasket = new FundsBasket();

        //expect
        thrown.expect(NullPointerException.class);

        //when
        fundsBasket.setInvestmentStyle(null);
    }

    @Test
    public void shouldFailWhenTryCalculateWithoutSetInvestmentStyle() throws InvalidValueOfPercentageRatio, InvalidFundsCollectionForInvestmentStyle, InvalidBasketState {
        //given
        FundsBasket fundsBasket = new FundsBasket();
        IAlgorithm algorithm = mock(IAlgorithm.class);
        Money money = mock(Money.class);
        when(money.getValue()).thenReturn(BigDecimal.ONE);
        fundsBasket.setInvestmentAmount(money);

        //expect
        thrown.expect(InvalidBasketState.class);

        //when
        fundsBasket.calculate(algorithm);
    }

    @Test
    public void shouldFailWhenTryCalculateWithoutSetInvestmentAmount() throws InvalidValueOfPercentageRatio, InvalidFundsCollectionForInvestmentStyle, InvalidBasketState {
        //given
        FundsBasket fundsBasket = new FundsBasket();
        IAlgorithm algorithm = mock(IAlgorithm.class);
        fundsBasket.setInvestmentStyle(mock(InvestmentStyle.class));

        //expect
        thrown.expect(InvalidBasketState.class);

        //when
        fundsBasket.calculate(algorithm);
    }

    @Test
    public void shouldSuccessCalculate() throws InvalidBasketState, InvalidFundsCollectionForInvestmentStyle, InvalidValueOfPercentageRatio {
        //given
        int valueOfCalculatedFund = 30;
        int valueOfInvestmentAmount = 100;
        FundsBasket fundsBasket = new FundsBasket();

        CalculatedFund calculatedFund = mock(CalculatedFund.class);
        Money fundMoney = mock(Money.class);
        when(fundMoney.getValue()).thenReturn(BigDecimal.valueOf(valueOfCalculatedFund));

        Map<Integer, CalculatedFund> calculatedFunds = new HashMap<>();
        calculatedFunds.put(1, calculatedFund);
        calculatedFunds.put(2, calculatedFund);
        when(calculatedFund.getValue()).thenReturn(fundMoney);

        IAlgorithm algorithm = mock(IAlgorithm.class);
        when(algorithm.calculate(any(),any(), any())).thenReturn(calculatedFunds);

        fundsBasket.setInvestmentStyle(mock(InvestmentStyle.class));

        Money investmentMoney = mock(Money.class);
        when(investmentMoney.getValue()).thenReturn(BigDecimal.valueOf(valueOfInvestmentAmount));
        fundsBasket.setInvestmentAmount(investmentMoney);

        //when
        fundsBasket.calculate(algorithm);

        //then
        assertEquals(calculatedFunds, fundsBasket.getCalculatedFundsCollection());
        assertEquals(BigDecimal.valueOf(40), fundsBasket.getUnusedInvestmentAmount().getValue());
    }
}