package com.javarush.islandmodel.island;

import com.javarush.islandmodel.settings.IslandSettings;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class Island {

    private final int cellsX;
    private final int cellsY;
    private final Map<Integer, Cell> cellsMap = new TreeMap<>();

    private Island(int cellsX, int cellsY) {
        this.cellsX = cellsX;
        this.cellsY = cellsY;
        createCells();
    }

    private static class SingletonHolder {
        public static final Island HOLDER_INSTANCE = new Island(IslandSettings.CELLS_X, IslandSettings.CELLS_Y);
    }

    public static Island getInstance() {
        return Island.SingletonHolder.HOLDER_INSTANCE;
    }

    private void createCells(){
        int cellNumber = 0;
        for (int rowNumber = 1; rowNumber <= cellsX; rowNumber++){
            for (int columnNumber = 1; columnNumber <= cellsY; columnNumber++) {
                cellNumber++;
                Cell cell = new Cell(rowNumber,columnNumber,cellNumber);
                cellsMap.put(cell.getCellId(), cell);
            }
        }
    }

    public Map<Integer, Cell> getCellsMap() {
        return cellsMap;
    }

    public Cell getCell(int coordinateX, int coordinateY){
        return cellsMap.get(Integer.parseInt(coordinateX+""+coordinateY));
    }

    public Cell getCell(Integer cellId){
        return cellsMap.get(cellId);
    }

    public int getAnimalsCount(){
        int animalsCounter = 0;
        Collection<Cell> cells = getCellsMap().values();
        for (Cell cell : cells) {
            animalsCounter += cell.getAllAnimalsCount();
        }
        return animalsCounter;
    }
}
