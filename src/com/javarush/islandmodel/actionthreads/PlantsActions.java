package com.javarush.islandmodel.actionthreads;

import com.javarush.islandmodel.island.Cell;
import com.javarush.islandmodel.island.Island;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public class PlantsActions implements Runnable{
    private final  Island island = Island.getInstance();
    Collection<Cell> cells = island.getCellsMap().values();

    @Override
    public void run() {
        synchronized (island){
            growPlants();
        }
    }

    private void growPlants(){
        for (Cell cell : cells) {
            cell.addPlants(ThreadLocalRandom.current().nextInt(31));
        }
    }



}
