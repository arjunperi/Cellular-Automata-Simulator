package View;

import cellsociety.SimulationRunner;
import java.util.Collection;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Alex Jimenez
 *
 * The graphView class that creates the scene and visual javaFX objects to display the state concentrations
 * graph
 */
public class GraphView {

  private static final String OPEN_SIMULATION_BUTTON = "OpenSimulationButton";
  private static final String GRAPH_TITLE = "Graph";
  private static final String GRAPH_ID = "graph";
  private static final String FX_STROKE = "-fx-stroke: #";
  private static final String FX_BACKGROUND = "-fx-background-color: #";
  private static final String WHITE = ", white";
  private static final String DOT_SERIES = ".series";
  private static final String SEMICOLON = "; ";
  private static final int TWO = 2;
  private static final int EIGHT = 8;

  private final ResourceBundle projectTextResources;
  private final Map<Integer, String> stateColorMap;
  private final Stage simulationStage;
  private Stage graphStage;
  private LineChart<Number, Number> lineChart;

  /**
   * @param stage The stage upon which the main simulation grid is displayed.
   * @param stateColorMap The state color mappings defined in the model class by the current simulation property file.
   * @param projectTextResources The current text recourse properties file of the simulation being ran.
   */
  public GraphView(Stage stage, Map<Integer, String> stateColorMap,
      ResourceBundle projectTextResources) {
    this.simulationStage = stage;
    this.stateColorMap = stateColorMap;
    this.projectTextResources = projectTextResources;
  }

  /**
   * Creates the visual aspects of the line chart to be displayed. Sets the graph title and id but
   * does not yet add any data
   */
  public void createLineChart() {
    this.graphStage = new Stage();
    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();
    this.lineChart = new LineChart<>(xAxis, yAxis);
    lineChart.getStylesheets().clear();
    lineChart.setTitle(this.projectTextResources.getString(GRAPH_TITLE));
    lineChart.setId(GRAPH_ID);
  }

  /**
   * Adds the series (lines) for each state to the graph. These lines do not yet contain data.
   *
   * @param stateSeries A collection of lines for each state
   */
  public void addLinesToGraph(Collection<XYChart.Series> stateSeries) {
    for (XYChart.Series series : stateSeries) {
      lineChart.getData().add(series);
    }
  }

  /**
   * Displays the graph window upon which the graph is viewed. Initializes the linechart as well
   * as the menu above the graph.
   */
  public void createGraphWindow() {
    Group topGroup = new Group();
    BorderPane root = new BorderPane();
    root.setCenter(lineChart);
    root.setTop(topGroup);
    Scene graphScene = new Scene(root, SimulationRunner.SCENE_WIDTH, SimulationRunner.SCENE_HEIGHT,
        SimulationRunner.BACKGROUND);
    this.graphStage.setScene(graphScene);
    this.graphStage.show();
    Button button = new Button(this.projectTextResources.getString(OPEN_SIMULATION_BUTTON));
    button.setOnAction(event -> this.simulationStage.show());
    topGroup.getChildren().add(button);

  }

  /**
   * Sets the action to occur when the graph window is closed
   *
   * @param event Event passed in from the graphController
   */
  public void setOnGraphClose(EventHandler event) {
    this.graphStage.setOnCloseRequest(event);
  }

  /**
   * Add a datapoint of data calculated in the graphController to the graph.
   *
   * @param stepCount The number of steps that have occured since the graph was open (x value).
   * @param stateCount The number of cells with the specific state (y value)
   * @param state The state in question
   * @param line The series/line that the data is to be added to
   */
  public void addPointToLine(int stepCount, int stateCount, int state, XYChart.Series line) {
    XYChart.Data dataPoint = new XYChart.Data(stepCount, stateCount);
    line.getData().add(dataPoint);
    dataPoint.getNode().setId(state + "," + stateCount);
    setPointStyle(state, dataPoint.getNode());
  }

  /**
   * Sets the style of the datapoint so that its color matches the state color in the simulation grid.
   *
   * @param state The state in question.
   * @param node The node of the datapoint.
   */
  public void setPointStyle(int state, Node node) {
    StringBuilder style = new StringBuilder();
    style.append(FX_STROKE + Color
        .web(stateColorMap.get(state)).toString().substring(TWO, EIGHT) + SEMICOLON);
    style.append(
        FX_BACKGROUND + Color.web(stateColorMap.get(state)).toString().substring(TWO, EIGHT)
            + WHITE);
    node.setStyle(style.toString());
  }

  /**
   * Sets/updates the color of all data points in the line for the specified state.
   *
   * @param state The state in question.
   */
  public void setLineStyle(int state) {
    Set<Node> nodes = this.lineChart.lookupAll(DOT_SERIES + state);
    for (Node node : nodes) {
      setPointStyle(state, node);
    }
  }

  /**
   * Closes the graph.
   */
  public void close() {
    this.graphStage.close();
  }

  /**
   * getter method for the graph stage
   *
   * @return the stage upon which the graph is shown
   */
  public Stage getStage() {
    return this.graphStage;
  }

  /**
   * Shows the graph scene to allow it to be reopened.
   */
  public void showGraph() {
    this.graphStage.show();
  }
}
