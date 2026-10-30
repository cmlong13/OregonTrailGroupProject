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
        this.events = List.of(new Illness(), new WagonBreakdown(), new BadWeather(), new FindWildFood());
    }

    /**
     * @return today's event, or empty if nothing happens. Events are picked
     *         by weight, so higher-weight events come up more often.
     */
    public Optional<TrailEvent> rollForEvent() {
        // TODO (events owner): with DAILY_EVENT_CHANCE, pick an event using the weights.
        return Optional.empty();
    }
}
