package oregontrail.exceptions;

/**
 * Thrown when the wagon does not have enough of an item.
 * Checked, so every caller has to handle it.
 */
public class InsufficientSuppliesException extends Exception {

    public InsufficientSuppliesException(String message) {
        super(message);
    }
}
