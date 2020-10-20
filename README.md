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
25 hrs/week * 3 weeks = 75 hrs

### Primary Roles


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

### Notes/Assumptions

Assumptions or Simplifications:
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

