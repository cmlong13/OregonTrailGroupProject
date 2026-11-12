package oregontrail.trail;

import oregontrail.Game;

/**
 * The end of the trail. Arriving wins the game.
 */
public class Destination extends Location {

    public Destination(String name, int mile) {
        super(name, mile);
    }

    /** Announces the arrival and tells the game the party won. */
    @Override
    public void onArrive(Game game) {
        game.getUi().print("At last, the green fields of the Willamette Valley come into view!");
        game.markArrived();
    }
}
