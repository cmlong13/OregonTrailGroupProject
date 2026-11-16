package oregontrail.model;

import oregontrail.Game;
import oregontrail.exceptions.InsufficientFundsException;
import oregontrail.ui.ConsoleUI;

public class Store {

    // Purchases an item and adds it to the wagon.
    public void buy(ItemType type, int qty, Game game)
            throws InsufficientFundsException {

        if (type == null || game == null) {
            throw new IllegalArgumentException(
                    "Item type and game cannot be null"
            );
        }

        if (qty <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be positive"
            );
        }

        int cost = Math.multiplyExact(type.getPrice(), qty);

        game.spendMoney(cost);

        game.getWagon().getInventory().add(type, qty);
    }

    // This displays the store menu and lets players buy supplies.
    public void open(Game game) {
        ConsoleUI ui = game.getUi();
        ItemType[] items = ItemType.values();

        String[] options = new String[items.length + 1];

        // Creates a menu option for each available item.
        for (int i = 0; i < items.length; i++) {
            options[i] = items[i] + " ($"
                    + items[i].getPrice()
                    + " per " + items[i].getUnit() + ")";
        }

        options[items.length] = "Leave the store";

        while (true) {
            ui.print("=== Store === You have $"
                    + game.getMoney());

            int choice = ui.showMenu(
                    "What would you like to buy?", options
            );

            // Players need at least one ox before leaving.
            if (choice == items.length + 1) {
                if (game.getWagon().getInventory()
                        .get(ItemType.OXEN) == 0) {

                    ui.print(
                            "You cannot leave without oxen! "
                                    + "Buy at least one."
                    );

                    continue;
                }

                return;
            }

            // Makes sure the player selected a valid option.
            if (choice < 1 || choice > items.length) {
                ui.print("Invalid selection.");
                continue;
            }

            ItemType type = items[choice - 1];

            int max = game.getMoney() / type.getPrice();

            if (max == 0) {
                ui.print("You cannot afford any " + type);
                continue;
            }

            ui.print("How many " + type.getUnit()
                    + "s? (0 to cancel, max " + max + ")");

            int qty = ui.readInt(0, max);

            if (qty == 0) {
                continue;
            }

            try {
                buy(type, qty, game);

                ui.print("Bought " + qty + " " + type + ".");

            } catch (InsufficientFundsException e) {
                ui.print(e.getMessage());
            }
        }
    }
}
