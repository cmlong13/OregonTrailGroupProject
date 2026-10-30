package oregontrail.model;

/**
 * Anything that can describe itself in one status line.
 * Implemented by Person, Wagon, TrailEvent and Location.
 */
public interface Describable {

    /** @return a short, player-facing description */
    String describe();
}
