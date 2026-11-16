# Model package

This is where all the data and rules of the game live: the people in the party, the wagon, the supplies, and the store. Most of these classes never print anything. The only exception is `Store`, which talks to the player through `ConsoleUI`.

## What's in here

| File | Type | What it's for |
| --- | --- | --- |
| `Describable` | interface | Lets anything describe itself in one line with `describe()` |
| `Person` | class | One party member (name + health) |
| `Party` | class | The group of `Person` objects |
| `Wagon` | class | Supplies, miles traveled, pace, and rations |
| `Inventory` | class | Keeps track of how much of each item we have |
| `Store` | class | Where the player buys supplies |
| `ItemType` | enum | Items you can buy, with their price and unit |
| `Pace` | enum | How fast the party travels |
| `Rations` | enum | How much the party eats each day |

## Describable
Just one method, `describe()`, that returns a short line the player can read. `Person`, `Wagon`, `TrailEvent` and `Location` all implement it, so the game can print any of them the same way. That's our polymorphism example.

## Person
One traveler. Health is private, so the only way to change it is through `damage` and `heal`. That's our encapsulation example.
- Health always stays between 0 and `MAX_HEALTH` (100). If you pass a weird starting value to the constructor, it gets clamped.
- `damage(amount)` lowers health but never goes below 0. A negative amount throws an `IllegalArgumentException`.
- `heal(amount)` raises health but never goes above 100. Negative amounts throw too. Also, dead people can't be healed, so once you're at 0 you stay there.
- `isAlive()` is true as long as health is above 0.
- `describe()` shows a word instead of a number: Good (70+), Fair (40 to 69), Poor (1 to 39), or Dead (0).

## Party
Holds the list of `Person` members.
- `addMember(person)` adds someone to the party.
- `getAlive()` gives back only the people who are still alive.
- `isWipedOut()` is true when nobody is alive. The game uses this as one of the ways you lose.
- `applyDailyHealth(rations, pace)` adds up the rations cost and the pace cost. If the total is positive, every living member takes that much damage. If it's negative, they heal instead.
- `getMembers()` returns a read-only copy of the list so other classes can't mess with it.

## Inventory
Keeps track of the supplies. We used an `EnumMap` since the keys are a fixed enum, which is a bit faster and smaller than a regular `HashMap`. Every item starts at 0.
- `add(type, amount)` adds supplies. Negative amounts throw an `IllegalArgumentException`.
- `remove(type, amount)` takes supplies away, and throws the checked `InsufficientSuppliesException` if we don't have enough.
- `removeUpTo(type, amount)` takes as much as it can without ever throwing, and returns how much it actually took. We added this so events like `Thief` and `WagonBreakdown` don't need try/catch blocks.
- `get(type)` tells you how many of an item we have.

## Wagon
Owns the `Inventory` and keeps track of miles traveled, the current `Pace`, and the current `Rations`.
- `travelOneDay()` moves the wagon forward by the pace's miles per day. If there are no oxen, it doesn't move at all.
- `feed(party)` uses up one day of food (pounds per person times the number of living members). If there isn't enough, the party eats whatever is left and every living member takes 10 damage. It returns `true` if everyone ate enough and `false` if they went hungry.
- `setPace` and `setRations` let the player change how they travel and eat.
- `describe()` gives a line like `Miles traveled: 120 | Pace: STEADY | Rations: FILLING`.

## Store
Used at the start of the game and at forts.
- `buy(type, qty, game)` charges `price x qty` through `game.spendMoney`. That throws `InsufficientFundsException` if the player can't pay. If it works, the items get added to the wagon's inventory.
- `open(game)` shows a menu with every item and its price. After picking an item, the player types a quantity (0 cancels), and it's capped at what they can afford. You can't leave the store if the wagon has zero oxen, since the game would be over right away.

## Enums

**ItemType** (prices are just starting values, we'll tune them once we can playtest)

| Item | Price | Unit |
| --- | --- | --- |
| FOOD | $1 | pound |
| OXEN | $40 | ox |
| CLOTHING | $10 | set |
| AMMUNITION | $2 | box |
| SPARE_PART | $10 | part |

**Pace** (faster = more miles, but more health lost)

| Pace | Miles per day | Health cost |
| --- | --- | --- |
| STEADY | 12 | 0 |
| STRENUOUS | 16 | 3 |
| GRUELING | 20 | 6 |

**Rations** (eating less costs more health, and a negative cost means health goes up)

| Rations | Pounds per person per day | Health cost |
| --- | --- | --- |
| FILLING | 3 | -2 |
| MEAGER | 2 | 1 |
| BARE_BONES | 1 | 4 |

## How it all fits together in one day
1. `Wagon.travelOneDay()` moves the wagon based on the current `Pace`.
2. `Wagon.feed(party)` takes food out of the `Inventory` based on `Rations`.
3. `Party.applyDailyHealth(rations, pace)` changes everyone's health.
4. Events and stops can then change `Inventory`, `Person` and `Party`.

## OOP stuff we used here
- **Encapsulation:** `Person` health and `Inventory` counts are private and can only be changed through methods that check the input first.
- **Abstraction and polymorphism:** `Describable` lets different classes be printed the same way.
- **Composition:** `Wagon` has an `Inventory`, and `Party` has a list of `Person` objects.
- **Enums with data:** `ItemType`, `Pace` and `Rations` keep their numbers in one place, so tuning the game is just editing one line.
- **Checked exceptions:** `remove` and `buy` throw exceptions the caller has to handle, so running out of supplies or money can't be ignored.