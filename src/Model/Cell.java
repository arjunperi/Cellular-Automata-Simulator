package Model;

public class Cell {

    private int currentState;
    private int futureState;

    public Cell(int state){
        currentState = state;
        futureState = currentState;
    }

    public void setFutureState(int state){
        futureState = state;
    }

    public int getCurrentState(){
        return currentState;
    }

    public void nextState(){ currentState = futureState; }
}
