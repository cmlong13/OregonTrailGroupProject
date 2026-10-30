package oregontrail.model;

import oregontrail.Game;
import oregontrail.exceptions.InsufficientFundsException;

/**
 * Sells supplies. Used at the start of the game and at forts.
 */
public class Store {

    /**
     * Buys an item and adds it to the wagon.
     *
     * @param type the item
     * @param qty  how many, must be positive
     * @param game the game, for money and the wagon
     * @throws InsufficientFundsException if the player cannot afford it
     */
    public void buy(ItemType type, int qty, Game game) throws InsufficientFundsException {
        game.spendMoney(type.getPrice() * qty);
        game.getWagon().getInventory().add(type, qty);
    }

    /** Shows the store menu until the player leaves. */
    public void open(Game game) {
        // TODO (game owner): menu of items with prices, ask for quantity, catch InsufficientFundsException.
    }
}
