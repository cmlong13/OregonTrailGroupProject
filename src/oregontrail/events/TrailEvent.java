package oregontrail.events;

import oregontrail.Game;
import oregontrail.model.Describable;

/**
 * Something random that can happen on a day of travel. Each subclass decides
 * what it does in {@link #apply(Game)}, so {@link oregontrail.Game} never needs
 * to know which event it got (abstraction and polymorphism).
 */
public abstract class TrailEvent implements Describable {

    private final String name;
    private final int weight;

    /**
     * @param name   shown to the player
     * @param weight how likely this event is compared to the others (higher is more likely)
     */
    protected TrailEvent(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    /** Makes the event happen and tells the player what happened. */
    public abstract void apply(Game game);

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String describe() {
        return name;
    }
}
