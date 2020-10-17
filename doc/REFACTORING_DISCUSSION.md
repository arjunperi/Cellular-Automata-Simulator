## Refactoring Discussion
### Team 14
### Names
Alex Jimenez
Arjun Peri
Christopher Shin


### Issues in Current Code
* Passing around grid and references to its data structure (List<List<Cell>>)
* Parallel abstractions in the Model package that require model and cell to be extended/changed
whenever a new simulation type is implemented. The current model abstraction is underutilized
and likely unnecessary seeing that it is only overridden for the Segregation Simulation. 
* Feels like controller is doing too much
    * We want to make a separate controller menu class
* We still have text for the GUI that is defined in the code we need to key it from the resource files.
* Other random small issues that should be easier to fix such as temporary magic numbers, small bits
of duplication, and complex conditional statements.
* Grid does too much, stores the grid structure and reads/writes files

#### Model Package Cell and Model Classes
 * Design issues
    * We have an inheritance hierarchy for the model classes that must be extended for every new model, but
    that currently is only ever overridden for one simulation.
 * Design issue
    * Parallel hierarchies, overridden code.
#### Entire Controller Class
 * Design issues
    * The controller class currently has too many responsibilities, and is working against the MVC layout 
    we have implemented. The controller has too much of a part in adding buttons and UI to the scene. It should
    instead delegate this to the view and only worry about the backend functionality of the user iteraction.
 * Methods
    * initializeButtonMenu()
    * displayInfo()
    * makeButton()
    * showError()
    * initializeSimulationMenu()
    * etc.
 * Goes against the single responsibility principle, and the MVC model structure. 
    
    

### Refactoring Plan

 * What are the code's biggest issues?
    * The biggest and most glaring issues with our code currently are the parallel abstractions in the
    model package and the duplcated/cluttered nature of the controller class. 
 * Which issues are easy to fix and which are hard?
    * The abstractions in the model area easy to fix once we actually decide what implementation we want
    to follow to fix them. We are still deciding if we prefer to have an abstract model or cell class,
    but once we choose it should be easy to move the methods from one to another.
    * The controller clutteredness is a more difficult fix. We are struggling conceptually to decide what should
    be in the controlled and what should be in the view. Additionally, there is a conflict with items that 
    we want to move to the view, but they call methods that are part of controller. This is not compatible with our
    current structure because there is no instance of controller in the view class, and we cannot add one
    because this would lead to poor dependency structure. 
 * What are good ways to implement the changes "in place"?
    * Some of the clutteredness in the controller can be solved by extracting code that is common to multiple methods. 
    For example, we could pull out a method that makes a button when given an event instead of remaking buttons
    every time. 

### Refactoring Work

We spent a lot of time in the lab discussing design questions with a TA and only got to one refactoring
item. However, now we have a good list and understanding of items to refactor over the weekend. 

 * Issue chosen: Fix and Alternatives
    * 

