simulation
====

This project implements a cellular automata simulator.

Names:
- Alex Jimenez (ajj29)
- Arjun Peri (ap458)
- Christopher Shin (cjs97)

### Timeline

Start Date: 10/2/2020

Finish Date: 10/20/2020

Hours Spent: 
30 hrs/week * 3 weeks = 90 hrs

### Primary Roles
- Alex: Handled visualization and Controller.
- Arjun: Worked with exceptions and configuration. 
- Chris: Responsible for Model and simulations. 


### Resources Used
- Course website - Some starter code with setting up GUI elements was taken and modified from works of Dr. Robert Duvall.
- Stack overflow 
	- Used [this](https://howtodoinjava.com/java/io/read-write-properties-file/) for help  with property file writing. 
	- Used [this](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Dialog.html) for help with dialog box. 
	- Used [this](https://dzone.com/articles/implementing-custom-exceptions-in-java) for help with custom exceptions. 
- Class Piazza board
- Office hour TA's - Ben Xu, Christina Chen

### Running the Program

Main class: 

To run the program, simply run main in the SimulationRunner.java class.

Data files needed: 
- Properties file and csv file for each initial state configuration of each simulation. Each property file contains the key "fileName" that links the corresponding csv file.
- Default files for each of the 6 simulaiont types that hold default values for potential missing keys (see assumptions).
- Language Resource files in the src/Resources folder
- CSS files in the src/Resources folder

Features implemented:
- Main menu with file input text box.
- Splash screen with selected simulaion info, home button, and start button.
- Simulation visualizer
    - home button
    - save button which allows writing out to a properties and csv file. 
    - Pause button 
    - Step button
    - Change color button which allows user to specify the color of a certain state. 
    - Show graph button which pops up a graph on a separate window displaying the frequencies of each cell state. 
    -  Speed slider to dynamically change the speed of the simulation.
- Error checking so that the program doesn't crash and descriptive error messages to indicate where errors have occured. 

### Notes/Assumptions

Assumptions or Simplifications:
- To start a simulation, run the program and enter into the resulting text box one of the properties files listed in the Properties folder. For example, input "ConwayStatesPulsar" to run the Game of Life simulation with Pulsar configuration. 
- To access and reference different simulations, we use a variable called modelType throughout the program. There are 6 different possible values of modelType for each of the 6 different simulations: GameOfLife, Percolation, RPS, Segregation, SpreadingFire, WaTor. In order to add a new simulation to the program, one needs to make a properties file that has the key "Type" that equals one of these 6 modelType values. The "Type" key is the only key in the properties file that will not be set to a default value if nonexistent, as retreiving the modelType is one of the most integral steps in chossing and starting a simulatoin. 
- One assumption we make is that there will be a default properties file that exists for each type of simulation, and that is accurately named and populated. The default file should be named modelType + "Default.properties", where the modelType is one of the 6 different simulation prefixes.

Interesting data files:
- RPS50
- WaTor50

Known Bugs:
- Bottom row of cells are cut off on the screen. This may have to do with BorderPane setup and menu panel at the top messing with the screen/grid fitting.

Extra credit:
- Completed part of optional configuration specifications. 


### Impressions
- Arjun: Overall, I thought this project was a great introduction to front end development as well a great opportunity to continue building on JavaFx skills. Learning about GUI elements, css, and properties files was tough at first but the iterative nature of the project made it manageable.
- Alex: I also enjoyed the project and thought it was a good intro to front end development. Since I was primarily in charge of the view and vizualization, I got to spend alot of time with the GUI, css, and property files which are all great to know about for future UI development. The usage of the MVC model was difficult to understand at first, but as the project progressed I think we were able to follow it better and actually see its advantages.
- Chris: I think this project was really challenging but interesting.  The MVC model was quite difficult to absorb at first, but in the end it made development way easier, as we could all work on different things without worrying about breaking anything.  I spent the majority of my time on the backend, which I thought had it's own challenges, but was fun to work on.  The abstractions/how to structure the backend was especially challenging, but I'm sure the intuition for how to structure it will come with more experience.

