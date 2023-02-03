package com.javarush.islandmodel.actionthreads;

import com.javarush.islandmodel.island.Cell;
import com.javarush.islandmodel.island.Island;
import com.javarush.islandmodel.lifeforms.animals.Animal;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class AnimalsActions implements Runnable {

    private final Island island = Island.getInstance();
    Collection<Cell> cells = island.getCellsMap().values();

    @Override
    public void run() {
        synchronized (island) {
            checkAnimalsLevelOfHunger();
            reproduce();
            giveABirth();
            eat();
            move();
            increaseLevelOfHunger();
        }
    }

    public void checkAnimalsLevelOfHunger() {
        for (Cell cell : cells) {
            for (Set<Animal> animals : cell.getAnimalMap().values()) {
                Iterator<Animal> iterator = animals.iterator();
                while (iterator.hasNext()) {
                    Animal animal = iterator.next();
                    if (animal.getLevelOfHunger() <= 0) {
                        animal.die();
                        iterator.remove();
                    }
                }
            }
        }
    }

    public void reproduce() {
        for (Cell cell : cells) {
            for (Set<Animal> animals : cell.getMaleAnimalMap().values()) {
                for (Animal animal : animals) {
                    animal.reproduce();
                }
            }
        }
    }

    public void giveABirth() {
        for (Cell cell : cells) {
            for (Set<Animal> animals : cell.getFemaleAnimalMap().values()) {
                HashSet<Animal> childAnimals = new HashSet<>();
                for (Animal animal : animals) {
                    if (cell.getAnimalMap().size() + childAnimals.size() >= animal.getMaxAmountPerCell()) {
                        break;
                    }
                    if (animal.getPregnancyStatus()) {
                        Animal childAnimal = animal.giveABirth();
                        childAnimals.add(childAnimal);
                    }
                }
                if (childAnimals.size() > 0) {
                    for (Animal childAnimal : childAnimals) {
                        childAnimal.setAnimalCell(cell);
                        cell.addAnimal(childAnimal);
                    }
                }
            }
        }
    }

    public void eat() {
        for (Cell cell : cells) {
            for (Set<Animal> animals : cell.getAnimalMap().values()) {
                for (Animal animal : animals) {
                    if (animal.isHungry()) {
                        animal.eat();
                    }
                }
            }
        }
    }

    public void move() {
        for (Cell cell : cells) {
            for (Set<Animal> animals : cell.getAnimalMap().values()) {
                Iterator<Animal> iterator = animals.iterator();
                while (iterator.hasNext()) {
                    Animal animal = iterator.next();
                    animal.move();
                    if (animal.getAnimalCell() != cell) {
                        iterator.remove();
                    }
                }
            }
        }
    }

    public void increaseLevelOfHunger() {
        for (Cell cell : cells) {
            for (Set<Animal> animals : cell.getAnimalMap().values()) {
                for (Animal animal : animals) {
                    animal.increaseLevelOfHunger();
                }
            }
        }
    }


}
