package View;

import java.util.Map;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class GameOfLifeFrontEndCell extends AbstractFrontEndCell {

  public GameOfLifeFrontEndCell(int cellState, double x, double y, double width, double height) {
    super(cellState);
    super.setCellShape(new Rectangle(x, y, width, height));
    super.updateCellColor();
  }

  @Override
  public void initializeColorMapping() {
    Map<Integer, Paint> colorMap = Map.of(
        0, Color.WHITE,
        1, Color.BLACK);
    super.setStateColorMapping(colorMap);
  }
}
