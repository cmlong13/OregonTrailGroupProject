package oregontrail.events;

import oregontrail.Game;
import oregontrail.model.Inventory;
import oregontrail.model.ItemType;

/**
 * Lost ox event. An ox wanders off in the night. The last ox is always
 * found again, so this event alone can never end the game.
 */
public class LostOx extends TrailEvent {

    /** Creates a lost ox event with weight 10. */
    public LostOx() {
        super("Lost ox", 10);
    }

    /**
     * Removes one ox if the party has more than one.
     *
     * @param game the running game
     */
    @Override
    public void apply(Game game) {
        Inventory inv = game.getWagon().getInventory();
        if (inv.get(ItemType.OXEN) > 1) {
            inv.removeUpTo(ItemType.OXEN, 1);
            game.getUi().print("An ox wandered off in the night and is gone for good. "
                    + inv.get(ItemType.OXEN) + " oxen remain.");
        } else {
            game.getUi().print("Your last ox wandered off, but you tracked it down by morning.");
        }
    }
}
