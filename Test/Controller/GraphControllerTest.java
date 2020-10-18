package Controller;

import static org.junit.jupiter.api.Assertions.*;

import Model.Model;
import View.GraphView;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class GraphControllerTest extends DukeApplicationTest {

  private Model mainModel;
  private GraphView graphView;
  private GraphController graphController;
  Map<Integer, String> stateColorMap = new HashMap<>();


  public void start(final Stage stage) {
    stateColorMap.put(0,"Pink");
    stateColorMap.put(1, "Cyan");
    mainModel = new Model("ConwayStatesBlinker.csv", "GameOfLife");
    graphController = new GraphController(mainModel, stateColorMap);
    graphView = graphController.getGraphView();
  }

  @Test
  public void testCreateGraph(){
    Scene currentScene = graphController.getGraphView().getStage().getScene();
    assertTrue(null != currentScene.lookup(".series0"));
    assertTrue(null != currentScene.lookup(".series1"));

  }

  @Test
  public void testCorrectPointsOnGraph(){
    Scene currentScene = graphController.getGraphView().getStage().getScene();
    assertTrue(null == currentScene.lookup("#1,3"));
    javafxRun(() -> graphController.updateGraph());
    assertTrue(null != currentScene.lookup("#1,3"));
    assertTrue(null != currentScene.lookup("#0,397"));
  }
}