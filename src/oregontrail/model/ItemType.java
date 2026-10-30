package oregontrail.model;

/**
 * Supplies the player can buy. Prices are starting values to tune once we can play.
 */
public enum ItemType {
    FOOD(1, "pound"),
    OXEN(40, "ox"),
    CLOTHING(10, "set"),
    AMMUNITION(2, "box"),
    SPARE_PART(10, "part");

    private final int price;
    private final String unit;

    ItemType(int price, String unit) {
        this.price = price;
        this.unit = unit;
    }

    public int getPrice() {
        return price;
    }

    public String getUnit() {
        return unit;
    }
}
