package oregontrail.events;

import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Decides whether a random event happens today, and which one.
 */
public class EventGenerator {

    /** Chance that any event happens on a given day, from 0.0 to 1.0. */
    public static final double DAILY_EVENT_CHANCE = 0.25;

    private final List<TrailEvent> events;
    private final Random rng;

    public EventGenerator() {
        this(new Random());
    }

    /** Pass a seeded Random to get the same events every run (useful for tests). */
    public EventGenerator(Random rng) {
        this.rng = rng;
        // TODO (events owner): fill this with one of each TrailEvent subclass.
        this.events = List.of();
    }

    /**
     * @return today's event, or empty if nothing happens. Events are picked
     *         by weight, so higher-weight events come up more often.
     */
    public Optional<TrailEvent> rollForEvent() {
        // TODO (events owner):
        //   if a random number from 0 to 1 is at or above DAILY_EVENT_CHANCE, return empty
        //   total = sum of every event's weight
        //   pick a random number from 0 to total - 1
        //   walk the list, subtracting each weight, and return the event where the number drops below 0
        return Optional.empty();
    }
}
