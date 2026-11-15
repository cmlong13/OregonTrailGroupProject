package oregontrail.model;

import oregontrail.exceptions.InsufficientSuppliesException;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class Inventory {

    // Stores the amount of each item the player owns.
    private final Map<ItemType, Integer> items =
            new EnumMap<>(ItemType.class);

    // Starts every item with a quantity of zero.
    public Inventory() {
        for (ItemType type : ItemType.values()) {
            items.put(type, 0);
        }
    }

    /**
     * Adds a positive amount of an item.
     */
    public void add(ItemType type, int amount) {
        Objects.requireNonNull(type, "Item type cannot be null");

        if (amount < 0) {
            throw new IllegalArgumentException(
                    "Amount cannot be negative"
            );
        }

        items.put(type, Math.addExact(items.get(type), amount));
    }

    // Removes supplies if the player has enough.
    public void remove(ItemType type, int amount)
            throws InsufficientSuppliesException {

        Objects.requireNonNull(type, "Item type cannot be null");

        if (amount < 0) {
            throw new IllegalArgumentException(
                    "Amount cannot be negative"
            );
        }

        int have = items.get(type);

        if (have < amount) {
            throw new InsufficientSuppliesException(
                    "Not enough " + type + ": need "
                            + amount + " but have " + have
            );
        }

        items.put(type, have - amount);
    }

    // Removes whatever amount is available without going below zero.
    public int removeUpTo(ItemType type, int amount) {
        Objects.requireNonNull(type, "Item type cannot be null");

        if (amount < 0) {
            throw new IllegalArgumentException(
                    "Amount cannot be negative"
            );
        }

        int taken = Math.min(amount, items.get(type));

        items.put(type, items.get(type) - taken);

        return taken;
    }

    // Returns how many of a certain item the player has.
    public int get(ItemType type) {
        Objects.requireNonNull(type, "Item type cannot be null");

        return items.get(type);
    }
}
