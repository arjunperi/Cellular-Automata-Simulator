package Controller;

import static org.junit.jupiter.api.Assertions.*;

import Model.GameOfLifeModel;
import View.GraphView;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class GraphControllerTest extends DukeApplicationTest {

  private GameOfLifeModel mainModel;
  private GraphView graphView;
  private GraphController graphController;
  Map<Integer, String> stateColorMap = new HashMap<>();


  public void start(final Stage stage) {
    stateColorMap.put(0,"Pink");
    stateColorMap.put(1, "Cyan");
    mainModel = new GameOfLifeModel("ConwayStatesBlinker.csv", "GameOfLife");
    graphController = new GraphController(mainModel, stateColorMap);
    graphView = graphController.getGraphView();
  }

  @Test
  public void testCreateGraph(){
    Scene currentScene = graphController.getGraphView().getStage().getScene();
    assertNotNull(currentScene.lookup(".series0"));
    assertNotNull(currentScene.lookup(".series1"));

  }

  @Test
  public void testCorrectPointsOnGraph(){
    Scene currentScene = graphController.getGraphView().getStage().getScene();
    assertNull(currentScene.lookup("#1,3"));
    javafxRun(() -> graphController.updateGraph());
    assertNotNull(currentScene.lookup("#1,3"));
    assertNotNull(currentScene.lookup("#0,397"));
  }
}