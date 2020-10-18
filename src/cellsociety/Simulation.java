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
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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

  public static final String TITLE = "Cellular Automata";

  public static final Paint BACKGROUND = Color.AZURE;
  public static final double FRAMES_PER_SECOND = 60;
  public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  public static final double SCENE_WIDTH = 800;
  public static final double SCENE_HEIGHT = 800;
  private Controller mainController;

  @Override
  public void start(final Stage stage) {
    mainController = new Controller();
    stage.setScene(mainController.setupScene());
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
    mainController.gameStep();
  }

  public static void main(String[] args) throws IOException {
//    try (CSVWriter csvWriter = new CSVWriter(new FileWriter("data/RPSExample.csv"))) {
//      int size=40;
//      String[] rowsAndColumns = new String[] {Integer.toString(size), Integer.toString(size)};
//      Random random = new Random();
//      csvWriter.writeNext(rowsAndColumns,false);
//      for(int i=0; i<size;i++) {
//        String[] currentRowStates = new String[size];
//        for(int j=0;j< size;j++) {
//          currentRowStates[j] = Integer.toString(random.nextInt(5));
//        }
//        csvWriter.writeNext(currentRowStates,false);
//      }
//    } catch (IOException e) {
//      e.printStackTrace();
//    }

    launch(args);
  }
}
