package View;

import java.util.Map;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class RPSFrontEndCell extends AbstractFrontEndCell {

  public RPSFrontEndCell(int cellState, double x, double y, double width, double height) {
    super(cellState);
    super.setCellShape(new Rectangle(x, y, width, height));
    super.updateCellColor();
  }

  @Override
  public void initializeColorMapping() {
    Map<Integer, Paint> colorMap = Map.of(
        0, Color.RED,
        1, Color.GREEN,
        2, Color.BLUE,
        3, Color.YELLOW,
        4, Color.PURPLE,
        5, Color.ORANGE);
    super.setStateColorMapping(colorMap);
  }
}
