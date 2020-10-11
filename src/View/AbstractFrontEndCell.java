package View;

import Model.State;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

public abstract class AbstractFrontEndCell extends Shape {

  private int cellState;
  private Shape cellShape;
  private Map<Integer, Paint> stateColorMapping;

  public AbstractFrontEndCell(int cellState) {
    this.cellState = cellState;
    this.stateColorMapping = new HashMap<>();
    initializeColorMapping();
  }

  public void setStateColorMapping(Map<Integer, Paint> colorMap) {
    this.stateColorMapping = colorMap;
  }

  public void updateCellColor() {
    this.cellShape.setFill(Color.valueOf(String.valueOf(stateColorMapping.get(this.cellState))));
  }

  public void setCellShape(Shape shape) {
    this.cellShape = shape;
  }

  void setCellState(int state) {
    this.cellState = state;
    updateCellColor();
  }

  public Shape getCellShape() {
    return this.cellShape;
  }

  public int getCellState() {
    return this.cellState;
  }

  public Paint getCellColor() {
    return this.cellShape.getFill();
  }

  public abstract void initializeColorMapping();
}
