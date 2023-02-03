package com.javarush.islandmodel.lifeforms.animals;

import com.javarush.islandmodel.lifeforms.lifeformenums.LifeFormType;
import com.javarush.islandmodel.lifeforms.additionalclasses.LifefFormFields;
import com.javarush.islandmodel.settings.LifeFormsSettings;

import java.util.concurrent.ThreadLocalRandom;

public class Herbivore extends Animal {

    public Herbivore(LifeFormType type, LifefFormFields fields){
        super(type, fields);
    }

    protected Float getPlants() {
        float result = 0f;
        if (animalCell.getPlantsCount() == 0) {
            return result;
        }
        if (ThreadLocalRandom.current().nextInt(100) < LifeFormsSettings.getInstance().getFoodChance(lifeFormType, LifeFormType.PLANT)) {
            result = animalCell.getPlants().getWeight() * animalCell.removePlant((int) Math.max(this.kgOfFoodToBeFullFeed * 0.01, 1));
        }
        return result;
    }

    @Override
    public void eat() {
        float food = 0f;
        Animal animal = getMeat();
        Float plants = getPlants();
        if (animal == null && plants == 0f) {
            return;
        } else {
            food = eatAnimal(animal) + plants;
        }
        decreaseLevelOfHunger(food);
    }
}
