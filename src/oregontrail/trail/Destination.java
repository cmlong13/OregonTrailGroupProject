package oregontrail.trail;

import oregontrail.Game;

/**
 * The end of the trail. Arriving wins the game.
 */
public class Destination extends Location {

    public Destination(String name, int mile) {
        super(name, mile);
    }

    @Override
    public void onArrive(Game game) {
        // TODO (events owner): print the arrival message and tell the game the party arrived.
    }
}
