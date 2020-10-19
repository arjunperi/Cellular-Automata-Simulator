package View;


import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class FrontEndCell extends Rectangle {

  private Paint cellStateColor;
  private final int row;
  private final int column;

  public FrontEndCell(String cellStateColor, double x, double y, double width, double height,
      int row, int col) {
    super(x, y, width, height);
    this.row = row;
    this.column = col;
    updateCellColor(cellStateColor);
  }

  public void updateCellColor(String stateColor) {
    cellStateColor = Paint.valueOf(stateColor);
    this.setFill(Color.valueOf(String.valueOf(this.cellStateColor)));
  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }

  public String getCellColor() {
    return this.cellStateColor.toString();
  }
}
