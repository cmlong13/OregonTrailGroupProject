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

    /** Creates a generator with an unseeded Random. */
    public EventGenerator() {
        this(new Random());
    }

    /**
     * Pass a seeded Random to get the same events every run (useful for tests).
     *
     * @param rng the random source to use
     */
    public EventGenerator(Random rng) {
        this.rng = rng;
        this.events = List.of(
                new Illness(),
                new BadWeather(),
                new FindWildFood(),
                new WagonBreakdown(),
                new Thief(),
                new FriendlyTraveler(),
                new LostOx());
    }

    /**
     * @return today's event, or empty if nothing happens. Events are picked
     *         by weight, so higher-weight events come up more often.
     */
    public Optional<TrailEvent> rollForEvent() {
        if (rng.nextDouble() >= DAILY_EVENT_CHANCE) {
            return Optional.empty();
        }
        int total = 0;
        for (TrailEvent e : events) {
            total += e.getWeight();
        }
        int roll = rng.nextInt(total);
        for (TrailEvent e : events) {
            roll -= e.getWeight();
            if (roll < 0) {
                return Optional.of(e);
            }
        }
        return Optional.empty(); // unreachable, but Java needs a return
    }
}