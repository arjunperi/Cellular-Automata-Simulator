package View;

import Model.State;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class FrontEndCell extends Shape {

  private Paint cellStateColor;
  private Shape cellShape;

  public FrontEndCell(String cellStateColor, double x, double y, double width, double height) {
    setCellShape(new Rectangle(x, y, width, height));
    updateCellColor(cellStateColor);
  }

  public void updateCellColor(String stateColor) {
      cellStateColor = Paint.valueOf(stateColor);
      this.cellShape.setFill(Color.valueOf(String.valueOf(this.cellStateColor)));
  }

  public void setCellShape(Shape shape) {
    this.cellShape = shape;
  }

  public Shape getCellShape() {
    return this.cellShape;
  }

  public String getCellColor() {
    return this.cellStateColor.toString();
  }
}
