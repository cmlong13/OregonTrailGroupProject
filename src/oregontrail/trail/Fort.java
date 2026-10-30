package oregontrail.trail;

import oregontrail.Game;
import oregontrail.model.Store;

/**
 * A fort where the party can buy supplies.
 */
public class Fort extends Location {

    private final Store store;

    public Fort(String name, int mile) {
        super(name, mile);
        this.store = new Store();
    }

    @Override
    public void onArrive(Game game) {
        game.getUi().print("You have reached " + getName() + ".");
        // TODO (events owner): ask whether to shop, then store.open(game).
    }
}
