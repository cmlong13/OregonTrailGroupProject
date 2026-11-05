package oregontrail.events;

import oregontrail.Game;
import oregontrail.model.Inventory;
import oregontrail.model.ItemType;

import java.util.Random;

/**
 * Wagon breakdown event. A spare part fixes it; without one the party
 * loses days and may be stranded for good.
 */
public class WagonBreakdown extends TrailEvent {

    private static final int MIN_DAYS_LOST = 2;
    private static final int MAX_DAYS_LOST = 5;
    /** Chance (0.0 to 1.0) of being stranded when there is no spare part. */
    private static final double STRANDED_CHANCE = 0.25;

    private final Random rng = new Random();

    /** Creates a wagon breakdown event with weight 20. */
    public WagonBreakdown() {
        super("Wagon breakdown", 20);
    }

    /**
     * Uses a spare part if there is one. Otherwise the party either loses
     * several days or is stranded, which ends the game.
     *
     * @param game the running game
     */
    @Override
    public void apply(Game game) {
        Inventory inv = game.getWagon().getInventory();
        game.getUi().print("A wagon wheel breaks!");

        if (inv.removeUpTo(ItemType.SPARE_PART, 1) == 1) {
            game.getUi().print("You used a spare part to fix it right away.");
        } else if (rng.nextDouble() < STRANDED_CHANCE) {
            game.getUi().print("You have no spare parts and cannot repair it. You are stranded!");
            game.markStranded();
        } else {
            int days = MIN_DAYS_LOST + rng.nextInt(MAX_DAYS_LOST - MIN_DAYS_LOST + 1);
            game.getUi().print("No spare parts. It takes " + days + " days to fix it by hand.");
            game.skipDays(days);
        }
    }
}