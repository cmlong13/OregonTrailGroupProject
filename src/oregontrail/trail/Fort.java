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
        // TODO (events owner): print that the party reached the fort, ask whether to shop, if yes store.open(game).
    }
}
