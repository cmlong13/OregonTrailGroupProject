package oregontrail.events;

import oregontrail.Game;

/**
 * Bad weather event.
 */
public class BadWeather extends TrailEvent {

    public BadWeather() {
        super("Bad weather", 25);
    }

    @Override
    public void apply(Game game) {
        // TODO (events owner): Hurt everyone a little, more if clothing is low.
        game.getUi().print(describe() + "!");
    }
}
