package cellsociety;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {

    private int currentState;
    private int futureState;

    public Cell(int state, int x, int y, double width, double height){
        currentState = state;
        futureState = currentState;
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        if(currentState == 0) setFill(Color.WHITE);
        if(currentState == 1) setFill(Color.BLACK);
        setStroke(Color.BLACK);
    }

    public void updateFill(){
        if(currentState == 0) setFill(Color.WHITE);
        if(currentState == 1) setFill(Color.BLACK);
    }

    public void setFutureState(int state){
        futureState = state;
    }

    public int getCurrentState(){
        return currentState;
    }

    public void nextState(){
        currentState = futureState;
        updateFill();
    }
}
