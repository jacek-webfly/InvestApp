package Unit;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class PercentageRatioTest {
    @Test
    public void shouldFailWhenCreateObjectWithWrongValue() throws InvalidValueOfPercentageRatio {
        //given
        new PercentageRatio(BigDecimal.valueOf(1));
    }

    @Test
    public void add() throws InvalidValueOfPercentageRatio {
        //given
        PercentageRatio ratio = new PercentageRatio(BigDecimal.valueOf(10.444));
        PercentageRatio augend = new PercentageRatio(BigDecimal.valueOf(20.333));

        //when
        Ratio result = ratio.add(augend);

        //then
        assertEquals(BigDecimal.valueOf(30.77), result.getValue());
    }

    @Test
    public void divide() throws InvalidValueOfPercentageRatio {
        //given
        PercentageRatio ratio = new PercentageRatio(BigDecimal.valueOf(10));
        int divisor = 6;

        //when
        Ratio result = ratio.divide(divisor);

        //then
        assertEquals(BigDecimal.valueOf(1.66), result.getValue());
    }

    @Test
    public void multiply() throws InvalidValueOfPercentageRatio {
        //given
        PercentageRatio ratio = new PercentageRatio(BigDecimal.valueOf(1.66));
        int multiplicand = 6;

        //when
        Ratio result = ratio.multiply(multiplicand);

        //then
        assertEquals(BigDecimal.valueOf(9.96), result.getValue());
    }

    @Test
    public void subtract() throws InvalidValueOfPercentageRatio {
        //given
        PercentageRatio ratio = new PercentageRatio(BigDecimal.valueOf(1.66));
        PercentageRatio subtrahend = new PercentageRatio(BigDecimal.valueOf(0.67));

        //when
        Ratio result = ratio.subtract(subtrahend);

        //then
        assertEquals(BigDecimal.valueOf(0.99), result.getValue());
    }

    @Test
    public void getAsMultiplicand() throws InvalidValueOfPercentageRatio {
        //given
        PercentageRatio ratio = new PercentageRatio(BigDecimal.valueOf(12.349));

        //when
        BigDecimal multiplicand = ratio.getAsMultiplicand();

        //then
        assertEquals(BigDecimal.valueOf(0.1234), multiplicand);
    }

    @Test
    public void renderValue() throws InvalidValueOfPercentageRatio {
        //given
        PercentageRatio ratio = new PercentageRatio(BigDecimal.valueOf(12.349));

        //when
        String renderedValue = ratio.renderValue();

        //then
        assertEquals("12.34%", renderedValue);
    }
}