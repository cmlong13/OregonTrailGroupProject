# Week 1 GitHub issues (due Nov 6; project due Nov 22)

Ten issues, one per chunk of TODOs in the starter project. Paste each title and body into a new issue and assign it to the owner. In every file, search for `TODO (model owner)`, `TODO (events owner)` or `TODO (game owner)` to find the exact spots.

Owners: **A** = model, **B** = events and trail, **C** = UI, Store and Game.

---

## Person A: model

### 1. Person and Party health (by Nov 6)

**Owner:** A
**Files:** `model/Person.java`, `model/Party.java`

Make health work for each traveler and for the whole party.

- [ ] `damage()` and `heal()` reject negative amounts and keep health between 0 and 100
- [ ] `isAlive()` is true only while health is above 0
- [ ] `getAlive()` returns only living members; `isWipedOut()` is true when nobody is alive
- [ ] `applyDailyHealth()` applies the rations and pace health costs to every living member
- [ ] `describe()` shows a word (Good, Fair, Poor, Dead) instead of a number
- [ ] Javadoc on every public method

### 2. Inventory (by Nov 6)

**Owner:** A
**Files:** `model/Inventory.java`

Track supplies safely so counts can never go negative.

- [ ] Every item starts at 0
- [ ] `add()` rejects negative amounts
- [ ] `remove()` throws `InsufficientSuppliesException` with a clear message when there isn't enough
- [ ] `get()` returns the current count
- [ ] Add a comment explaining why `EnumMap` is used (it's on the rubric for efficiency)

### 3. Wagon travel and feeding (by Nov 6)

**Owner:** A
**Files:** `model/Wagon.java`
**Needs:** issues 1 and 2

- [ ] `travelOneDay()` adds the pace's miles, or doesn't move if there are no oxen
- [ ] `feed()` removes rations x living members in food
- [ ] When food runs short, the party eats what's left and everyone takes damage
- [ ] `feed()` catches `InsufficientSuppliesException` rather than letting it crash the game

---

## Person B: events and trail

### 4. Trail stops (by Nov 6)

**Owner:** B
**Files:** `trail/Trail.java`

- [ ] The constructor adds the landmarks, the fort and the destination (suggested list is in the TODO)
- [ ] `nextStop()` uses the `TreeMap` to find the first stop at or after a mile
- [ ] `stopsPassed()` returns every stop passed in one day, in order, even if a day skips past two
- [ ] Add a comment explaining why `TreeMap` is used

### 5. Arriving at stops (by Nov 6)

**Owner:** B
**Files:** `trail/Landmark.java`, `trail/Fort.java`, `trail/Destination.java`

Each subclass handles arrival its own way (this is the polymorphism example for the presentation).

- [ ] Landmark prints its name and description
- [ ] Fort asks whether to shop and opens the store on yes
- [ ] Destination prints the win message and tells the game the party arrived

### 6. Random event roll (by Nov 6)

**Owner:** B
**Files:** `events/EventGenerator.java`

- [ ] The event list holds one of each event subclass
- [ ] `rollForEvent()` returns nothing about 75% of days (`DAILY_EVENT_CHANCE`)
- [ ] When an event happens, it's picked by weight, so higher weights come up more often
- [ ] A seeded `Random` gives the same results every run (useful for testing in week 2)

### 7. The four events (by Nov 6)

**Owner:** B
**Files:** `events/Illness.java`, `events/WagonBreakdown.java`, `events/BadWeather.java`, `events/FindWildFood.java`

- [ ] Illness: a random living member gets sick and takes damage; print who
- [ ] Wagon breakdown: use a spare part if there is one, otherwise lose time
- [ ] Bad weather: everyone takes a little damage, more if clothing is low
- [ ] Find wild food: add some food
- [ ] Each one tells the player what happened

---

## Person C: UI, Store and Game

### 8. Console input and menus (by Nov 6)

**Owner:** C
**Files:** `ui/ConsoleUI.java`

Do this one first, since everything else uses it.

- [ ] `showMenu()` prints a title and numbered options and returns the choice
- [ ] `readInt()` never crashes: it re-asks on letters, blanks and out-of-range numbers, and says what's allowed
- [ ] `readName()` re-asks on a blank line
- [ ] `pause()` waits for Enter

### 9. Store and money (by Nov 6)

**Owner:** C
**Files:** `model/Store.java`, `Game.java` (`spendMoney`)

- [ ] `spendMoney()` throws `InsufficientFundsException` when the player can't afford something
- [ ] `buy()` charges price x quantity and adds the items to the wagon
- [ ] `open()` shows items, prices and money left, and loops until the player leaves
- [ ] A purchase the player can't afford shows a friendly message and doesn't crash

### 10. Game loop (by Nov 6)

**Owner:** C
**Files:** `Game.java`
**Needs:** issues 8 and 9; uses the others as they land

Follow the game loop diagram in the design doc.

- [ ] `start()` names the party, opens the store, plays days until the game is over, then shows win or lose
- [ ] `playDay()` shows the status and daily menu, handles pace, rations, check supplies and rest, then travels, rolls for an event, and handles stops passed
- [ ] `isOver()` is true when the party arrived, is wiped out, or has run out of food or oxen
- [ ] `markArrived()` records the win

---

## Labels and board (optional)

- Labels: `model`, `events`, `game`, `week 1`
- Projects board columns: To do, In progress, Done
- Close each issue from its pull request by writing "Closes #N" in the PR description
