package App.Fund.Unit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class MoneyCreatingTest {

    private final BigDecimal value;
    private final int scale;
    private final BigDecimal expectedValue;
    private final String expectedRenderedValue;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        //given
        return Arrays.asList(new Object[][]{
                {BigDecimal.valueOf(0), 0, new BigDecimal(0).setScale(0, Money.ROUNDING_MODE), "0"},
                {BigDecimal.valueOf(0), 1, new BigDecimal(0).setScale(1, Money.ROUNDING_MODE), "0.0"},
                {BigDecimal.valueOf(0), 2, new BigDecimal(0).setScale(2, Money.ROUNDING_MODE), "0.00"},
                {BigDecimal.valueOf(0.01), 2, new BigDecimal(0.01).setScale(2, Money.ROUNDING_MODE), "0.01"},

                {BigDecimal.valueOf(1), 0, new BigDecimal(1).setScale(0, Money.ROUNDING_MODE), "1"},
                {BigDecimal.valueOf(1), 1, new BigDecimal(1).setScale(1, Money.ROUNDING_MODE), "1.0"},
                {BigDecimal.valueOf(1), 2, new BigDecimal(1).setScale(2, Money.ROUNDING_MODE), "1.00"},
                {BigDecimal.valueOf(0.01), 2, new BigDecimal(0.01).setScale(2, Money.ROUNDING_MODE), "0.01"},
                {BigDecimal.valueOf(0.001), 3, new BigDecimal(0.001).setScale(3, Money.ROUNDING_MODE), "0.001"},

                {BigDecimal.valueOf(-1), 0, new BigDecimal(-1).setScale(0, Money.ROUNDING_MODE), "-1"},
                {BigDecimal.valueOf(-1), 1, new BigDecimal(-1).setScale(1, Money.ROUNDING_MODE), "-1.0"},
                {BigDecimal.valueOf(-1), 2, new BigDecimal(-1).setScale(2, Money.ROUNDING_MODE), "-1.00"},
                {BigDecimal.valueOf(-0.01), 2, new BigDecimal(-0.01).setScale(2, Money.ROUNDING_MODE), "-0.01"},

                // rounding logic
                {BigDecimal.valueOf(1.9), 0, new BigDecimal(1).setScale(0, Money.ROUNDING_MODE), "1"},
                {BigDecimal.valueOf(1.09), 1, new BigDecimal(1).setScale(1, Money.ROUNDING_MODE), "1.0"},
                {BigDecimal.valueOf(1.109), 2, new BigDecimal(1.1).setScale(2, Money.ROUNDING_MODE), "1.10"},
        });
    }

    public MoneyCreatingTest(BigDecimal value, int scale, BigDecimal expectedValue, String expectedRenderedValue) {
        this.value = value;
        this.scale = scale;
        this.expectedValue = expectedValue;
        this.expectedRenderedValue = expectedRenderedValue;
    }

    @Test
    public void testMoneyCreating() {
        //when
        Money money = new Money(value, scale);

        //then
        assertEquals(expectedValue, money.getValue());
        assertEquals(expectedRenderedValue, money.render());
    }
}