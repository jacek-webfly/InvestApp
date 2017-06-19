package Fund;

import Unit.Money;
import Unit.Ratio;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculatedFundEntityTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldFailWhenFundEntityIsNull() {
        //expect
        thrown.expect(NullPointerException.class);

        //when
        new CalculatedFund(null, mock(Money.class), mock(Ratio.class));
    }

    @Test
    public void shouldFailWhenValueIsNull() {
        //expect
        thrown.expect(NullPointerException.class);

        //when
        new CalculatedFund(mock(FundEntity.class), null, mock(Ratio.class));
    }

    @Test
    public void shouldFailWhenRatioIsNull() {
        //expect
        thrown.expect(NullPointerException.class);

        //when
        new CalculatedFund(mock(FundEntity.class), mock(Money.class), null);
    }

    @Test
    public void shouldCorrectlyCreateCalculatedFundObject() {
        //given
        String fundName = "fundName";
        FundEntity fundEntity = mock(FundEntity.class);
        when(fundEntity.getName()).thenReturn(fundName);

        String ratioValue = "100%";
        Ratio ratio = mock(Ratio.class);
        when(ratio.renderValue()).thenReturn(ratioValue);

        BigDecimal moneyValue = BigDecimal.valueOf(100);
        Money money = mock(Money.class);
        when(money.getValue()).thenReturn(moneyValue);

        //when
        CalculatedFund calculatedFund = new CalculatedFund(fundEntity, money, ratio);

        //then
        assertEquals(fundName, calculatedFund.getName());
        assertEquals(money, calculatedFund.getValue());
        assertEquals(ratioValue, calculatedFund.renderRatio());
        assertEquals("100", calculatedFund.renderValue());
    }
}