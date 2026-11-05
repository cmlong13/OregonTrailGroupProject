package oregontrail.events;

import oregontrail.Game;
import oregontrail.model.ItemType;

import java.util.Random;

/**
 * Wild food event. A good event: the party finds berries or game.
 */
public class FindWildFood extends TrailEvent {

    private static final int MIN_FOOD = 20;
    private static final int MAX_FOOD = 60;

    private final Random rng = new Random();

    /** Creates a wild food event with weight 25. */
    public FindWildFood() {
        super("Wild food", 25);
    }

    /**
     * Adds a random amount of food to the wagon's inventory.
     *
     * @param game the running game
     */
    @Override
    public void apply(Game game) {
        int amount = MIN_FOOD + rng.nextInt(MAX_FOOD - MIN_FOOD + 1);
        game.getWagon().getInventory().add(ItemType.FOOD, amount);
        game.getUi().print("You found wild berries and roots! +" + amount + " pounds of food.");
    }
}