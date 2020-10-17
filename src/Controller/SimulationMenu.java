package Controller;

import Model.Model;
import View.View;
import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.layout.BorderPane;

public class SimulationMenu {

  private Model mainModel;
  private View mainView;
  private BorderPane root;
  private Group topMenuGroup;
  private Group centerPanelGroup;
  private boolean simIsSet = false;

  public SimulationMenu(Model model, View view){
    this.mainModel = model;
    this.mainView = view;
    this.root = mainView.getRoot();
    this.topMenuGroup = mainView.getTopGroup();
    this.centerPanelGroup = mainView.getCenterGroup();
  }

  public void initializeSimulationMenu(){
    clearTopGroup();
    clearCenterPanelGroup();

  }

  public void displaySimulationInfo(){}




  private void clearTopGroup(){
    this.topMenuGroup.getChildren().clear();
  }
  private void clearCenterPanelGroup(){
    this.centerPanelGroup.getChildren().clear();
  }

}
