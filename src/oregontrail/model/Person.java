package oregontrail.model;

import java.util.Objects;

public class Person implements Describable {

    public static final int MAX_HEALTH = 100;

    private final String name;
    private int health;

    // Creates a new traveler with full health.
    public Person(String name) {
        this(name, MAX_HEALTH);
    }

    // Creates a traveler with a specific starting health.
    public Person(String name, int health) {
        this.name = Objects.requireNonNull(
                name, "Name cannot be null"
        );

        if (name.isBlank()) {
            throw new IllegalArgumentException(
                    "Name cannot be blank"
            );
        }

        this.health = Math.max(
                0, Math.min(MAX_HEALTH, health)
        );
    }

    // Lowers health without allowing it to go below zero.
    public void damage(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException(
                    "Damage cannot be negative"
            );
        }

        health = Math.max(0, health - amount);
    }

    // Restores health but never above the maximum.
    public void heal(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException(
                    "Healing cannot be negative"
            );
        }

        if (!isAlive()) {
            return;
        }

        health = Math.min(MAX_HEALTH, health + amount);
    }

    // Checks whether the traveler is still alive.
    public boolean isAlive() {
        return health > 0;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    // Gives the player a simple description of the traveler's condition.
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
