package oregontrail.trail;

import oregontrail.Game;

/**
 * A sight along the trail. Arriving only shows a message.
 */
public class Landmark extends Location {

    private final String description;

    public Landmark(String name, int mile, String description) {
        super(name, mile);
        this.description = description;
    }

    @Override
    public void onArrive(Game game) {
        // TODO (events owner): print that the party reached this landmark, then its description.
    }
}
