package cellsociety;

import Controller.Controller;
import View.View;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import Model.*;
import javafx.util.Duration;


/**
 * This class initializes the application.
 *
 * @author Alex Jimenez
 * @author Arjun Peri
 * @author Christopher Shin
 */
public class Simulation extends Application {

  public static final String TITLE = "Percolation";

  public static final double FRAMES_PER_SECOND = 60;
  public static final double FRAMES_PER_MODEL_UPDATE = 20;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  public static final double SCENE_WIDTH = 800;
  public static final double SCENE_HEIGHT = 800;
  private Model mainModel;
  private View mainView;
  private Controller mainController;

  @Override
  public void start(final Stage stage) {
    String modelType = "GameOfLife";
    mainModel = new Model("ConwayStatesPulsar.csv", modelType);
    mainView = new View(mainModel, modelType);
    mainController = new Controller(mainModel);
    stage.setScene(mainView.setupScene());
    stage.setTitle(TITLE);
    stage.show();
    stage.getScene().setOnKeyPressed(e -> mainController.handleKeyInput(e.getCode()));
    initializeAnimation();
  }

  /**
   * Initializes the display's animation.
   */
  private void initializeAnimation() {
    KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> gameStep());
    Timeline animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  private void gameStep() {
    mainModel.modelStep();
    mainView.viewStep();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
