package oregontrail.events;

import oregontrail.Game;
import oregontrail.model.Person;

import java.util.List;
import java.util.Random;

/**
 * Illness event. One random living member gets sick and loses health.
 */
public class Illness extends TrailEvent {

    private static final String[] DISEASES = {
            "dysentery", "cholera", "typhoid", "measles", "exhaustion", "a fever"
    };
    private static final int MIN_DAMAGE = 10;
    private static final int MAX_DAMAGE = 30;

    private final Random rng = new Random();

    /** Creates an illness event with weight 30. */
    public Illness() {
        super("Illness", 30);
    }

    /**
     * Picks a random living member, damages them, and says who got sick.
     *
     * @param game the running game
     */
    @Override
    public void apply(Game game) {
        List<Person> alive = game.getParty().getAlive();
        if (alive.isEmpty()) {
            return;
        }
        Person sick = alive.get(rng.nextInt(alive.size()));
        int damage = MIN_DAMAGE + rng.nextInt(MAX_DAMAGE - MIN_DAMAGE + 1);
        sick.damage(damage);
        game.getUi().print(sick.getName() + " has come down with "
                + DISEASES[rng.nextInt(DISEASES.length)] + "!");
        if (!sick.isAlive()) {
            game.getUi().print(sick.getName() + " has died.");
        }
    }
}
