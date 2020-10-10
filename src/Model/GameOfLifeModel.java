/*
abstract state class/interface
specific model enums that implement the interface
  define the color/state in this enum

 */


package Model;

public class GameOfLifeModel extends Model {

  public GameOfLifeModel(String fileName) {
    super(fileName);
  }

  public GameOfLifeModel(String fileName, String fileOut) {
    super(fileName, fileOut);
  }

  public enum GameOfLifeState implements State {
    ALIVE("BLACK", 1),
    DEAD("WHITE", 0);

    private final String color;
    private final int code;

    GameOfLifeState(String color, int code) {
      this.color=color;
      this.code=code;
    }

    @Override
    public String getColor() {
      return color;
    }

    @Override
    public int getCode() {
      return code;
    }
  }
}