package Fund;

import Unit.InvalidValueOfPercentageRatio;
import Unit.Ratio;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InvestmentStyleTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldFailWhenNameIsNull() throws InvalidValueOfPercentageRatio, InvalidSumOfRatio {
        //given
        HashMap<FundType, Ratio> funds = new HashMap<>();
        funds.put(FundType.POLISH, mock(Ratio.class));

        //expect
        thrown.expect(NullPointerException.class);

        //when
        new InvestmentStyle(null, funds);
    }

    @Test
    public void shouldFailWhenFundsIsNull() throws InvalidValueOfPercentageRatio, InvalidSumOfRatio {
        //expect
        thrown.expect(NullPointerException.class);

        //when
        new InvestmentStyle("name", null);
    }

    @Test
    public void shouldFailWhenFundsIsEmpty() throws InvalidValueOfPercentageRatio, InvalidSumOfRatio {
        //given
        HashMap<FundType, Ratio> funds = new HashMap<>();

        //expect
        thrown.expect(IllegalArgumentException.class);

        //when
        new InvestmentStyle("safe", funds);
    }

    @Test
    public void shouldFailWhenSumOfRatioIsInvalid() throws InvalidValueOfPercentageRatio, InvalidSumOfRatio {
        //given
        Ratio ratio = mock(Ratio.class);
        when(ratio.add(any())).thenReturn(ratio);
        when(ratio.getRatioSum()).thenReturn(BigDecimal.valueOf(100));
        when(ratio.getValue()).thenReturn(BigDecimal.valueOf(99));

        HashMap<FundType, Ratio> funds = new HashMap<>();
        funds.put(FundType.POLISH, ratio);

        //expect
        thrown.expect(InvalidSumOfRatio.class);

        //when
        new InvestmentStyle("safe", funds);
    }

    @Test
    public void shouldSuccessWhenSumOfRatioIsValid() throws InvalidValueOfPercentageRatio, InvalidSumOfRatio {
        //given
        Ratio ratio = mock(Ratio.class);
        BigDecimal sumRatio = BigDecimal.valueOf(100);
        when(ratio.add(any())).thenReturn(ratio);
        when(ratio.getRatioSum()).thenReturn(sumRatio);
        when(ratio.getValue()).thenReturn(sumRatio);

        HashMap<FundType, Ratio> funds = new HashMap<>();
        funds.put(FundType.POLISH, ratio);

        //when
        InvestmentStyle investmentStyle = new InvestmentStyle("safe", funds);

        //then
        assertEquals(funds, investmentStyle.getFunds());
    }
}