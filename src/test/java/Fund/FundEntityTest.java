package Fund;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class FundEntityTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldFailWhenNameIsNull() {
        //expect
        thrown.expect(NullPointerException.class);

        //when
        new FundEntity(1, null, FundType.FOREIGN);
    }

    @Test
    public void shouldFailWhenFundTypeIsNull() {
        //expect
        thrown.expect(NullPointerException.class);

        //when
        new FundEntity(1, "name", null);
    }

    @Test
    public void shouldCorrectlyCreateFundObject() {
        //given
        int id = 1;
        String fundName = "FundName";
        FundType fundType = FundType.POLISH;

        //when
        FundEntity fundEntity = new FundEntity(id, fundName, fundType);

        //then
        assertEquals(id, fundEntity.getId());
        assertEquals(fundName, fundEntity.getName());
        assertEquals(fundType, fundEntity.getFundType());
    }
}