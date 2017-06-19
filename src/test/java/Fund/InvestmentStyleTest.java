package Fund;

import Fund.FundType;
import Fund.InvalidSumOfRatio;
import Fund.InvestmentStyle;
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
    public void shouldSuccessCreateObject() throws InvalidValueOfPercentageRatio, InvalidSumOfRatio {
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

    @Test
    public void shouldFailWhenCreateObjectWithWrongFundTypesRatio() throws InvalidValueOfPercentageRatio, InvalidSumOfRatio {
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
}