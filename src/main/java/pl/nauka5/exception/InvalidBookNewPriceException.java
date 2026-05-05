package pl.nauka5.exception;

import java.math.BigDecimal;

public class InvalidBookNewPriceException extends RuntimeException {
    public InvalidBookNewPriceException(BigDecimal newPricePerDay) {
        super("Invalid new price per day: " + newPricePerDay + "PLN. Lowest new price can be 0.00 PLN");
    }
}
