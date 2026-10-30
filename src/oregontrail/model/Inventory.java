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
        for (ItemType type : ItemType.values()) {
            items.put(type, 0);
        }
    }

    /** Adds a positive amount of an item. */
    public void add(ItemType type, int amount) {
        // TODO (model owner): reject negative amounts.
        items.merge(type, amount, Integer::sum);
    }

    /**
     * Removes an amount of an item.
     *
     * @throws InsufficientSuppliesException if there is not enough on hand
     */
    public void remove(ItemType type, int amount) throws InsufficientSuppliesException {
        // TODO (model owner): throw InsufficientSuppliesException when get(type) < amount.
        items.put(type, items.get(type) - amount);
    }

    public int get(ItemType type) {
        return items.get(type);
    }
}
