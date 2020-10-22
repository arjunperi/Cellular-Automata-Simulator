package cellsociety;

import Controller.Controller;
import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * This class initializes the application.
 *
 * @author Alex Jimenez
 * @author Arjun Peri
 * @author Christopher Shin
 */
public class SimulationRunner extends Application {

  public static final String TITLE = "Cellular Automata";

  public static final Paint BACKGROUND = Color.AZURE;
  public static final double FRAMES_PER_SECOND = 60;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  public static final double SCENE_WIDTH = 800;
  public static final double SCENE_HEIGHT = 800;
  private Controller mainController;

  /**
   * This class starts and runs the simulation by creating a Controller
   *
   * @param stage The stage upon which the main simulation is ran
   */
  @Override
  public void start(final Stage stage) {
    mainController = new Controller(stage);
    stage.setScene(mainController.setupScene());
    stage.setTitle(TITLE);
    stage.show();
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
    mainController.gameStep();

  }

  public static void main(String[] args) throws IOException {
    launch(args);
  }
}
