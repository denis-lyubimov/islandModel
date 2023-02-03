package com.javarush.islandmodel;

import com.javarush.islandmodel.actionthreads.AnimalsActions;
import com.javarush.islandmodel.actionthreads.PlantsActions;
import com.javarush.islandmodel.actionthreads.PrinterActions;
import com.javarush.islandmodel.island.Island;
import com.javarush.islandmodel.island.LifeFormGenerator;
import com.javarush.islandmodel.settings.IslandSettings;
import com.javarush.islandmodel.statistics.Printer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class IslandModel implements Runnable {


    @Override
    public void run() {
        Island island = Island.getInstance();
        LifeFormGenerator lifeFormGenerator = new LifeFormGenerator(island);
        lifeFormGenerator.generateAnimals();
        lifeFormGenerator.generatePlants();
        Printer printer = new Printer();
        printer.printEachCellInfo();
        printer.printIslandInfo();
        System.out.println("запуск деятельности на острове\n");
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
        scheduledExecutorService.scheduleWithFixedDelay(new AnimalsActions(), 0, 2, TimeUnit.SECONDS);
        scheduledExecutorService.scheduleAtFixedRate(new PlantsActions(), 0, 2, TimeUnit.SECONDS);
        scheduledExecutorService.scheduleAtFixedRate(new PrinterActions(printer), 0, 2100, TimeUnit.MILLISECONDS);
        int cycles = 1;
        while (true){
            try {
                Thread.sleep(2000);
                if (island.getAnimalsCount() == 0) {
                    scheduledExecutorService.shutdown();
                    System.out.println("Все животные померли");
                    break;
                } else if ( cycles >= IslandSettings.CYCLES_COUNT){
                    scheduledExecutorService.shutdown();
                    System.out.println("Достигнуто максимальное количество циклов");
                    break;
                }
                cycles++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
