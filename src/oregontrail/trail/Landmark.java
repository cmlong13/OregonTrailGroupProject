package oregontrail.trail;

import oregontrail.Game;
import oregontrail.ui.ConsoleUI;

/**
 * A sight along the trail. Arriving only shows a message.
 */
public class Landmark extends Location {

    private final String description;

    public Landmark(String name, int mile, String description) {
        super(name, mile);
        this.description = description;
    }

    /** Shows the landmark's name, its description and how far along the trail it is. */
    @Override
    public void onArrive(Game game) {
        ConsoleUI ui = game.getUi();
        ui.header("YOU HAVE REACHED " + getName().toUpperCase());
        ui.print(description);
        ui.print("Mile " + getMile() + " of " + Trail.LENGTH + ".");
        ui.pause();
    }
}
