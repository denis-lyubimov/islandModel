package com.javarush.islandmodel;

public class Start {
    public static void main(String[] args) {
        new Thread(new IslandModel()).start();
    }
}
