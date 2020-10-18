package View;

import static org.junit.jupiter.api.Assertions.*;


import Controller.GraphController;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

class GraphViewTest extends DukeApplicationTest {

  private GraphView graphView;
  private GraphController graphController;
  Map<Integer, String> stateColorMap = new HashMap<>();
  private Stage stage;
  private Scene scene;

  public void start(final Stage stage) {
    stateColorMap.put(0,"Pink");
    stateColorMap.put(1, "Cyan");
    graphView = new GraphView(stateColorMap);
    graphView.createLineChart();
    this.stage = graphView.getStage();
  }

  @Test
  public void testGraphStartUp(){
    javafxRun(() -> graphView.createGraphWindow());
    this.scene = stage.getScene();
    assertTrue(null != scene.lookup("#graph"));
  }

  @Test
  public void testLinesAddedToScene(){
    XYChart.Series testSeries = new Series();
    graphView.addLinesToGraph(Arrays.asList(testSeries));
    javafxRun(() -> graphView.createGraphWindow());
    this.scene = stage.getScene();
    assertTrue(null != scene.lookup(".series0"));
    assertTrue(null == scene.lookup(".series1"));
  }

  @Test
  public void testAddPointToLine(){
    XYChart.Series testSeries = new Series();
    graphView.addLinesToGraph(Arrays.asList(testSeries));
    javafxRun(() -> graphView.createGraphWindow());
    this.scene = stage.getScene();
    assertTrue(null == this.scene.lookup("#1,100"));
    javafxRun(() ->graphView.addPointToLine(1,100,1, testSeries));
    assertTrue(null != this.scene.lookup("#1,100"));
  }

  @Test
  public void testPointColors(){
    XYChart.Series testSeries = new Series();
    graphView.addLinesToGraph(Arrays.asList(testSeries));
    javafxRun(() -> graphView.createGraphWindow());
    this.scene = stage.getScene();
    javafxRun(() ->graphView.addPointToLine(1,100,1, testSeries));
    Node dataPoint = this.scene.lookup("#1,100");
    String cyanValue = Color.web(stateColorMap.get(1)).toString().substring(2, 8);
    assertTrue(dataPoint.getStyle().contains(cyanValue));
    javafxRun(() -> graphView.addPointToLine(1,100,0, testSeries));
    Node dataPoint2 = this.scene.lookup("#0,100");
    String pinkValue = Color.web(stateColorMap.get(0)).toString().substring(2, 8);
    assertTrue(dataPoint2.getStyle().contains(pinkValue));
  }

  @Test
  public void testUpdateColorMapping(){
    XYChart.Series padderSeries = new Series();
    javafxRun(() -> graphView.addLinesToGraph(Arrays.asList(padderSeries)));
    javafxRun(() -> graphView.createGraphWindow());
    this.scene = stage.getScene();
    XYChart.Series testSeries = new Series();
    testSeries.setName("state: "+1);
    javafxRun(() -> graphView.addLinesToGraph(Arrays.asList(testSeries)));
    javafxRun(() ->graphView.addPointToLine(1,100,1, testSeries));
    javafxRun(() -> graphView.showGraph());
    Node dataPoint = this.scene.lookup("#1,100");
    String cyanValue = Color.web(stateColorMap.get(1)).toString().substring(2, 8);
    assertTrue(dataPoint.getStyle().contains(cyanValue));
    stateColorMap.put(1,"Red");
    javafxRun(() -> graphView.setLineStyle(1));
    javafxRun(() -> graphView.showGraph());
    dataPoint = this.scene.lookup("#1,100");
    String redValue = Color.web(stateColorMap.get(1)).toString().substring(2, 8);
    assertTrue(dataPoint.getStyle().contains(redValue));
  }


}