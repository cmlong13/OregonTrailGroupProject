package oregontrail.model;

/**
 * One member of the party. Health is private and can only change through
 * {@link #damage(int)} and {@link #heal(int)} (encapsulation).
 */
public class Person implements Describable {

    public static final int MAX_HEALTH = 100;

    private final String name;
    private int health;

    /** Creates a person at full health. */
    public Person(String name) {
        this(name, MAX_HEALTH);
    }

    /** Creates a person with a specific starting health (clamped to 0..MAX_HEALTH). */
    public Person(String name, int health) {
        this.name = name;
        this.health = Math.max(0, Math.min(MAX_HEALTH, health));
    }

    /**
     * Lowers health, never below 0.
     *
     * @throws IllegalArgumentException if amount is negative
     */
    public void damage(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Damage cannot be negative: " + amount);
        }
        health = Math.max(0, health - amount);
    }

    /**
     * Raises health, never above {@link #MAX_HEALTH}. The dead stay dead.
     *
     * @throws IllegalArgumentException if amount is negative
     */
    public void heal(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Healing cannot be negative: " + amount);
        }
        if (!isAlive()) {
            return;
        }
        health = Math.min(MAX_HEALTH, health + amount);
    }

    /** @return true while health is above 0 */
    public boolean isAlive() {
        return health > 0;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    /** @return the name plus a word (Good, Fair, Poor or Dead) instead of a number */
    @Override
    public String describe() {
        String status;
        if (health >= 70) {
            status = "Good";
        } else if (health >= 40) {
            status = "Fair";
        } else if (health > 0) {
            status = "Poor";
        } else {
            status = "Dead";
        }
        return name + ": " + status;
    }
}
