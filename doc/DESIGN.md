# cellSociety Design Final
### Names

## Team Roles and Responsibilities

 * Arjun Peri
    * Worked mainly on the controller of the project, specifically in regard to error handling and file
    input/output.

 * Alex Jimenez
    * Worked mainly on the view and controller, specifically to implement the visualization aspects of the project.
    Worked on Model the first stage to implement Game of Life. 

 * Christopher Shin
    * Backend development with in the Model package. Specifically managed the model and cell hierarchies
    and completely implemented all simulations other than Game Of Life. 


## Design goals

#### What Features are Easy to Add
   * New simulations, new CSV configurations files, new properties files, simulations with different 
   colors and sizes, different themes for the UI, more graph windows/types of graphs.

## High-level Design
   * Our program is designed to adhere to the MVC model. All of the simulation rules, cell interactions, 
   and state updates are handled in the model package. All handling of the stage/scene and displaying of 
   javaFX elements is done in the view. And finally the controller handles all user interactions and mediates 
   data between the model and view. The controller is basically the central communicator of the simulation 
   system.
#### Core Classes
   * Model
        * The Model package is made up of a cell hierarchy, a model hierarchy, and the grid class. 
        The classes all work together to store the collection of cells and update their states. The
        grid is the actual collection that stores and structures the cells. The specific simulation
        model classes store the rules for the simulation and the cell classes store the states and
        other simulation specific cell information. The simulation model classes are the main unit
        of the model package, and they contain instances of grids which contain cells. The model
        class interacts with the controller class to share its cell state information.

   * Controller
        * The controller package handles the mediation of information between the model and the view 
        packages. The controller class handles information between the model and the view and the 
        graphController performs a similar function between the model and the graphView. The controller 
        fetches state information from the model, converts it to visual information (corresponding cell 
        state colors) and passes this along to the view. It also handles any user input to the view’s 
        graphical elements and affects the model appropriately.

   * View
        * The view package handles what is actually displayed to the user. The view class displays 
        the simulation grid of cells and its menu while the graphView handles the graph that is displayed 
        when requested. The view class is passed information from the controller. The view and controller 
        are very tightly connected as the controller needs to receive information from user input to the 
        fields that the view displays.

## Assumptions that Affect the Design
   * One assumption that we make is that there is an existing default properties file for each of the
    6 simulation types that are currently implemented. Without this default file, the user will have
    to specify all the existing keys for a given simulation’s properties file, otherwise errors will
    be thrown as the getOrDefault() file won’t work properly.
   * Another assumption that we make is that the “Type” key in a given simulation’s properties file 
   is correctly named to be one of the 6 “model types” that work for our code: GameOfLife, Percolation,
   RPS, Segregation, SpreadingFire, WaTor. If someone fails to enter this “Type” key or enters something 
   that doesn’t match one of those 6 model types, the code will throw an error. 

#### Features Affected by Assumptions
 
## Significant differences from Original Plan
   * In our original plan, we stated that the Controller class would manipulate the Model class along
    with Grid, which our current code does do, but we did not plan on the Controller class also interacting
    and manipulating the View. Since our Controller class handles user input (as specified in the our plan),
    and the user input is tightly woven into the user interface, we needed to add the dependency between
    the Controller and View to maintain UI progression and transitions.
   * Another big difference within our current code that was not present in our plan is the idea of 
   having front end and back end cells. This addition was conceived when we realized that having the
   backend (Model) deal with cell colors was violating MVC principles, so made this distinction in 
   order for only the front end (View) to deal with things like colors, thus making the responsibilities
   be more aligned.

## New Features HowTo

#### Easy to Add Features
   * Adding a new simulation requires someone to create a new properties file with all of the appropriate
    keys/required parameters, new CSV files for the simulation, and a new model/cell class in the model 
    package.  The model class will handle the specific rules for updating the cell states for this simulation, 
    as well as parse the parameters as specified in the properties file.
   
#### Other Features not yet Done
   * Multiple block shapes
   * Cells displaying images instead of just colors.
