# Events Package Documentation

Random things that can happen on a day of travel. The `Game` asks the `EventGenerator` once per day whether something happens. If so, it calls that event’s `apply(game)`, and the event changes the game state and prints what happened. The `Game` never needs to know which event it got (utilizing abstraction and polymorphism).

---

## Core Classes

### `TrailEvent.java`
The abstract parent of every event.
* Stores a `name` and a `weight` (how likely the event is compared to others).
* Requires every subclass to implement its own `apply(Game game)`.
* Implements `Describable`, so `describe()` returns the event’s name.

### `EventGenerator.java`
Decides if an event happens today and which one.
* Each day there is a 25% chance of any event (`DAILY_EVENT_CHANCE`).
* If an event happens, it is picked by weight: higher weight means it comes up more often. The code adds up all the weights, picks a random number below the total, then walks the list subtracting weights until the number drops below 0.
* Returns an `Optional<TrailEvent>`, which is empty on days when nothing happens.
* Accepts a seeded `Random` in its constructor so tests can get the same events every run.
* Holds one of each event: `Illness`, `BadWeather`, `FindWildFood`, `WagonBreakdown`, `Thief`, `FriendlyTraveler`, `LostOx`.

---

## Event Breakdown

* **`Illness.java` (Weight: 30)**
  One random living party member gets sick and loses 10 to 30 health. It prints who got sick and with what (dysentery, cholera, typhoid, measles, exhaustion, or a fever). If that person’s health reaches 0, it prints that they died.

* **`BadWeather.java` (Weight: 25)**
  A random storm (thunderstorm, blizzard, hailstorm, freezing rain) hurts everyone alive. Each person loses 5 health, or 12 if the wagon has fewer sets of clothing than living people. It prints a message for anyone who does not survive.

* **`FindWildFood.java` (Weight: 25)**
  A good event. The party finds berries and roots and gains 20 to 60 pounds of food.

* **`WagonBreakdown.java` (Weight: 20)**
  A wheel breaks.
    * If the party has a spare part, one is used and the wagon is fixed right away.
    * If not, there is a 25% chance the party is stranded, which ends the game.
    * Otherwise, the repair takes 2 to 5 days. Those days still use food and affect health.

* **`Thief.java` (Weight: 10, new)**
  A thief steals 10% to 30% of the food in the wagon (at least 1 pound). If there is no food, it prints that the thief found nothing.

* **`FriendlyTraveler.java` (Weight: 10, new)**
  A good event. A passing traveler gives the party $10 to $40.

* **`LostOx.java` (Weight: 10, new)**
  An ox wanders off and is lost for good. If the party has only one ox left, it is “found again” instead, so this event alone can never end the game.