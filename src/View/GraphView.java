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

  private Map<Integer, String> stateColorMap;

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
    lineChart.setTitle("Graph");
  }

  public void addLinesToGraph(Collection<XYChart.Series> stateSeries){
    for(XYChart.Series series: stateSeries){
      lineChart.getData().add(series);
    }
  }

  public void showGraph(){
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
    setPointStyle(state, dataPoint.getNode());
  }

  public void setPointStyle(int state, Node node){
    StringBuilder style = new StringBuilder();
    style.append("-fx-stroke: #" + Color
        .web(stateColorMap.get(state)).toString().substring(2, 8)  +"; ");
    style.append("-fx-background-color: #" + Color.web(stateColorMap.get(state)).toString().substring(2, 8)+ ", white");
    node.setStyle(style.toString());
  }

  public void setLineStyle(int state){
    Set<Node> nodes = this.lineChart.lookupAll(".series"+state);
    for(Node node:nodes){
      setPointStyle(state,node);
    }
  }

  public void close(){
    this.graphStage.close();
  }
}
