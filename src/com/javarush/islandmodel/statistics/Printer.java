package com.javarush.islandmodel.statistics;

import com.javarush.islandmodel.island.Cell;
import com.javarush.islandmodel.island.Island;
import com.javarush.islandmodel.lifeforms.animals.Animal;
import com.javarush.islandmodel.lifeforms.lifeformenums.LifeFormType;
import com.javarush.islandmodel.settings.LifeFormsSettings;

import java.util.*;

public class Printer {
    private final Island island = Island.getInstance();
    private final Map<LifeFormType, Integer> islandCounter = new HashMap<>();

    public Printer() {
        resetIslandCounter();
    }

    private void resetIslandCounter() {
        for (LifeFormType lifeFormType : LifeFormType.values()) {
            islandCounter.put(lifeFormType, 0);
        }
    }

    private void fillIslandCounter() {
        resetIslandCounter();
        Collection<Cell> cells = island.getCellsMap().values();
        for (Cell cell : cells) {
            for (Map.Entry<LifeFormType, Set<Animal>> animalEntry : cell.getAnimalMap().entrySet()) {
                islandCounter.put(animalEntry.getKey(), islandCounter.get(animalEntry.getKey()) + animalEntry.getValue().size());
            }
            islandCounter.put(LifeFormType.PLANT, cell.getPlantsCount() + islandCounter.get(LifeFormType.PLANT));
        }
    }

    public void printCellInfo(Cell cell) {
        System.out.println(cell);
    }

    public void printEachCellInfo() {
        Collection<Cell> cells = island.getCellsMap().values();
        for (Cell cell : cells) {
            System.out.println(cell);
        }
    }

    public void printIslandInfo() {
        String message = ("Общая информация по острову : \n");
        StringBuilder result = new StringBuilder(message);
        fillIslandCounter();

        for (Map.Entry<LifeFormType, Integer> counterEntry : islandCounter.entrySet()) {
            result.append(LifeFormsSettings.getInstance().getLifefFormFields(counterEntry.getKey()).getSign()).append(" = ").append(counterEntry.getValue()).append("; ");
        }
        result.append("\n");

        System.out.println(result);
    }


}

