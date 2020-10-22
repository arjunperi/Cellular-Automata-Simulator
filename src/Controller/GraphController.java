package Controller;

import Model.Model;
import View.GraphView;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.stage.Stage;


/**
 * @author Alex Jimenez
 *
 * Controller class for the graph display. Handles the backend data to update the graph and delegates to
 * the graphView class to display the graph scene.
 */
public class GraphController {

  private final static int NEW_GRAPH_STEP_COUNT = 0;
  private final static boolean GRAPH_SHOWING = true;
  private final static boolean GRAPH_NOT_SHOWING = false;
  private final static String STATE = "state";
  private final static String COLON = ":";
  private final GraphView graphView;
  private final Model mainModel;

  private boolean graphShowing = GRAPH_NOT_SHOWING;
  private int graphStepCount = NEW_GRAPH_STEP_COUNT;

  private final Map<Integer, String> stateColorMap;
  private Map<Integer, Series> stateSeries = new HashMap<>();
  private Map<Integer, Integer> stateCountsMap = new HashMap<>();

  /**
   * @param model The main model of the simulation being ran.
   * @param stateColorMap The state color mapping of the current simulation. Initially read from property file in the controller.
   * @param stage The stage upon which the simulation grid is displayed.
   * @param projectTextResources The current text recourse properties file of the simulation being ran.
   */
  public GraphController(Model model, Map<Integer, String> stateColorMap, Stage stage,
      ResourceBundle projectTextResources) {
    this.graphView = new GraphView(stage, stateColorMap, projectTextResources);
    this.mainModel = model;
    this.stateColorMap = stateColorMap;
    createGraph();
  }


  /**
   * Initializes the new graph by creating data object for state counts and calling the graphView to
   * initialize the line chart view with relevant state lines.
   */
  public void createGraph() {
    graphStepCount = NEW_GRAPH_STEP_COUNT;
    graphShowing = GRAPH_SHOWING;
    this.stateCountsMap = new HashMap<>();
    this.stateSeries = new HashMap<>();

    initializeStateCounts();
    initializeStateSeries();

    this.graphView.createLineChart();
    this.graphView.addLinesToGraph(this.stateSeries.values());
    this.graphView.createGraphWindow();
    this.graphView.setOnGraphClose(event -> graphShowing = GRAPH_NOT_SHOWING);
  }

  /**
   * Updates the graph by adding points for the current state concentrations. Does so by iterating over the
   * state counts map and providing this information to the view
   *
   * @return A boolean that represents if the graph is showing or not
   */
  public boolean updateGraph() {
    updateStateCounts();
    for (int currentState : this.stateCountsMap.keySet()) {
      int currentStateCount = this.stateCountsMap.get(currentState);
      XYChart.Series currentStateSeries = this.stateSeries.get(currentState);
      this.graphView
          .addPointToLine(this.graphStepCount, currentStateCount, currentState, currentStateSeries);
    }
    return graphShowing;
  }

  private void updateStateCounts() {
    setSeriesColor();
    graphStepCount++;

    initializeStateCounts();
    for (int row = 0; row < mainModel.getNumberOfRows(); row++) {
      for (int column = 0; column < mainModel.getNumberOfColumns(); column++) {
        int currentState = mainModel.getCellState(row, column);
        this.stateCountsMap
            .put(currentState, this.stateCountsMap.getOrDefault(currentState, 0) + 1);
      }
    }
  }

  private void initializeStateSeries() {
    for (Integer state : this.stateCountsMap.keySet()) {
      XYChart.Series currentSeries = new Series();
      currentSeries.setName(STATE + COLON + state);
      this.stateSeries.put(state, currentSeries);
    }
  }

  private void initializeStateCounts() {
    for (int state : this.stateColorMap.keySet()) {
      this.stateCountsMap.put(state, 0);
    }
  }

  private void setSeriesColor() {
    for (int currentState : this.stateSeries.keySet()) {
      this.graphView.setLineStyle(currentState);
    }
  }

  public void closeGraph() {
    this.graphView.close();
  }

  public GraphView getGraphView() {
    return this.graphView;
  }

}
