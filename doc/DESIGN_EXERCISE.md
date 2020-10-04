# Simulation Lab Discussion
## Names and NetIDs
Alex Jimenez (ajj29)
Christopher Shin (csj97)
Arjun Peri (ap458) 

## Rock Paper Scissors

### High Level Design Ideas
    * Game class
    * Players class
    * Turn class
    * Rules class

### CRC Card Classes

*Game

This class's purpose or value is to manage the game and allow the other game's classes to interact:
```java
 public class Game {
     public Rules setGameRules ()
     public void nextTurn()
     
 }
```

 * Player

This class's purpose or value is to manage hold a players information, including score and their next gesture

```java
 public class Player {
     public int getPlayerScore()
     public void setNextGesture()
     public void updatePlayerScore()
     public boolean hasWon()
 }
```

 * Turn

This class's purpose or value is to compare the players gesture in the current turn and to collect
user input to determine their gestures in the next turn
```java
 public class Turn {
     public gesture getNextGesture()
     public void comparePlayerGestures()
     
 }
```



Rules

This class's purpose or value is to store the rules for the currently running interaction of the game.
These rules dictate what gestures beat what other gestures
```java
 public class Game {
     public player compareGestures()
     public void setRulesMapping()
 }
```


### Use Cases

### Use Cases

 * A new game is started with five players, their scores are reset to 0.
   * The current turn class would need to be overriden to compare a group of players instead of just two.
   This should be as simple as a loop to loop over the player's hands

 * A player chooses his RPS "weapon" with which he wants to play for this round.
   * This is handled in the setNextGesture() method of the Player class

 * Given three players' choices, one player wins the round, and their scores are updated.
   * The comparison of these choices is made in the compareGestures() method of the turn class and each 
   player's score is updated with the updatePlayerScore method of the player class. The hasWon() method
   determines if the player has reached the winning score

 * A new choice is added to an existing game and its relationship to all the other choices is updated.
 

 * A new game is added to the system, with its own relationships for its all its "weapons".


## Cell Society

### High Level Design Ideas
* A grid of cell objects which store their state, as well as a variable to 
    store their next state. The cells are manipulated by a model class, which is responsible for 
    storing simulation rules and updating cell states. Each simulation will have its own model class,
    specific to the simulation rules. The view class will be responsible for displaying the simulations state.
    Each simulation will have its own theme class, which will be used by the view class to determine what
    color/object is displayed for each simulation's states. 

### CRC Card Classes

Cell


| Responsibilities      | Collaborators    |
| :------------- | :----------: |
|  Knows State   | Model   |
| Knows Location   | Grid |
| Stores Next State   | ViewController |

Grid


| Responsibilities      | Collaborators    |
| :------------- | :----------: |
|  Stores Cells | Cell |
|   | Model |
|   | ViewController |

Model


| Responsibilities      | Collaborators    |
| :------------- | :----------: |
|  Handle Rule Logic | Cells   |
| Update Cells   | Grid |
| Store Possible States   | ViewController |

Theme


| Responsibilities      | Collaborators    |
| :------------- | :----------: |
|  Store Cell Colors | ViewController  |


View


| Responsibilities      | Collaborators    |
| :------------- | :----------: |
|  Fetch Cell States | Model  |
| Display Grind  | Grid |
| | Cell |
|   | Theme |

Controller


| Responsibilities      | Collaborators    |
| :------------- | :----------: |
|  Handle User Input | Model  |
| Manipulate Model Class | Grid |

### Use Cases

* Apply the rules to a cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all of its neighbors)
```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```

* Move to the next generation: update all cells in a simulation from their current state to their next state
```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```

* Switch simulations: load a new simulation from a data file, replacing the current running simulation with the newly loaded one
```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```