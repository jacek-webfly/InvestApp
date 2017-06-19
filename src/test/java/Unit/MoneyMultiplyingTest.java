package Unit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class MoneyMultiplyingTest {
    private final Money money;
    private final BigDecimal multiplicand;
    private final BigDecimal expectedValue;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        //given
        return Arrays.asList(new Object[][]{
                {new Money(BigDecimal.valueOf(10), 0), BigDecimal.valueOf(-1), BigDecimal.valueOf(-10)},
                {new Money(BigDecimal.valueOf(10), 0), BigDecimal.valueOf(0), BigDecimal.valueOf(0)},
                {new Money(BigDecimal.valueOf(10), 0), BigDecimal.valueOf(1), BigDecimal.valueOf(10)},
                {new Money(BigDecimal.valueOf(10), 0), BigDecimal.valueOf(0.2), BigDecimal.valueOf(2)},
                {new Money(BigDecimal.valueOf(10), 0), BigDecimal.valueOf(0.0339), BigDecimal.valueOf(0)},
                {new Money(BigDecimal.valueOf(10), 2), BigDecimal.valueOf(0.0339), BigDecimal.valueOf(0.33)},
        });
    }

    public MoneyMultiplyingTest(Money money, BigDecimal multiplicand, BigDecimal expectedValue) {
        this.money = money;
        this.multiplicand = multiplicand;
        this.expectedValue = expectedValue;
    }

    @Test
    public void testMoneyMultiplying() {
        //given
        Ratio ratio = mock(Ratio.class);
        when(ratio.getAsMultiplicand()).thenReturn(multiplicand);

        //when
        Money result = money.multiply(ratio);

        //then
        assertEquals(expectedValue, result.getValue());
    }
}