package oregontrail;

import oregontrail.events.EventGenerator;
import oregontrail.events.TrailEvent;
import oregontrail.exceptions.InsufficientFundsException;
import oregontrail.model.ItemType;
import oregontrail.model.Pace;
import oregontrail.model.Party;
import oregontrail.model.Person;
import oregontrail.model.Rations;
import oregontrail.model.Store;
import oregontrail.model.Wagon;
import oregontrail.trail.Location;
import oregontrail.trail.Trail;
import oregontrail.ui.AsciiArt;
import oregontrail.ui.ConsoleUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Runs the game: setup, the store, and the day-by-day loop until the party
 * wins or loses. Owns every other part of the game (composition).
 */
public class Game {

    public static final int STARTING_MONEY = 800;
    public static final int PARTY_SIZE = 5;

    /** Health each living traveler gets back from a day of rest. */
    public static final int REST_HEAL = 10;

    /** Points for each traveler who reaches Oregon alive. */
    public static final int POINTS_PER_SURVIVOR = 500;

    /** How fast the title art draws, in milliseconds per line. */
    private static final int ART_DELAY_MS = 60;

    private static final String[] MAIN_MENU = {
            "Start the journey",
            "Learn how to play",
            "Quit"
    };

    private static final String[] DAILY_MENU = {
            "Keep traveling",
            "Check the wagon and party",
            "Look at the map",
            "Change the pace",
            "Change the rations",
            "Rest for a day",
            "Go hunting",
            "Quit the game"
    };

    private final ConsoleUI ui;
    private final Party party;
    private final Wagon wagon;
    private final EventGenerator events;
    private final Trail trail;
    private final Store startingStore;
    private final Hunt hunt;
    private int day;
    private int money;
    private boolean arrived;
    private boolean stranded;
    private boolean quit;

    public Game() {
        this.ui = new ConsoleUI();
        this.party = new Party();
        this.wagon = new Wagon();
        this.events = new EventGenerator();
        this.trail = new Trail();
        this.startingStore = new Store("Independence General Store");
        this.hunt = new Hunt();
        this.day = 1;
        this.money = STARTING_MONEY;
        this.arrived = false;
        this.stranded = false;
        this.quit = false;
    }

    // ------------------------------------------------------------------
    // Starting and ending the game
    // ------------------------------------------------------------------

    /**
     * Runs the whole game: title screen, main menu, party setup, the starting
     * store, then one day at a time until {@link #isOver()} is true, and the ending.
     */
    public void start() {
        showTitleScreen();
        if (!mainMenu()) {
            return;
        }
        setUpParty();

        ui.header("INDEPENDENCE, MISSOURI - SPRING 1848");
        ui.print("Before you leave, buy supplies at the general store. You have $" + money + ".");
        ui.print("Tip: you need at least one ox, but more oxen keep you moving if one is");
        ui.print("lost. Buy plenty of food, some clothing for bad weather, ammunition");
        ui.print("for hunting, and a few spare parts for the wagon.");
        ui.pause();

        startingStore.open(this);

        ui.header("THE JOURNEY BEGINS");
        ui.print("Your wagon rolls west out of Independence. Oregon is " + Trail.LENGTH + " miles away.");
        ui.pause();

        while (!isOver()) {
            playDay();
        }
        showEnding();
    }

    /** Draws the title art and the two lines under it, then waits for Enter. */
    private void showTitleScreen() {
        ui.printSlowly(AsciiArt.TITLE, ART_DELAY_MS);
        ui.printSlowly(AsciiArt.WAGON, ART_DELAY_MS);
        ui.blank();
        ui.print(ConsoleUI.center("A 2,000-mile journey from Independence, Missouri"));
        ui.print(ConsoleUI.center("to the Willamette Valley, spring 1848"));
        ui.blank();
        ui.pause();
    }

    /**
     * Shows the main menu until the player starts or quits.
     *
     * @return true to start the journey, false to quit
     */
    private boolean mainMenu() {
        while (true) {
            int choice = ui.showMenu("MAIN MENU", MAIN_MENU);
            switch (choice) {
                case 1:
                    return true;
                case 2:
                    showHowToPlay();
                    break;
                case 3:
                    ui.print("Goodbye!");
                    return false;
            }
        }
    }

    /** Explains the goal and the choices the player has each day. */
    private void showHowToPlay() {
        ui.header("HOW TO PLAY");
        ui.print("Lead a party of " + PARTY_SIZE + " travelers " + Trail.LENGTH
                + " miles from Independence, Missouri,");
        ui.print("to the Willamette Valley in Oregon.");
        ui.blank();
        ui.print("- Spend your $" + STARTING_MONEY + " at the general store before you leave.");
        ui.print("  You can't leave without at least one ox to pull the wagon.");
        ui.print("- Each day, choose to travel, rest, hunt, or check on your party.");
        ui.print("- A faster pace covers more miles but wears your party down.");
        ui.print("- Bigger rations keep your party healthy but use more food.");
        ui.print("- Hunting uses ammunition and a whole day, but brings back food.");
        ui.print("- At rivers, choose how to cross. Deep water is dangerous.");
        ui.print("- Forts have trading posts where you can buy more supplies.");
        ui.blank();
        ui.print("The journey ends if everyone dies, the wagon is stranded, or the last");
        ui.print("ox is gone. Reach Oregon to score points for every survivor, the money");
        ui.print("you have left, and your remaining food.");
        ui.pause();
    }

    /** Asks for the travelers' names, lets the player fix any, then adds them to the party. */
    private void setUpParty() {
        ui.header("NAME YOUR PARTY");
        String[] names = new String[PARTY_SIZE];
        names[0] = ui.readName("Name of the wagon leader");
        for (int i = 1; i < PARTY_SIZE; i++) {
            names[i] = ui.readName("Name of traveler " + (i + 1));
        }

        while (true) {
            ui.blank();
            ui.print("Your party:");
            for (int i = 0; i < names.length; i++) {
                String line = "  " + (i + 1) + ". " + names[i];
                if (i == 0) {
                    line += " (leader)";
                }
                ui.print(line);
            }
            if (ui.readYesNo("Are these names right?")) {
                break;
            }
            int which = ui.readInt("Which traveler's name do you want to change?", 1, PARTY_SIZE);
            names[which - 1] = ui.readName("New name for traveler " + which);
        }

        for (String name : names) {
            party.addMember(new Person(name));
        }
    }

    /** Shows the win, quit or game over screen, with the score on a win. */
    private void showEnding() {
        if (arrived) {
            ui.printSlowly(AsciiArt.VALLEY, ART_DELAY_MS);
            ui.header("YOU MADE IT TO OREGON!");
            ui.print("Your party reached the Willamette Valley in " + daysOnTrail() + " days.");
            ui.blank();
            ui.print("Survivors:");
            for (Person p : party.getAlive()) {
                ui.print("  " + p.describe());
            }
            int survivors = party.getAlive().size();
            int food = wagon.getInventory().get(ItemType.FOOD);
            // %-36s prints text left-aligned in 36 spaces; %6d prints a whole
            // number right-aligned in 6 spaces. Together they line up the columns.
            ui.box(String.format("%-36s %6d", "Survivors (" + survivors + " x " + POINTS_PER_SURVIVOR + ")",
                            survivors * POINTS_PER_SURVIVOR),
                    String.format("%-36s %6d", "Money left ($" + money + ")", money),
                    String.format("%-36s %6d", "Food left (" + food + " lb, 1 point per 2 lb)", food / 2),
                    String.format("%-36s %6s", "", "------"),
                    String.format("%-36s %6d", "TOTAL", calculateScore()));
        } else if (quit) {
            ui.header("JOURNEY ABANDONED");
            ui.print("Your party gave up at mile " + wagon.getMilesTraveled() + " of " + Trail.LENGTH + ".");
        } else {
            ui.printSlowly(AsciiArt.TOMBSTONE, ART_DELAY_MS);
            ui.header("GAME OVER");
            ui.print(gameOverReason());
            ui.print("You made it " + wagon.getMilesTraveled() + " of " + Trail.LENGTH
                    + " miles in " + daysOnTrail() + " days.");
            List<String> dead = new ArrayList<>();
            for (Person p : party.getMembers()) {
                if (!p.isAlive()) {
                    dead.add(p.getName());
                }
            }
            if (!dead.isEmpty()) {
                ui.print("Lost on the trail: " + String.join(", ", dead) + ".");
            }
        }
        ui.pause();
    }

    /** @return why the journey ended, for the game over screen */
    private String gameOverReason() {
        if (party.isWipedOut()) {
            return "Everyone in your party has died.";
        } else if (stranded) {
            return "Your wagon broke down and couldn't be repaired. You are stranded.";
        } else {
            return "You have no oxen left to pull the wagon.";
        }
    }

    /** @return the days spent on the trail so far (the counter starts at day 1) */
    private int daysOnTrail() {
        return day - 1;
    }

    /** @return survivors x {@link #POINTS_PER_SURVIVOR}, plus money left, plus half the food left */
    public int calculateScore() {
        return party.getAlive().size() * POINTS_PER_SURVIVOR
                + money
                + wagon.getInventory().get(ItemType.FOOD) / 2;
    }

    // ------------------------------------------------------------------
    // One day on the trail
    // ------------------------------------------------------------------

    /**
     * Plays one day: shows the status and the daily menu. Checking the wagon,
     * the map, the pace and the rations don't use up the day, so the menu
     * comes back; traveling, resting, hunting and quitting end the day.
     */
    public void playDay() {
        while (true) {
            showStatusBar();
            int choice = ui.showMenu("WHAT WILL YOU DO?", DAILY_MENU);
            switch (choice) {
                case 1:
                    travel();
                    return;
                case 2:
                    showWagonReport();
                    break;
                case 3:
                    showMap();
                    break;
                case 4:
                    changePace();
                    break;
                case 5:
                    changeRations();
                    break;
                case 6:
                    rest();
                    return;
                case 7:
                    hunt.go(this);
                    return;
                case 8:
                    if (ui.readYesNo("Are you sure you want to give up the journey?")) {
                        quit = true;
                        return;
                    }
                    break;
            }
        }
    }

    /**
     * One day of travel: move, eat, take the daily health change, then maybe
     * a random event, then every stop passed today.
     */
    private void travel() {
        List<Person> aliveBefore = party.getAlive();
        int milesBefore = wagon.getMilesTraveled();

        wagon.travelOneDay();
        wagon.feed(party);
        party.applyDailyHealth(wagon.getRations(), wagon.getPace());
        day++;

        int milesAfter = wagon.getMilesTraveled();
        ui.blank();
        ui.print("You traveled " + (milesAfter - milesBefore) + " miles today.");
        announceDeaths(aliveBefore);

        // rollForEvent() returns an Optional: a box that either holds a
        // TrailEvent or is empty. isPresent() checks, and get() takes it out.
        Optional<TrailEvent> event = events.rollForEvent();
        if (event.isPresent() && !party.isWipedOut()) {
            ui.blank();
            ui.print("*** " + event.get().describe().toUpperCase() + " ***");
            event.get().apply(this);
            ui.pause();
        }

        for (Location stop : trail.stopsPassed(milesBefore, milesAfter)) {
            if (isOver()) {
                break;
            }
            stop.onArrive(this);
        }
    }

    /**
     * Prints a message for each traveler who was alive before and isn't now.
     *
     * @param aliveBefore the living members from before something happened
     */
    private void announceDeaths(List<Person> aliveBefore) {
        for (Person p : aliveBefore) {
            if (!p.isAlive()) {
                ui.print(p.getName() + " has died. The party buries them beside the trail.");
            }
        }
    }

    /** Heals every living traveler and uses up a day. */
    private void rest() {
        for (Person p : party.getAlive()) {
            p.heal(REST_HEAL);
        }
        skipDays(1);
        ui.print("The party rests for a day. Everyone feels a little stronger.");
        ui.pause();
    }

    /** Lets the player pick a new pace. */
    private void changePace() {
        Pace[] paces = Pace.values();
        String[] options = new String[paces.length];
        for (int i = 0; i < paces.length; i++) {
            Pace p = paces[i];
            options[i] = ConsoleUI.fit(ConsoleUI.titleCase(p.name()), 11)
                    + p.getMilesPerDay() + " miles a day, "
                    + healthChangeText(p.getHealthCost(), "no extra strain")
                    + currentMark(p == wagon.getPace());
        }
        Pace chosen = paces[ui.showMenu("CHANGE THE PACE", options) - 1];
        wagon.setPace(chosen);
        ui.print("Pace set to " + ConsoleUI.titleCase(chosen.name()) + ".");
    }

    /** Lets the player pick new rations. */
    private void changeRations() {
        Rations[] choices = Rations.values();
        String[] options = new String[choices.length];
        for (int i = 0; i < choices.length; i++) {
            Rations r = choices[i];
            options[i] = ConsoleUI.fit(ConsoleUI.titleCase(r.name()), 11)
                    + r.getPoundsPerPerson() + " lb per person a day, "
                    + healthChangeText(r.getHealthCost(), "no health change")
                    + currentMark(r == wagon.getRations());
        }
        Rations chosen = choices[ui.showMenu("CHANGE THE RATIONS", options) - 1];
        wagon.setRations(chosen);
        ui.print("Rations set to " + ConsoleUI.titleCase(chosen.name()) + ".");
    }

    /**
     * Describes a daily health cost for the menus. A negative cost means
     * health is gained, so it is shown with a +.
     *
     * @param healthCost health lost per day
     * @param ifZero     what to show when there is no change
     * @return for example "-3 health a day" or "+2 health a day"
     */
    private static String healthChangeText(int healthCost, String ifZero) {
        if (healthCost > 0) {
            return "-" + healthCost + " health a day";
        } else if (healthCost < 0) {
            return "+" + (-healthCost) + " health a day";
        } else {
            return ifZero;
        }
    }

    /** @return "  (current)" for the option already chosen, otherwise "" */
    private static String currentMark(boolean isCurrent) {
        if (isCurrent) {
            return "  (current)";
        }
        return "";
    }

    // ------------------------------------------------------------------
    // Screens
    // ------------------------------------------------------------------

    /** Shows the day, miles, supplies and party in a box, plus any warnings. */
    private void showStatusBar() {
        int miles = wagon.getMilesTraveled();
        int food = wagon.getInventory().get(ItemType.FOOD);
        String health = partyHealthWord();

        String next = "Next: -";
        Location nextStop = trail.nextStop(miles + 1);
        if (nextStop != null) {
            next = "Next: " + nextStop.getName() + " in " + (nextStop.getMile() - miles) + " mi";
        }

        ui.blank();
        ui.box("Day " + day + "   Mile " + miles + " of " + Trail.LENGTH + "   " + next,
                "Food " + food + " lb   Oxen " + wagon.getInventory().get(ItemType.OXEN)
                        + "   Money $" + money + "   Party health " + health,
                "Pace " + ConsoleUI.titleCase(wagon.getPace().name())
                        + "   Rations " + ConsoleUI.titleCase(wagon.getRations().name())
                        + "   Travelers alive " + party.getAlive().size() + " of " + PARTY_SIZE);
        if (food == 0) {
            ui.print("WARNING: You are out of food! Your party will grow weak.");
        }
        if (health.equals("Poor")) {
            ui.print("WARNING: Your party's health is poor. Resting for a day will help.");
        }
    }

    /** @return the health word for the average health of the living travelers */
    private String partyHealthWord() {
        List<Person> alive = party.getAlive();
        if (alive.isEmpty()) {
            return Person.healthWord(0);
        }
        int total = 0;
        for (Person p : alive) {
            total += p.getHealth();
        }
        return Person.healthWord(total / alive.size());
    }

    /** Shows each traveler, every supply, and the money, pace and rations. */
    private void showWagonReport() {
        ui.header("WAGON REPORT - DAY " + day);
        ui.print("Party:");
        for (Person p : party.getMembers()) {
            ui.print("  " + p.describe());
        }

        ui.blank();
        ui.print("Supplies:");
        for (ItemType type : ItemType.values()) {
            int count = wagon.getInventory().get(type);
            ui.print("  " + ConsoleUI.fit(ConsoleUI.titleCase(type.name()), 14)
                    + count + " " + type.unitFor(count));
        }

        int foodPerDay = wagon.getRations().getPoundsPerPerson() * party.getAlive().size();
        if (foodPerDay > 0) {
            ui.print("Food lasts about " + wagon.getInventory().get(ItemType.FOOD) / foodPerDay
                    + " more days at these rations.");
        }

        ui.blank();
        ui.print("Money:   $" + money);
        ui.print("Pace:    " + ConsoleUI.titleCase(wagon.getPace().name())
                + " (" + wagon.getPace().getMilesPerDay() + " miles a day)");
        ui.print("Rations: " + ConsoleUI.titleCase(wagon.getRations().name())
                + " (" + wagon.getRations().getPoundsPerPerson() + " lb per person a day)");
        ui.pause();
    }

    /** Shows a progress bar and every stop, marking the ones passed and the next one. */
    private void showMap() {
        int miles = wagon.getMilesTraveled();
        ui.header("MAP OF THE TRAIL");
        int percent = Math.min(100, miles * 100 / Trail.LENGTH);
        ui.print(ConsoleUI.progressBar(miles, Trail.LENGTH, 50) + " " + percent + "%");
        ui.blank();

        // %4d prints the mile right-aligned in 4 spaces, so the numbers line up.
        ui.print("[x] " + ConsoleUI.fit("Independence, Missouri", 28) + String.format("mile %4d", 0));
        Location next = trail.nextStop(miles + 1);
        for (Location stop : trail.getStops()) {
            String mark = "[ ]";
            if (stop.getMile() <= miles) {
                mark = "[x]";
            }
            String line = mark + " " + ConsoleUI.fit(stop.getName(), 28)
                    + String.format("mile %4d", stop.getMile());
            if (stop == next) {
                line += "   <-- next";
            }
            ui.print(line);
        }
        ui.pause();
    }

    // ------------------------------------------------------------------
    // Game state, used by the other classes
    // ------------------------------------------------------------------

    /**
     * @return true when the party arrived, is stranded, quit, has all died,
     *         or has no oxen left
     */
    public boolean isOver() {
        return arrived
                || stranded
                || quit
                || party.isWipedOut()
                || wagon.getInventory().get(ItemType.OXEN) == 0;
    }

    /**
     * Takes money from the player.
     *
     * @param amount dollars to spend, must not be negative
     * @throws InsufficientFundsException if the player cannot afford it
     * @throws IllegalArgumentException   if amount is negative
     */
    public void spendMoney(int amount) throws InsufficientFundsException {
        if (amount < 0) {
            throw new IllegalArgumentException("Can't spend a negative amount: " + amount);
        }
        if (amount > money) {
            throw new InsufficientFundsException(amount, money);
        }
        money -= amount;
    }

    /**
     * Gives the player money; called by FriendlyTraveler.
     *
     * @param amount dollars to add, must not be negative
     * @throws IllegalArgumentException if amount is negative
     */
    public void addMoney(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Can't add a negative amount: " + amount);
        }
        money += amount;
    }

    /** Marks the game as won; called by {@link oregontrail.trail.Destination}. */
    public void markArrived() {
        arrived = true;
    }

    /** Ends the game because the wagon can't be fixed; called by WagonBreakdown. */
    public void markStranded() {
        stranded = true;
    }

    /**
     * Passes days without traveling, such as while resting, hunting or fixing
     * the wagon. Each day the party eats and takes the health change for a
     * steady pace. Stops early if everyone has died.
     *
     * @param days how many days pass
     */
    public void skipDays(int days) {
        for (int i = 0; i < days && !party.isWipedOut(); i++) {
            List<Person> aliveBefore = party.getAlive();
            wagon.feed(party);
            party.applyDailyHealth(wagon.getRations(), Pace.STEADY);
            day++;
            announceDeaths(aliveBefore);
        }
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
