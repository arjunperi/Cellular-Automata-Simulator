package Controller;

import Model.Model;
import View.GraphView;
import cellsociety.Simulation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GraphContoller{

  private static final String RESOURCES = "Resources/";
  public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES.replace("/", ".");

  private final GraphView graphView;
  private final Model mainModel;
  private boolean graphShowing = false;
  private int graphCount = 0;
  private Map<Integer, String> stateColorMap;

  private Map<Integer, Series> stateSeries = new HashMap<>();
  private Map<Integer, Integer> stateCountsMap = new HashMap<>();
  private Scene graphScene;
  private LineChart<Number,Number> lineChart;

  public GraphContoller(Model model, Map<Integer, String> stateColorMap){
    this.graphView = new GraphView();
    this.mainModel = model;
    this.stateColorMap = stateColorMap;
    createGraph();
  }

  public void graphStep(){}

  public void createGraph(){
    this.stateCountsMap = new HashMap<>();
    this.stateSeries = new HashMap<>();
    graphCount = 0;
    initializeStateCounts();
    initializeStateSeries();

    graphShowing = true;
    Stage test = new Stage();

    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();
    this.lineChart =
        new LineChart<>(xAxis,yAxis);

    lineChart.setTitle("Graph");

    for(XYChart.Series series:this.stateSeries.values()){
      lineChart.getData().add(series);
    }

    this.graphScene = new Scene(lineChart, Simulation.SCENE_WIDTH, Simulation.SCENE_HEIGHT, Simulation.BACKGROUND);
    test.setScene(graphScene);
    test.show();
    test.setOnCloseRequest(event -> graphShowing = false);
  }

  public boolean updateGraph(){
    updateStateCounts();
    for(int currentState : this.stateCountsMap.keySet()){
      int currentStateCount = this.stateCountsMap.get(currentState);
      XYChart.Series currentStateSeries = this.stateSeries.get(currentState);
      currentStateSeries.getData().add(new XYChart.Data(graphCount,currentStateCount));
    }
    return graphShowing;
  }

  public void updateStateCounts(){
    setSeriesColor();
    graphCount ++;
    initializeStateCounts();
    for(int row=0; row<mainModel.getNumberOfRows();row++) {
      for(int column=0; column< mainModel.getNumberOfColumns(); column++) {
        int currentState = mainModel.getCellState(row,column);
        this.stateCountsMap.put(currentState, this.stateCountsMap.getOrDefault(currentState,0) + 1);
      }
    }
  }

  public void initializeStateSeries(){
    for(Integer state:this.stateCountsMap.keySet()){
      XYChart.Series currentSeries = new Series();
      currentSeries.setName("state: "+state);
      this.stateSeries.put(state, currentSeries);
    }
  }

  public void initializeStateCounts(){
    for(int state : this.stateColorMap.keySet()){
      this.stateCountsMap.put(state,0);
    }
  }

  public void setSeriesColor(){
    for(int currentState : this.stateSeries.keySet()){
      Set<Node> nodes = this.lineChart.lookupAll(".series"+currentState);
      for(Node node:nodes){
        StringBuilder style = new StringBuilder();
        style.append("-fx-stroke: #" + Color
            .web(stateColorMap.get(currentState)).toString().substring(2, 8)  +"; ");
        style.append("-fx-background-color: #" + Color.web(stateColorMap.get(currentState)).toString().substring(2, 8)+ ", white");
        node.setStyle(style.toString());
      }
    }
  }
}
