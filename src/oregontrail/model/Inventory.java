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

    public Inventory() {
        // TODO (model owner): start every ItemType at 0.
    }

    /** Adds a positive amount of an item. */
    public void add(ItemType type, int amount) {
        // TODO (model owner): reject a negative amount, then add it to the current count.
    }

    /**
     * Removes an amount of an item.
     *
     * @throws InsufficientSuppliesException if there is not enough on hand
     */
    public void remove(ItemType type, int amount) throws InsufficientSuppliesException {
        // TODO (model owner):
        //   if there is less than amount on hand, throw InsufficientSuppliesException
        //   otherwise subtract amount from the current count
    }

    public int get(ItemType type) {
        // TODO (model owner)
        return 0;
    }
}
