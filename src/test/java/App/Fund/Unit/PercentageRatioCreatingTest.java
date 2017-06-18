package App.Fund.Unit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class PercentageRatioCreatingTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void testFailWhenCreateObjectWithToLowValue() throws InvalidValueOfPercentageRatio {
        //expect
        thrown.expect(InvalidValueOfPercentageRatio.class);

        //when
        new PercentageRatio(BigDecimal.valueOf(-0.00001));
    }

    @Test
    public void testFailWhenCreateObjectWithToHighValue() throws InvalidValueOfPercentageRatio {
        //expect
        thrown.expect(InvalidValueOfPercentageRatio.class);

        //when
        new PercentageRatio(BigDecimal.valueOf(100.00001));
    }

    @Test
    public void testSuccessWhenCreateObjectWithCorrectLowValue() throws InvalidValueOfPercentageRatio {
        //given
        int value = 0;

        //when
        PercentageRatio percentageRatio = new PercentageRatio(BigDecimal.valueOf(value));

        //then
        assertEquals(BigDecimal.valueOf(value).setScale(2, PercentageRatio.ROUNDING_MODE), percentageRatio.getValue());
        assertEquals("0.00%", percentageRatio.renderValue());
    }

    @Test
    public void testSuccessWhenCreateObjectWithCorrectHighValue() throws InvalidValueOfPercentageRatio {
        //given
        int value = 100;

        //when
        PercentageRatio percentageRatio = new PercentageRatio(BigDecimal.valueOf(value));

        //then
        assertEquals(BigDecimal.valueOf(value).setScale(2, PercentageRatio.ROUNDING_MODE), percentageRatio.getValue());
        assertEquals("100.00%", percentageRatio.renderValue());
    }

    @Test
    public void testSuccessWhenCreateByStaticMethod() throws InvalidValueOfPercentageRatio {
        //given
        double value = 99.99;

        //when
        PercentageRatio percentageRatio = PercentageRatio.of(value);

        //then
        assertEquals(percentageRatio.getValue(), BigDecimal.valueOf(value));
    }
}