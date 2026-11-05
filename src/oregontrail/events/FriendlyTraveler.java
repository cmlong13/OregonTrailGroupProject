package oregontrail.events;

import oregontrail.Game;

import java.util.Random;

/**
 * Friendly traveler event. A good event: a passing traveler gives the party money.
 */
public class FriendlyTraveler extends TrailEvent {

    private final Random rng = new Random();

    /** Creates a friendly traveler event with weight 10. */
    public FriendlyTraveler() {
        super("Friendly traveler", 10);
    }

    /**
     * Gives the player $10 to $40.
     *
     * @param game the running game
     */
    @Override
    public void apply(Game game) {
        int gift = 10 + rng.nextInt(31);
        game.addMoney(gift);
        game.getUi().print("A friendly traveler shares trail stories and gives you $" + gift + ".");
    }
}