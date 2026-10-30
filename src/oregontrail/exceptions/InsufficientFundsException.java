package oregontrail.exceptions;

/**
 * Thrown when the player tries to spend more money than they have.
 * Checked, so every caller has to handle it.
 */
public class InsufficientFundsException extends Exception {

    public InsufficientFundsException(int cost, int available) {
        super("That costs $" + cost + " but you only have $" + available + ".");
    }
}
