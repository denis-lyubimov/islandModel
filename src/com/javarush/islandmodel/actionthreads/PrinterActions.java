package com.javarush.islandmodel.actionthreads;

import com.javarush.islandmodel.island.Island;
import com.javarush.islandmodel.statistics.Printer;

public class PrinterActions implements Runnable{
    Island island = Island.getInstance();
    Printer printer ;

    public PrinterActions(Printer printer){
        this.printer = printer;
    }

    @Override
    public void run() {
        synchronized (island){
            printer.printIslandInfo();
        }
    }
}
