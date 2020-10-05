package Model;

import Model.Cell;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Grid {

    private List<String[]> cellStates;
    private List<List<Cell>> myCells = new ArrayList<>();
    final String PATH = "data/";


    public Grid(String fileName){
        cellStates = readAll(fileName);
        String[] firstRow = cellStates.get(0);
        double cellsPerRow = Double.parseDouble(firstRow[0]);
        double cellsPerColumn = Double.parseDouble(firstRow[1]);

        //Since view has not yet been implemented, let's just assume height and width of the scene are 400
        double xOffset  = 400 / cellsPerRow;
        double yOffset  = 400 / cellsPerColumn;

        int x = 0;
        int y = 0;
        for(int i = 1; i< cellStates.size(); i++){
        String[] row = cellStates.get(i);
            x = 0;
            List<Cell> cellRow = new ArrayList<>();
            for(String stateString : row){
                int state = Integer.parseInt(stateString);
                cellRow.add(new Cell(state));
                x += xOffset;
            }
            myCells.add(cellRow);
            y += yOffset;
        }
    }

    private List<String[]> readAll (String fileName) {
        try (CSVReader csvReader = new CSVReader(new FileReader(PATH + fileName))) {
            return csvReader.readAll();
        }
        catch (IOException | CsvException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<List<Cell>> getGridCells(){
        return myCells;
    }
}
