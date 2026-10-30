# Oregon Trail (text-based, Java)

A console version of the classic Oregon Trail, built for our group project.
It uses only the Java standard library.

## Running it in IntelliJ

1. **File > Open** and pick this folder.
2. If IntelliJ asks for an SDK, choose any JDK 17 or newer.
3. Open `src/oregontrail/Main.java` and click the green run arrow.

## How the code is organized

| Package | What's in it |
| --- | --- |
| `oregontrail` | `Main` (entry point) and `Game` (the day-by-day loop) |
| `oregontrail.ui` | `ConsoleUI`: all printing and input checking |
| `oregontrail.model` | `Person`, `Party`, `Wagon`, `Inventory`, `Store`, the `ItemType`/`Pace`/`Rations` enums, `Describable` |
| `oregontrail.events` | Abstract `TrailEvent`, its subclasses, and `EventGenerator` |
| `oregontrail.trail` | Abstract `Location`, `Landmark`/`Fort`/`Destination`, and `Trail` |
| `oregontrail.exceptions` | `InsufficientFundsException`, `InsufficientSuppliesException` |

Every class compiles and the game starts, but the methods are empty: each one has a TODO with pseudocode for what it should do.
Search for `TODO` to see what's left. Each TODO names its owner:

- **model owner:** everything in `oregontrail.model` except `Store`
- **events owner:** everything in `oregontrail.events` and `oregontrail.trail`
- **game owner:** `Game`, `ConsoleUI`, `Store`

## Schedule (due Nov 22)

| Dates | Goal |
| --- | --- |
| Oct 30 to Nov 6 | Fill in the MVP stubs, each person in their own package |
| Nov 7 to 12 | Connect the pieces so the MVP plays start to finish; add unit tests |
| Nov 13 to 17 | Classic features: rivers, hunting, trading, illnesses, score. Feature freeze Nov 17 |
| Nov 18 to 20 | Javadoc, README, OOP pillars sheet, final bug fixes |
| Nov 21 | Rehearse the presentation |

## Working together

- Pull before you start: `git pull`.
- Do each piece of work on its own branch and open a pull request into `main`.
- Naming: `PascalCase` classes, `camelCase` methods and fields, `UPPER_CASE` constants.
- Every public class and method gets a Javadoc comment.
- Anything we use that wasn't covered in class gets a comment explaining it.
