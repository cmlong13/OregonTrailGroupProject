package oregontrail;

import oregontrail.events.EventGenerator;
import oregontrail.events.TrailEvent;
import oregontrail.exceptions.InsufficientFundsException;
import oregontrail.model.Party;
import oregontrail.model.Wagon;
import oregontrail.trail.Location;
import oregontrail.trail.Trail;
import oregontrail.ui.ConsoleUI;

import java.util.Optional;

/**
 * Runs the game: setup, the store, and the day-by-day loop until the party
 * wins or loses. Owns every other part of the game (composition).
 */
public class Game {

    public static final int STARTING_MONEY = 800;
    public static final int PARTY_SIZE = 5;

    private final ConsoleUI ui;
    private final Party party;
    private final Wagon wagon;
    private final EventGenerator events;
    private final Trail trail;
    private int day;
    private int money;
    private boolean arrived;

    public Game() {
        this.ui = new ConsoleUI();
        this.party = new Party();
        this.wagon = new Wagon();
        this.events = new EventGenerator();
        this.trail = new Trail();
        this.day = 1;
        this.money = STARTING_MONEY;
        this.arrived = false;
    }

    /** Runs setup, then plays days until {@link #isOver()} is true. */
    public void start() {
        // TODO (game owner): name the party, open the store, then loop playDay() until isOver().
        ui.print("Welcome to the Oregon Trail! (starter project: game loop not written yet)");
    }

    /**
     * Plays one day: daily menu, travel, a possible random event, and a
     * possible stop. See the game loop diagram in the design doc.
     */
    public void playDay() {
        // TODO (game owner): show the daily menu and handle the choice.
        int milesBefore = wagon.getMilesTraveled();
        wagon.travelOneDay();
        wagon.feed(party);
        party.applyDailyHealth(wagon.getRations(), wagon.getPace());

        Optional<TrailEvent> event = events.rollForEvent();
        event.ifPresent(e -> e.apply(this)); // polymorphism: the subclass decides what happens

        for (Location stop : trail.stopsPassed(milesBefore, wagon.getMilesTraveled())) {
            stop.onArrive(this); // polymorphism: Landmark, Fort or Destination
        }
        day++;
    }

    /** @return true when everyone has died, the party is stranded, or it reached Oregon */
    public boolean isOver() {
        // TODO (game owner): also end the game when food or oxen run out.
        return arrived || party.isWipedOut();
    }

    /**
     * Takes money from the player.
     *
     * @param amount dollars to spend, must be positive
     * @throws InsufficientFundsException if the player cannot afford it
     */
    public void spendMoney(int amount) throws InsufficientFundsException {
        if (amount > money) {
            throw new InsufficientFundsException(amount, money);
        }
        money -= amount;
    }

    /** Marks the game as won; called by {@link oregontrail.trail.Destination}. */
    public void markArrived() {
        arrived = true;
    }

    public ConsoleUI getUi() {
        return ui;
    }

    public Party getParty() {
        return party;
    }

    public Wagon getWagon() {
        return wagon;
    }

    public int getDay() {
        return day;
    }

    public int getMoney() {
        return money;
    }
}
