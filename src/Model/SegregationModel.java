package Model;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SegregationModel extends Model{

  //private final Grid mainGrid;
  private final Queue<int[]> emptyCellQueue = new LinkedList<>();

  public SegregationModel(String fileName, String modelType, String fileOut) {
    super(fileName,modelType, fileOut);
    //mainGrid=this.getGridOfCells();
    //initializeEmptyCellQueue();
  }

  public SegregationModel(String fileName, String modelType) {
    super(fileName,modelType);
    //mainGrid=this.getGridOfCells();
    //initializeEmptyCellQueue();
  }

//  @Override
//  public void updateCells() {
//    for (int row = 0; row < mainGrid.getCellsPerColumn(); row++) {
//      for (int column = 0; column < mainGrid.getCellsPerRow(); column++) {
//        updateCellState(row,column);
//      }
//    }
//  }
//
//  private void updateCellState(int row, int column) {
//    Cell currentCell = mainGrid.getCell(row,column);
//    List<Cell> cellNeighbors = mainGrid.getNeighbors(row, column, currentCell);
//    currentCell.updateState(cellNeighbors);
//    if(currentCell.getCurrentState()!=currentCell.getFutureState() && currentCell.getCurrentState()!=0) {
//      emptyCellQueue.add(new int[] {row,column});
//      if(!emptyCellQueue.isEmpty()) {
//        int[] cellToMoveTo = emptyCellQueue.poll();
//        mainGrid.getCell(cellToMoveTo[0],cellToMoveTo[1]).setFutureState(currentCell.getCurrentState());
//      }
//    }
//  }
//
//  private void initializeEmptyCellQueue() {
//    for (int row = 0; row < mainGrid.getCellsPerColumn(); row++) {
//      for (int column = 0; column < mainGrid.getCellsPerRow(); column++) {
//        Cell currentCell = mainGrid.getCell(row,column);
//        if(currentCell.getCurrentState()==0) {
//          emptyCellQueue.add(new int[] {row,column});
//        }
//      }
//    }
//  }
}
