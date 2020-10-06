package cellsociety;

import Model.Model;
import View.View;
import javafx.application.Application;
import javafx.stage.Stage;
import Model.GameOfLifeModel;


/**
 * This class initializes the application.
 *
 * @author Shane Wang
 */
public class Game extends Application {

    public static final String TITLE = "GameOfLife";
    private Model myModel;

    @Override
    public void start(final Stage stage) {
        myModel = new GameOfLifeModel("ConwayStatesPulsar.csv");
        View myView = new View(myModel);
        stage.setScene(myView.setupScene());
        stage.setTitle(TITLE);
        stage.show();
        myView.initializeAnimation();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
