package oregontrail.model;

/**
 * One member of the party. Health is private and can only change through
 * {@link #damage(int)} and {@link #heal(int)} (encapsulation).
 */
public class Person implements Describable {

    public static final int MAX_HEALTH = 100;

    private final String name;
    private int health;

    public Person(String name) {
        this(name, MAX_HEALTH);
    }

    public Person(String name, int health) {
        this.name = name;
        this.health = health;
    }

    /** Lowers health, never below 0. */
    public void damage(int amount) {
        // TODO (model owner): reject a negative amount, subtract it, never go below 0.
    }

    /** Raises health, never above {@link #MAX_HEALTH}. */
    public void heal(int amount) {
        // TODO (model owner): reject a negative amount, add it, never go above MAX_HEALTH.
    }

    public boolean isAlive() {
        // TODO (model owner)
        return true;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    /**
     * Turns a health number into a word the player can read at a glance.
     * It is static because it only needs the number, not a Person, so the game
     * can also use it for the party's average health: Person.healthWord(55).
     *
     * @param health a health value from 0 to {@link #MAX_HEALTH}
     * @return "Good" for 70 and up, "Fair" for 40 and up, "Poor" above 0, otherwise "Dead"
     */
    public static String healthWord(int health) {
        if (health >= 70) {
            return "Good";
        } else if (health >= 40) {
            return "Fair";
        } else if (health > 0) {
            return "Poor";
        } else {
            return "Dead";
        }
    }

    /** @return the name and a health word, for example "Mary - Good" */
    @Override
    public String describe() {
        return name + " - " + healthWord(health);
    }
}
