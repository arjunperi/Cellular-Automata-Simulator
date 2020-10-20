package Model;

import java.util.List;

public class SegregationModel extends Model{
  public static final int EMPTY = 0;
  private final double percentSimilar;
  private static final String PERCENT_SIMILAR = "Percent_Similar";

  public SegregationModel(String fileName, String modelType) {
    super(fileName, modelType);
    percentSimilar=Double.parseDouble((String)propertyFile.getOrDefault(PERCENT_SIMILAR, defaultPropertyFile.get(PERCENT_SIMILAR)));
    initializeEmptyQueue();
  }

  public void updateState(int row, int column, List<Cell> neighbors) {
    Cell currentCell = getCell(row,column);
    if (currentCell.getCurrentState() != EMPTY && getPercentageSimilar(currentCell, neighbors) <= percentSimilar && !emptyQueue.isEmpty()) {
      emptyQueue.poll().setFutureState(currentCell.getCurrentState());
      currentCell.setFutureState(EMPTY);
      emptyQueue.add(currentCell);
    }
  }

  private double getPercentageSimilar(Cell currentCell, List<Cell> neighbors) {
    int countSimilar = 0;
    int countOther = 0;
    for (Cell neighbor : neighbors) {
      if (neighbor.getCurrentState() != EMPTY) {
        if (neighbor.getCurrentState() == currentCell.getCurrentState()) {
          countSimilar++;
        } else {
          countOther++;
        }
      }
    }
    if (countSimilar + countOther > 0) {
      return (double) countSimilar / (double) (countSimilar + countOther);
    }
    return 0;
  }

  private void initializeEmptyQueue() {
    for (int row = 0; row < gridOfCells.getCellsPerColumn(); row++) {
      for (int column = 0; column < gridOfCells.getCellsPerRow(); column++) {
        if(getCell(row,column).getCurrentState()==EMPTY) {
          emptyQueue.add(getCell(row,column));
        }
      }
    }
  }

}
