package Fund;

import org.junit.Test;

import static org.junit.Assert.*;

public class FundEntityTest {
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