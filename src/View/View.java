package View;

import Model.Model;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import Model.Cell;

import java.util.List;


public class View {

    static final double SCENE_WIDTH = 400;
    static final double SCENE_HEIGHT = 400;
    private static final Paint BACKGROUND = Color.AZURE;
    private static final int FRAMES_PER_SECOND = 1;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    double xOffset  = View.SCENE_WIDTH / 20;
    double yOffset  = View.SCENE_HEIGHT / 20;

    private final Model myModel;
    private Timeline animation;
    private final Group root;
    private List<List<AbstractFrontendCell>> frontEndCellGrid;


    public View(Model model){
        root = new Group();
        myModel = model;
        initializeFrontEndCells();
    }

    public Scene setupScene(){
        return new Scene(root, SCENE_WIDTH, SCENE_HEIGHT, BACKGROUND);
    }

    /**
     * Initializes the display's animation.
     */
    public void initializeAnimation() {
        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> viewStep());
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    public void viewStep() {
        myModel.step();
        updateFrontEndCells();
    }

    private void initializeFrontEndCells() {
        frontEndCellGrid = new ArrayList<>();
        List<List<Cell>> backendCells = myModel.getModelCells();
        double x=0;
        double y=0;

        for(List<Cell> currentBackendCellRow : backendCells){
            x=0;
            List<AbstractFrontendCell> frontEndCellRow = new ArrayList<>();
            for(Cell currentBackEndCell : currentBackendCellRow) {
                int state = currentBackEndCell.getCurrentState();
                AbstractFrontendCell currentFrontEndCell = new GameOfLifeFrontendCell(state, x, y, xOffset, yOffset);
                frontEndCellRow.add(currentFrontEndCell);
                root.getChildren().add(currentFrontEndCell.getCellShape());
                x += xOffset;
            }
            frontEndCellGrid.add(frontEndCellRow);
            y += yOffset;
        }
    }

    private void updateFrontEndCells() {
        List<List<Cell>> backendCells = myModel.getModelCells();
        for(int row=0; row<backendCells.size(); row++){
            for(int column=0; column<backendCells.get(row).size(); column++){
                int state = backendCells.get(row).get(column).getCurrentState();
                frontEndCellGrid.get(row).get(column).setCellState(state);
            }
        }
    }

    public List<List<AbstractFrontendCell>> getFrontEndCellGrid(){
        return frontEndCellGrid;
    }


    public Runnable handleKeyInput(KeyCode code) {
        return null;
    }

}
