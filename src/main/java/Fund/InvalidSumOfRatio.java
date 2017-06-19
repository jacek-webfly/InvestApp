package Fund;

import java.math.BigDecimal;

public class InvalidSumOfRatio extends Exception {
    InvalidSumOfRatio(BigDecimal availableSumOfRatio, BigDecimal givenSumOfRatio) {
        super("Available sum of ratio is: " + availableSumOfRatio.toString() + ", but given: " + givenSumOfRatio.toString());
    }
}