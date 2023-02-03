package com.javarush.islandmodel.island;

import com.javarush.islandmodel.lifeforms.additionalclasses.LifeFormFactory;
import com.javarush.islandmodel.lifeforms.animals.Animal;
import com.javarush.islandmodel.lifeforms.lifeformenums.LifeFormType;
import com.javarush.islandmodel.settings.LifeFormsSettings;

import java.util.Collection;
import java.util.Random;

public class LifeFormGenerator {
    private final Island island;
    private final LifeFormFactory lifeFormFactory = LifeFormFactory.getInstance();

    public LifeFormGenerator(Island island) {
        this.island = island;
    }

    public void generateAnimals() {
        Random random = new Random();
        Collection<Cell> cells = island.getCellsMap().values();
        for (Cell cell : cells) {
            for (LifeFormType lifeFormType : LifeFormType.values()) {
                if (lifeFormType == LifeFormType.PLANT) {
                    continue;
                }
                int randomAmountOfAnimals = random.nextInt(1,Math.max(LifeFormsSettings.getInstance().getLifefFormFields(lifeFormType).getMaxAmountPerCell() / 4,2));
                for (int i = 0; i < randomAmountOfAnimals; i++) {
                    Animal animal = lifeFormFactory.createAnimal(lifeFormType);
                    cell.addAnimal(animal);
                    animal.setAnimalCell(cell);
                }
            }
        }
    }

    public void generatePlants() {
        Random random = new Random();
        Collection<Cell> cells = island.getCellsMap().values();
        for (Cell cell : cells) {
            int randomAmountOfPlants = random.nextInt(LifeFormsSettings.getInstance().getLifefFormFields(LifeFormType.PLANT).getMaxAmountPerCell());
            cell.addPlants(randomAmountOfPlants);
        }
    }
}

