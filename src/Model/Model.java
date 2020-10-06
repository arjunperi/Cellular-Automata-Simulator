package Model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public abstract class Model{

  private final List<List<Cell>> myCells;
  private final Grid myGrid;

  public Model(String fileName) {
    myGrid = new Grid(fileName);
    myCells = myGrid.getGridCells();
  }

  public abstract void updateCells(Cell cell, int x, int y);

  public void step() {
    for(int i = 0; i < myGrid.getCellsPerColumn(); i ++){
      for(int j = 0; j < myGrid.getCellsPerRow(); j ++){
        updateCells(myCells.get(i).get(j),i,j);
      }
    }
    for(int i = 0; i < myGrid.getCellsPerColumn(); i ++){
      for(int j = 0; j < myGrid.getCellsPerRow(); j ++){
        this.myCells.get(i).get(j).nextState();
      }
    }
  }

  public List<List<Integer>> getNeighbors(int x , int y){
    int[][] possibleNeighbors = new int[][]{{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1}};
    List<List<Integer>> neighbors = new ArrayList<>();
    for(int[] possibleNeighbor : possibleNeighbors){
      int currentX = x + possibleNeighbor[0];
      int currentY = y + possibleNeighbor[1];
      if(currentX < 0 || currentY < 0) continue;
      if(currentX >= myGrid.getCellsPerColumn() || currentY >= myGrid.getCellsPerRow()) continue;
      neighbors.add(Arrays.asList(currentX, currentY));
    }
    return neighbors;
  }

  public List<List<Cell>> getModelCells(){
    return myCells;
  }
}
