package oregontrail.model;

import oregontrail.exceptions.InsufficientSuppliesException;

import java.util.EnumMap;
import java.util.Map;

/**
 * Supplies on hand. EnumMap is used because the keys are a fixed enum:
 * it is faster and smaller than a HashMap for this case.
 */
public class Inventory {

    private final Map<ItemType, Integer> items = new EnumMap<>(ItemType.class);

    /** Creates an inventory with every item at 0. */
    public Inventory() {
        for (ItemType type : ItemType.values()) {
            items.put(type, 0);
        }
    }

    /**
     * Adds a positive amount of an item.
     *
     * @throws IllegalArgumentException if amount is negative
     */
    public void add(ItemType type, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot add a negative amount: " + amount);
        }
        items.put(type, items.get(type) + amount);
    }

    /**
     * Removes an amount of an item.
     *
     * @throws InsufficientSuppliesException if there is not enough on hand
     * @throws IllegalArgumentException if amount is negative
     */
    public void remove(ItemType type, int amount) throws InsufficientSuppliesException {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot remove a negative amount: " + amount);
        }
        int have = items.get(type);
        if (have < amount) {
            throw new InsufficientSuppliesException(
                    "Not enough " + type + ": need " + amount + " but have " + have + ".");
        }
        items.put(type, have - amount);
    }

    /**
     * Removes up to amount of an item, never going below 0. Handy for events
     * (like a thief) that take "as much as possible" and must not fail.
     *
     * @return how much was actually removed
     */
    public int removeUpTo(ItemType type, int amount) {
        int taken = Math.min(Math.max(amount, 0), items.get(type));
        items.put(type, items.get(type) - taken);
        return taken;
    }

    /** @return how many of this item are on hand */
    public int get(ItemType type) {
        return items.get(type);
    }
}
