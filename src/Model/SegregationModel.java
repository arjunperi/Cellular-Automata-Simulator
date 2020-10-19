package Model;

import java.util.List;

public class SegregationModel extends Model{
  public static final int EMPTY = 0;
  public static final double PERCENT_SIMILAR = .5;

  public SegregationModel(String fileName, String modelType) {
    super(fileName, modelType);
    initializeEmptyQueue();
  }

  public void updateState(int row, int column, List<Cell> neighbors) {
    Cell currentCell = getCell(row,column);
    if (currentCell.getCurrentState() != EMPTY) {
      double percentSimilarInNeighborhood = getPercentageSimilar(currentCell, neighbors);
      if (percentSimilarInNeighborhood <= PERCENT_SIMILAR) {
        if (!emptyQueue.isEmpty()) {
          emptyQueue.poll().setFutureState(currentCell.getCurrentState());
          currentCell.setFutureState(EMPTY);
          emptyQueue.add(currentCell);
        }
      }
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
