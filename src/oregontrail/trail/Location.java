package oregontrail.trail;

import oregontrail.Game;
import oregontrail.model.Describable;

/**
 * A stop on the trail at a fixed mile. Each subclass decides what happens
 * when the party arrives (abstraction and polymorphism).
 */
public abstract class Location implements Describable {

    private final String name;
    private final int mile;

    protected Location(String name, int mile) {
        this.name = name;
        this.mile = mile;
    }

    /** Called once when the wagon reaches or passes this stop. */
    public abstract void onArrive(Game game);

    public String getName() {
        return name;
    }

    public int getMile() {
        return mile;
    }

    @Override
    public String describe() {
        return name + " (mile " + mile + ")";
    }
}
