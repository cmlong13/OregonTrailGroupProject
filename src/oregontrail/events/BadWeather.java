package oregontrail.events;

import oregontrail.Game;
import oregontrail.model.ItemType;
import oregontrail.model.Person;

import java.util.List;
import java.util.Random;

/**
 * Bad weather event. Hurts everyone a little, and more if the party
 * does not have enough clothing to go around.
 */
public class BadWeather extends TrailEvent {

    private static final int BASE_DAMAGE = 5;
    private static final int COLD_DAMAGE = 12;
    private static final String[] STORMS = {"a thunderstorm", "a blizzard", "a hailstorm", "freezing rain"};

    private final Random rng = new Random();

    /** Creates a bad weather event with weight 25. */
    public BadWeather() {
        super("Bad weather", 25);
    }

    /**
     * Damages every living member, extra if there are fewer sets of clothing than people.
     *
     * @param game the running game
     */
    @Override
    public void apply(Game game) {
        List<Person> alive = game.getParty().getAlive();
        boolean cold = game.getWagon().getInventory().get(ItemType.CLOTHING) < alive.size();

        game.getUi().print("The party is caught in " + STORMS[rng.nextInt(STORMS.length)] + "!");
        if (cold) {
            game.getUi().print("You don't have enough clothing, so everyone suffers.");
        }
        int damage = cold ? COLD_DAMAGE : BASE_DAMAGE;
        for (Person p : alive) {
            p.damage(damage);
            if (!p.isAlive()) {
                game.getUi().print(p.getName() + " did not survive the storm.");
            }
        }
    }
}