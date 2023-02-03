package com.javarush.islandmodel.lifeforms.additionalclasses;

import com.javarush.islandmodel.lifeforms.animals.Animal;
import com.javarush.islandmodel.lifeforms.animals.carnivores.*;
import com.javarush.islandmodel.lifeforms.animals.herbivores.*;
import com.javarush.islandmodel.lifeforms.lifeformenums.LifeFormType;
import com.javarush.islandmodel.lifeforms.plants.Plant;
import com.javarush.islandmodel.settings.LifeFormsSettings;

public class LifeFormFactory {

    private LifeFormFactory() {
    }

    private static class SingletonHolder {
        public static final LifeFormFactory HOLDER_INSTANCE = new LifeFormFactory();
    }

    public static LifeFormFactory getInstance() {
        return LifeFormFactory.SingletonHolder.HOLDER_INSTANCE;
    }

    public Animal createAnimal(LifeFormType type) {
        return switch (type) {
            case ANACONDA -> new Anaconda(type, LifeFormsSettings.getInstance().getLifefFormFields(type));
            case BEAR -> new Bear(type, LifeFormsSettings.getInstance().getLifefFormFields(type));
            case BOAR -> new Boar(type, LifeFormsSettings.getInstance().getLifefFormFields(type));
            case BUFFALO -> new Buffalo(type, LifeFormsSettings.getInstance().getLifefFormFields(type));
            case CATERPILLAR -> new Caterpillar(type, LifeFormsSettings.getInstance().getLifefFormFields(type));
            case DEER -> new Deer(type, LifeFormsSettings.getInstance().getLifefFormFields(type));
            case DUCK -> new Duck(type, LifeFormsSettings.getInstance().getLifefFormFields(type));
            case EAGLE -> new Eagle(type, LifeFormsSettings.getInstance().getLifefFormFields(type));
            case FOX -> new Fox(type, LifeFormsSettings.getInstance().getLifefFormFields(type));
            case GOAT -> new Goat(type, LifeFormsSettings.getInstance().getLifefFormFields(type));
            case HORSE -> new Horse(type, LifeFormsSettings.getInstance().getLifefFormFields(type));
            case MOUSE -> new Mouse(type, LifeFormsSettings.getInstance().getLifefFormFields(type));
            case RABBIT -> new Rabbit(type, LifeFormsSettings.getInstance().getLifefFormFields(type));
            case SHEEP -> new Sheep(type, LifeFormsSettings.getInstance().getLifefFormFields(type));
            case WOLF -> new Wolf(type, LifeFormsSettings.getInstance().getLifefFormFields(type));
            default -> {
                try {
                    throw new IllegalStateException("LifeFormFactory does not know this animal type: " + type);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
                yield null;
            }
        };
    }

    public Plant createPlant(LifeFormType type) {
        return switch (type) {
            case PLANT -> new Plant(type, LifeFormsSettings.getInstance().getLifefFormFields(type));
            default -> {
                try {
                    throw new IllegalStateException("LifeFormFactory does not know this animal type: " + type);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
                yield null;
            }
        };
    }

}
