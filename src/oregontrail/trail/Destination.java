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
        game.getUi().print("You made it to " + getName() + "!");
        game.markArrived();
    }
}
