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
        // TODO (game owner):
        //   cost = item price x qty
        //   spend the money through the game (this throws if the player can't afford it)
        //   add qty of the item to the wagon's inventory
    }

    /** Shows the store menu until the player leaves. */
    public void open(Game game) {
        // TODO (game owner):
        //   loop until the player picks "leave":
        //     show each ItemType with its price and how much money is left
        //     ask which item and how many
        //     try buy(); if InsufficientFundsException, show its message and continue
    }
}
