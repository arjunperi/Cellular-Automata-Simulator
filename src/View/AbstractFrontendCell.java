package View;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

public abstract class AbstractFrontendCell extends Shape {

  private int cellState;
  private Shape cellShape;
  private Map<Integer, Paint> stateColorMapping;

  public AbstractFrontendCell(int cellState) {
    this.cellState = cellState;
    this.stateColorMapping = new HashMap<>();
    initializeColorMapping();
  }

  public void setStateColorMapping(Map<Integer, Paint> colorMap) {
    this.stateColorMapping = colorMap;
  }

  public void updateCellColor() {
    this.cellShape.setFill(stateColorMapping.get(this.cellState));
  }

  public void setCellShape(Shape shape) {
    this.cellShape = shape;
  }

  void setCellState(int state) {
    this.cellState = state;
    updateCellColor();
  }

//  public abstract void setCellLocation(int x, int y);

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
