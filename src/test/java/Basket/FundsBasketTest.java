package Basket;

import Fund.CalculatedFund;
import Algorithm.IAlgorithm;
import Fund.FundEntity;
import Fund.InvalidFundsCollectionForInvestmentStyle;
import Fund.InvestmentStyle;
import Unit.InvalidValueOfPercentageRatio;
import Unit.Money;
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
    public void shouldFailWhenTryCalculateAndBasketIsEmpty() throws InvalidValueOfPercentageRatio, InvalidFundsCollectionForInvestmentStyle, InvalidBasketState {
        //given
        FundsBasket fundsBasket = new FundsBasket();
        IAlgorithm algorithm = mock(IAlgorithm.class);

        Money money = mock(Money.class);
        when(money.getValue()).thenReturn(BigDecimal.ONE);
        fundsBasket.setInvestmentAmount(money);

        fundsBasket.setInvestmentStyle(mock(InvestmentStyle.class));

        //expect
        thrown.expect(InvalidBasketState.class);
        thrown.expectMessage(FundsBasket.EXCEPTION_MSG_BASKET_IS_EMPTY);

        //when
        fundsBasket.calculate(algorithm);
    }

    @Test
    public void shouldFailWhenTryCalculateWithoutSetInvestmentStyle() throws InvalidValueOfPercentageRatio, InvalidFundsCollectionForInvestmentStyle, InvalidBasketState {
        //given
        FundsBasket fundsBasket = new FundsBasket();
        IAlgorithm algorithm = mock(IAlgorithm.class);

        Money money = mock(Money.class);
        when(money.getValue()).thenReturn(BigDecimal.ONE);
        fundsBasket.setInvestmentAmount(money);

        fundsBasket.addFund(mock(FundEntity.class));

        //expect
        thrown.expect(InvalidBasketState.class);
        thrown.expectMessage(FundsBasket.EXCEPTION_MSG_INVESTMENT_STYLE_IS_NULL);

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
        thrown.expectMessage(FundsBasket.EXCEPTION_MSG_INVESTMENT_AMOUNT_IS_NULL);

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
        when(calculatedFund.getValue()).thenReturn(fundMoney);

        Map<Integer, CalculatedFund> calculatedFunds = new HashMap<>();
        calculatedFunds.put(1, calculatedFund);
        calculatedFunds.put(2, calculatedFund);

        IAlgorithm algorithm = mock(IAlgorithm.class);
        when(algorithm.calculate(any(), any(), any())).thenReturn(calculatedFunds);

        fundsBasket.setInvestmentStyle(mock(InvestmentStyle.class));

        Money investmentMoney = mock(Money.class);
        when(investmentMoney.getValue()).thenReturn(BigDecimal.valueOf(valueOfInvestmentAmount));
        fundsBasket.setInvestmentAmount(investmentMoney);

        fundsBasket.addFund(mock(FundEntity.class));

        //when
        fundsBasket.calculate(algorithm);

        //then
        assertEquals(calculatedFunds, fundsBasket.getCalculatedFundsCollection());
        assertEquals(BigDecimal.valueOf(40), fundsBasket.getUnusedInvestmentAmount().getValue());
    }
}