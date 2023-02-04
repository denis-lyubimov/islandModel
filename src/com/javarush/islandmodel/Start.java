package com.javarush.islandmodel;


//You can change amount of cylcels and size of the islend in com.javarush.islandmodel.settings.IslandSettings class
public class Start {
    public static void main(String[] args) {
        new Thread(new IslandModel()).start();
    }
}
