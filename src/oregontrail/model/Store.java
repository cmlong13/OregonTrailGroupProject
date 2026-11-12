package oregontrail.model;

import oregontrail.Game;
import oregontrail.exceptions.InsufficientFundsException;
import oregontrail.ui.ConsoleUI;

/**
 * Sells supplies. Used at the start of the game and at forts.
 */
public class Store {

    private static final String LEAVE = "Leave the store";

    /** Column titles, lined up with the menu options below them. */
    private static final String COLUMNS = "     Item          Price           In your wagon";

    private final String name;

    /**
     * Creates a store.
     *
     * @param name the store's name, shown as the heading of its screen
     */
    public Store(String name) {
        this.name = name;
    }

    /**
     * Buys an item and adds it to the wagon.
     *
     * @param type the item
     * @param qty  how many, must be positive
     * @param game the game, for money and the wagon
     * @throws InsufficientFundsException if the player cannot afford it
     * @throws IllegalArgumentException   if qty is 0 or less
     */
    public void buy(ItemType type, int qty, Game game) throws InsufficientFundsException {
        if (qty <= 0) {
            throw new IllegalArgumentException("Quantity must be positive: " + qty);
        }
        // Pay first: if spendMoney throws, this method stops here and
        // nothing is added to the wagon.
        game.spendMoney(type.getPrice() * qty);
        game.getWagon().getInventory().add(type, qty);
    }

    /**
     * Shows the store menu until the player leaves. The player can't leave
     * without at least one ox, because nothing could pull the wagon.
     *
     * @param game the game, for money, the wagon and the screen
     */
    public void open(Game game) {
        ConsoleUI ui = game.getUi();
        Inventory inventory = game.getWagon().getInventory();
        // values() gives every constant of an enum, in the order they're written.
        ItemType[] types = ItemType.values();

        while (true) {
            ui.header(name.toUpperCase());
            int foodPerDay = game.getWagon().getRations().getPoundsPerPerson()
                    * game.getParty().getAlive().size();
            ui.print("Money: $" + game.getMoney()
                    + "     Your party eats about " + foodPerDay + " pounds of food a day.");

            // One option per item, plus "Leave the store" as the last one.
            String[] options = new String[types.length + 1];
            for (int i = 0; i < types.length; i++) {
                ItemType type = types[i];
                int count = inventory.get(type);
                options[i] = ConsoleUI.fit(ConsoleUI.titleCase(type.name()), 14)
                        + ConsoleUI.fit("$" + type.getPrice() + " per " + type.getUnit(), 16)
                        + count + " " + type.unitFor(count);
            }
            options[types.length] = LEAVE;

            int choice = ui.showMenu(COLUMNS, options);

            if (choice == options.length) {
                if (inventory.get(ItemType.OXEN) == 0) {
                    ui.print("You can't leave without at least one ox to pull the wagon!");
                    ui.pause();
                    continue;
                }
                return;
            }

            ItemType type = types[choice - 1];
            int qty = ui.readInt("How many " + type.getUnits() + "?", 0, 999);
            if (qty == 0) {
                continue;
            }
            try {
                buy(type, qty, game);
                ui.print("Bought " + ConsoleUI.titleCase(type.name()) + " x " + qty
                        + " for $" + (type.getPrice() * qty) + ".");
            } catch (InsufficientFundsException e) {
                ui.print(e.getMessage());
            }
            ui.pause();
        }
    }
}
