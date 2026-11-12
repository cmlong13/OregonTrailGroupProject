package oregontrail.model;

/**
 * Supplies the player can buy. Prices are starting values to tune once we can play.
 */
public enum ItemType {
    FOOD(1, "pound", "pounds"),
    OXEN(40, "ox", "oxen"),
    CLOTHING(10, "set", "sets"),
    AMMUNITION(2, "box", "boxes"),
    SPARE_PART(10, "part", "parts");

    private final int price;
    private final String unit;
    private final String units;

    ItemType(int price, String unit, String units) {
        this.price = price;
        this.unit = unit;
        this.units = units;
    }

    public int getPrice() {
        return price;
    }

    /** @return the unit for one item, such as "ox" */
    public String getUnit() {
        return unit;
    }

    /** @return the unit for several items, such as "oxen" */
    public String getUnits() {
        return units;
    }

    /**
     * Picks the right unit for an amount, so the game can say "1 ox" and "4 oxen".
     *
     * @param amount how many items there are
     * @return the singular unit when amount is 1, otherwise the plural unit
     */
    public String unitFor(int amount) {
        if (amount == 1) {
            return unit;
        }
        return units;
    }
}
