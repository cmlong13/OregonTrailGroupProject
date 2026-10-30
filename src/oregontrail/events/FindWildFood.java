package oregontrail.events;

import oregontrail.Game;

/**
 * Wild food event.
 */
public class FindWildFood extends TrailEvent {

    public FindWildFood() {
        super("Wild food", 25);
    }

    @Override
    public void apply(Game game) {
        // TODO (events owner): Add some food to the inventory (a good event).
        game.getUi().print(describe() + "!");
    }
}
