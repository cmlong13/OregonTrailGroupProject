package oregontrail.trail;

import oregontrail.Game;
import oregontrail.model.Store;
import oregontrail.ui.ConsoleUI;

/**
 * A fort where the party can buy supplies.
 */
public class Fort extends Location {

    private final Store store;

    public Fort(String name, int mile) {
        super(name, mile);
        this.store = new Store(name + " Trading Post");
    }

    /** Welcomes the party and offers a visit to the fort's trading post. */
    @Override
    public void onArrive(Game game) {
        ConsoleUI ui = game.getUi();
        ui.header("YOU HAVE REACHED " + getName().toUpperCase());
        ui.print("Soldiers and traders bustle around the fort, and the trading post is open.");
        if (ui.readYesNo("Would you like to visit the trading post?")) {
            store.open(game);
        }
    }
}
