package View;

import cellsociety.Simulation;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GraphView {

  private static final String GRAPH_TITLE = "Graph";
  private static final String GRAPH_ID = "graph";
  private static final String FX_STROKE = "-fx-stroke: #";
  private static final String FX_BACKGROUND = "-fx-background-color: #";
  private static final String WHITE = ", white";
  private static final String DOT_SERIES = ".series";
  private static final String SEMICOLON = "; ";
  private static final int TWO = 2;
  private static final int EIGHT = 8;

  private final Map<Integer, String> stateColorMap;

  private Scene graphScene;
  private Stage graphStage;
  private LineChart<Number,Number> lineChart;

  public GraphView(Map<Integer, String> stateColorMap){
    this.stateColorMap = stateColorMap;
  }

  public void createLineChart(){
    this.graphStage = new Stage();
    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();
    this.lineChart = new LineChart<>(xAxis,yAxis);
    lineChart.setTitle(GRAPH_TITLE);
    lineChart.setId(GRAPH_ID);
  }

  public void addLinesToGraph(Collection<XYChart.Series> stateSeries){
    for(XYChart.Series series: stateSeries){
      lineChart.getData().add(series);
    }
  }

  public void createGraphWindow(){
    this.graphScene = new Scene(lineChart, Simulation.SCENE_WIDTH, Simulation.SCENE_HEIGHT, Simulation.BACKGROUND);
    this.graphStage.setScene(graphScene);
    this.graphStage.show();
  }

  public void setOnGraphClose(EventHandler event){
    this.graphStage.setOnCloseRequest(event);
  }

  public void addPointToLine(int stepCount, int stateCount,int state, XYChart.Series line){
    XYChart.Data dataPoint = new XYChart.Data(stepCount, stateCount);
    line.getData().add(dataPoint);
    dataPoint.getNode().setId(state+","+stateCount);
    setPointStyle(state, dataPoint.getNode());
  }

  public void setPointStyle(int state, Node node){
    StringBuilder style = new StringBuilder();
    style.append(FX_STROKE + Color
        .web(stateColorMap.get(state)).toString().substring(TWO, EIGHT)  +SEMICOLON);
    style.append(FX_BACKGROUND + Color.web(stateColorMap.get(state)).toString().substring(TWO, EIGHT)+ WHITE);
    node.setStyle(style.toString());
  }

  public void setLineStyle(int state){
    Set<Node> nodes = this.lineChart.lookupAll(DOT_SERIES +state);
    for(Node node:nodes){
      setPointStyle(state,node);
    }
  }

  public void close(){
    this.graphStage.close();
  }
  public Stage getStage(){ return this.graphStage; }
  public void showGraph(){this.graphStage.show();}
}
