package oregontrail.events;

import oregontrail.Game;
import oregontrail.model.Inventory;
import oregontrail.model.ItemType;

import java.util.Random;

/**
 * Thief event. Someone sneaks into camp at night and steals part of the food.
 */
public class Thief extends TrailEvent {

    private final Random rng = new Random();

    /** Creates a thief event with weight 10. */
    public Thief() {
        super("Thief", 10);
    }

    /**
     * Steals 10 to 30 percent of the food in the wagon.
     *
     * @param game the running game
     */
    @Override
    public void apply(Game game) {
        Inventory inv = game.getWagon().getInventory();
        int food = inv.get(ItemType.FOOD);
        if (food <= 0) {
            game.getUi().print("A thief sneaks into camp, but finds nothing worth taking.");
            return;
        }
        int stolen = Math.max(1, food * (10 + rng.nextInt(21)) / 100);
        stolen = inv.removeUpTo(ItemType.FOOD, stolen);
        game.getUi().print("A thief raided the wagon during the night and stole " + stolen + " pounds of food!");
    }
}