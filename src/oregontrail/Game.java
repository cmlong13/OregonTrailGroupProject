package oregontrail;

import oregontrail.events.EventGenerator;
import oregontrail.exceptions.InsufficientFundsException;
import oregontrail.model.Party;
import oregontrail.model.Wagon;
import oregontrail.trail.Trail;
import oregontrail.ui.ConsoleUI;

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
        // TODO (game owner):
        //   print a welcome message
        //   for PARTY_SIZE people: ask for a name, add a new Person to the party
        //   open the store so the player can buy supplies
        //   while the game is not over: playDay()
        //   print the win or lose screen
        ui.print("Welcome to the Oregon Trail! (starter project: nothing is written yet)");
    }

    /**
     * Plays one day: daily menu, travel, a possible random event, and a
     * possible stop. See the game loop diagram in the design doc.
     */
    public void playDay() {
        // TODO (game owner):
        //   show the status line and the daily menu
        //   if the player picks something other than "continue", handle it and show the menu again
        //   remember the miles before traveling
        //   wagon travels one day, feeds the party, party takes daily health change
        //   ask the event generator for an event; if there is one, call its apply(this)
        //   for each stop the trail says we passed today, call its onArrive(this)
        //   add one to the day counter
    }

    /** @return true when everyone has died, the party is stranded, or it reached Oregon */
    public boolean isOver() {
        // TODO (game owner): over if arrived, if the party is wiped out, or if food or oxen ran out.
        return true;
    }

    /**
     * Takes money from the player.
     *
     * @param amount dollars to spend, must be positive
     * @throws InsufficientFundsException if the player cannot afford it
     */
    public void spendMoney(int amount) throws InsufficientFundsException {
        // TODO (game owner):
        //   if amount is more than money, throw InsufficientFundsException
        //   otherwise subtract amount from money
    }

    /** Marks the game as won; called by {@link oregontrail.trail.Destination}. */
    public void markArrived() {
        // TODO (game owner)
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
