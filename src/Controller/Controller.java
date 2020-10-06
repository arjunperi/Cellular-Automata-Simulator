package Controller;

import Model.Model;
import javafx.scene.input.KeyCode;

public class Controller {

  private final Model mainModel;

  public Controller(Model mainModel) {
    this.mainModel = mainModel;
  }


  public void handleKeyInput(KeyCode code) {
    switch (code) {
      case SPACE -> mainModel.switchPause();
      case S -> mainModel.step();
    }
  }
}
