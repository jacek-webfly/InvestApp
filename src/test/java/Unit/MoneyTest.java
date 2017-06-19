package Unit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class MoneyTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldFailWhenValueIsNull() {
        //expect
        thrown.expect(NullPointerException.class);

        //when
        new Money(null, 1);
    }
}