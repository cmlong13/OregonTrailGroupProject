
package oregontrail.model;

// These are the different supplies available in the game.
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

    // Returns the price of the item.
    public int getPrice() {
        return price;
    }

    // Returns how the item is measured.
    public String getUnit() {
        return unit;
    }
}

