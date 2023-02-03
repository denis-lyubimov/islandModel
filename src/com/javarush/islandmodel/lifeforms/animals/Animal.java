package com.javarush.islandmodel.lifeforms.animals;

import com.javarush.islandmodel.island.Cell;
import com.javarush.islandmodel.island.Island;
import com.javarush.islandmodel.lifeforms.InitialLifeForm;
import com.javarush.islandmodel.lifeforms.additionalclasses.LifeFormFactory;
import com.javarush.islandmodel.lifeforms.lifeformenums.LeaveCellType;
import com.javarush.islandmodel.lifeforms.lifeformenums.LifeFormType;
import com.javarush.islandmodel.lifeforms.additionalclasses.LifefFormFields;
import com.javarush.islandmodel.settings.IslandSettings;
import com.javarush.islandmodel.settings.LifeFormsSettings;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal extends InitialLifeForm {
    protected String gender;
    protected Cell animalCell;
    protected Float levelOfHunger;
    protected Boolean pregnancyStatus;
    private final List<String> movementDirections = new ArrayList<>() {{
        add("up");
        add("down");
        add("left");
        add("right");
    }};

    public Animal(LifeFormType type, LifefFormFields fields) {
        super(type, fields);
        this.gender = getRandomGender();
        this.pregnancyStatus = false;
        this.levelOfHunger = LifeFormsSettings.DEFAULT_ANIMAL_LEVEL_OF_HUNGER;
    }

    //other actions =============================
    public void die() {
        animalCell.removeAnimal(this, LeaveCellType.DIED);
        setAnimalCell(null);
    }

    public void increaseLevelOfHunger() {
        levelOfHunger = Math.max(levelOfHunger - 40, 0);
    }

    protected void decreaseLevelOfHunger(Float food) {
        levelOfHunger = Math.min(levelOfHunger + 100 * food / kgOfFoodToBeFullFeed, 100);
    }

    //reproduction  =============================

    protected Animal getPair() {
        Animal animal = null;
        if (animalCell.getMaleAnimalsCountByType(lifeFormType) == 0
                || animalCell.getFemaleAnimalsCountByType(lifeFormType) == 0) {
            return animal;
        }
        for (Animal femaleAnimal : animalCell.getFemaleAnimalMap().get(lifeFormType)) {
            if (!femaleAnimal.getPregnancyStatus()) {
                animal = femaleAnimal;
                break;
            }
        }
        return animal;
    }

    protected void makePregant(Animal femaleAnimal) {
        femaleAnimal.isPregant(true);
    }

    public void reproduce() {
        if (this.gender.equals("female")) {
            return;
        }
        Animal femaleAnimal = getPair();
        if (femaleAnimal != null) {
            makePregant(femaleAnimal);
        }
    }

    public Animal giveABirth() {
        Animal newAnimal = null;
        newAnimal = LifeFormFactory.getInstance().createAnimal(lifeFormType);
        isPregant(false);
        increaseLevelOfHunger();
        return newAnimal;
    }

    //eating =============================
    public boolean isHungry() {
        return !(levelOfHunger > 70f);
    }

    protected Animal getMeat() {
        Animal animalAsFood = null;
        Integer[] arrayOfChances = LifeFormsSettings.getInstance().getFoodChanceMap().get(lifeFormType);
        List<LifeFormType> possibleFood = new ArrayList<>();

        for (int animalId = 0; animalId < arrayOfChances.length; animalId++) {
            if (LifeFormType.values()[animalId] == LifeFormType.PLANT) {
                continue;
            }
            if (animalId == lifeFormType.ordinal() || arrayOfChances[animalId] == 0
                    || animalCell.getAnimalsCountByType(LifeFormType.values()[animalId]) == 0) {
                continue;
            }
            possibleFood.add(LifeFormType.values()[animalId]);
        }

        if (possibleFood.size() == 0) {
            return animalAsFood;
        }

        animalAsFood = animalCell.getAnimalByType(possibleFood.get(ThreadLocalRandom.current().nextInt(possibleFood.size())));

        return animalAsFood;
    }

    protected Float eatAnimal(Animal animal) {
        Float result = 0f;
        if (animal == null) {
            return result;
        }
        if (ThreadLocalRandom.current().nextInt(100) < LifeFormsSettings.getInstance().getFoodChance(lifeFormType, animal.getLifeFormType())) {
            result = animal.getWeight();
            animal.getAnimalCell().removeAnimal(animal, LeaveCellType.EATEN);
            animal.setAnimalCell(null);
        }
        return result;
    }

    public void eat() {
        Float food = 0f;
        Animal animal = getMeat();
        if (animal == null) {
            return;
        } else {
            food = eatAnimal(animal);
        }
//        System.out.println(this.name +animalCell.getCellId()+" ест "+animal  +" результат " + food);
        decreaseLevelOfHunger(food);
    }

    //movement =============================
    protected String getMovementDirection() {
        return movementDirections.get(ThreadLocalRandom.current().nextInt(movementDirections.size()));
    }

    protected int getCellsForMove(int maxCellsPerMove) {
        return ThreadLocalRandom.current().nextInt(maxCellsPerMove + 1);
    }

    public Cell move() {
        if (animalCell == null) {
            try {
                throw new RuntimeException("can not move animal, initial cell is null");
            } catch (RuntimeException e) {
                e.printStackTrace();
                return animalCell;
            }
        }

        int randomCellsToMove = getCellsForMove(maxCellsPerMove);
        if (randomCellsToMove == 0) {
            return animalCell;
        }

        String movementDirection = getMovementDirection();
        int rownum = animalCell.getRownum();
        int columnnum = animalCell.getColumnnum();

        switch (movementDirection) {
            case "up" -> {
                if (rownum == 1) {
                    return animalCell;
                }
                rownum = Math.max((rownum - randomCellsToMove), 1);
            }
            case "down" -> {
                if (rownum == IslandSettings.CELLS_X) {
                    return animalCell;
                }
                rownum = Math.min((rownum + randomCellsToMove), IslandSettings.CELLS_X);
            }
            case "left" -> {
                if (columnnum == 1) {
                    return animalCell;
                }
                columnnum = Math.max((columnnum - randomCellsToMove), 1);
            }
            case "right" -> {
                if (columnnum == IslandSettings.CELLS_Y) {
                    return animalCell;
                }
                columnnum = Math.min((columnnum + randomCellsToMove), IslandSettings.CELLS_Y);
            }
            default -> {
                try {
                    throw new IllegalStateException("Unexptected value in switch expression of move method : " + movementDirection);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                    return animalCell;
                }
            }
        }

        Cell newCell = Island.getInstance().getCell(rownum, columnnum);
        if (maxAmountPerCell <= newCell.getAnimalsCountByType(lifeFormType)) {
            return animalCell;
        }

        animalCell.removeAnimal(this, LeaveCellType.MOVED);
        newCell.addAnimal(this);
        setAnimalCell(newCell);
        return newCell;
    }

    //setter getter =============================
    protected String getRandomGender() {
        ArrayList<String> genders = new ArrayList<>() {{
            add("male");
            add("female");
        }};
        return genders.get(ThreadLocalRandom.current().nextInt(genders.size()));
    }

    public String getGender() {
        return gender;
    }

    public void setAnimalCell(Cell cell) {
        animalCell = cell;
    }

    public Cell getAnimalCell() {
        return animalCell;
    }

    public Float getLevelOfHunger() {
        return levelOfHunger;
    }

    public Boolean getPregnancyStatus() {
        return pregnancyStatus;
    }

    protected void isPregant(Boolean pregnancyStatus) {
        this.pregnancyStatus = pregnancyStatus;
    }
}
