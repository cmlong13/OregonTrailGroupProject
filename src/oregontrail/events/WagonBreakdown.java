package oregontrail.events;

import oregontrail.Game;

/**
 * Wagon breakdown event.
 */
public class WagonBreakdown extends TrailEvent {

    public WagonBreakdown() {
        super("Wagon breakdown", 20);
    }

    @Override
    public void apply(Game game) {
        // TODO (events owner): Use a spare part if there is one; otherwise lose days or get stranded.
    }
}
