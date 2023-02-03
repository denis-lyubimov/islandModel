package com.javarush.islandmodel.settings;

public class IslandSettings {

    public static final int CELLS_X = 10;
    public static final int CELLS_Y = 10;
    public static final int CYCLES_COUNT = 10;

    private IslandSettings(){

    }

    private static class SingletonHolder {
        public static final IslandSettings HOLDER_INSTANCE = new IslandSettings();
    }

    public static IslandSettings getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }
}
