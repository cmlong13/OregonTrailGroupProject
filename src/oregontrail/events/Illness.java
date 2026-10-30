package oregontrail.events;

import oregontrail.Game;

/**
 * Illness event.
 */
public class Illness extends TrailEvent {

    public Illness() {
        super("Illness", 30);
    }

    @Override
    public void apply(Game game) {
        // TODO (events owner): Pick a random living member, damage them, and print who got sick.
        game.getUi().print(describe() + "!");
    }
}
