# Simulation Design Plan
### Team Number
### Names


## Design Overview

 * Design Overview. Describe the classes you intend to create, without revealing any specific implementation details 
 (like data structures, file formats, etc.). CRC cards can be a very useful way to develop your design.
  
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

 * Design Details. Describe how your classes work together to handle specific features (like collaborating 
 with other classes, using resource files, or serving as an abstraction that could be extended). 
 Use Cases can be a very useful way to help make your descriptions more concrete.
    * At the core of the grid we have cell objects which store their state, as well as a variable to 
    store their next state. The cells are manipulated by the model class, which is responsible for 
    storing simulation rules and updating cell states. Each simulation will have its own model class,
    specific to the simulation rules. The view class will be responsible for displaying the simulations state.
    Each simulation will have its own theme class, which will be used by the view class to determine what
    color/object is displayed for each simulation's states. 
 * Design Considerations. Describe at least two design issues your team encountered (even if you have 
 not yet resolved it). Address any alternatives the team considered including pros and cons from all sides of the discussion.
    * How/where to store and handle the rules for different simulations
        * In the model class
            * We are leaning towards this implementation as it lends itself well to abstraction
        * In a separate rules class
        * Within the simulation's cells
    * How/where to deal with the cell's current state vs next state to properly handle cell updating
       * Instance Variable in cell class
       * A copy of the cell grid

## Design Details

Here is a graphical look at my design:

![This is cool, too bad you can't see it](online-shopping-uml-example.png "An initial UI")

made from [a tool that generates UML from existing code](http://staruml.io/).


## Design Considerations


## User Interface

Here is our amazing UI:

![This is cool, too bad you can't see it](29-sketched-ui-wireframe.jpg "An alternate design")

taken from [Brilliant Examples of Sketched UI Wireframes and Mock-Ups](https://onextrapixel.com/40-brilliant-examples-of-sketched-ui-wireframes-and-mock-ups/).


## Team Responsibilities

 * Team Member #1

 * Team Member #2

 * Team Member #3
