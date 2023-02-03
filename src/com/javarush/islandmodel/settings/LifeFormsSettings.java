package com.javarush.islandmodel.settings;


import com.javarush.islandmodel.lifeforms.additionalclasses.LifefFormFields;
import com.javarush.islandmodel.lifeforms.lifeformenums.LifeFormType;

import java.util.HashMap;
import java.util.Map;

public class LifeFormsSettings {

    private final Map<LifeFormType, LifefFormFields>  fieldsMap = new HashMap<>() {{
        put(LifeFormType.ANACONDA, new LifefFormFields("🐍", 15f, 30, 3, 8f));
        put(LifeFormType.BEAR, new LifefFormFields("🐻", 500f, 5, 2, 80f));
        put(LifeFormType.BOAR, new LifefFormFields("🐗", 400f, 50, 2, 50f));
        put(LifeFormType.BUFFALO, new LifefFormFields("🐃", 700f, 10, 3, 100f));
        put(LifeFormType.CATERPILLAR, new LifefFormFields("🐛", 0.01f, 1000, 0, 0f));
        put(LifeFormType.DEER, new LifefFormFields("🦌", 300f, 20, 4, 50f));
        put(LifeFormType.DUCK, new LifefFormFields("🦆", 1f, 200, 4, 0.15f));
        put(LifeFormType.EAGLE, new LifefFormFields("🦅", 6f, 20, 3, 1f));
        put(LifeFormType.FOX, new LifefFormFields("🦊", 8f, 30, 2, 2f));
        put(LifeFormType.GOAT, new LifefFormFields("🐐", 60f, 140, 3, 10f));
        put(LifeFormType.HORSE, new LifefFormFields("🐎", 400f, 20, 4, 60f));
        put(LifeFormType.MOUSE, new LifefFormFields("🐁", 0.05f, 500, 1, 0.01f));
        put(LifeFormType.PLANT, new LifefFormFields("🌿", 1f, 200, null, null));
        put(LifeFormType.RABBIT, new LifefFormFields("🐇", 2f, 150, 2, 0.45f));
        put(LifeFormType.SHEEP, new LifefFormFields("🐑", 70f, 140, 3, 15f));
        put(LifeFormType.WOLF, new LifefFormFields("🐺", 50f, 30, 3, 8f));
    }};

    private final Map<LifeFormType, Integer[]> foodChanceMap = new HashMap<>() {{
        put(LifeFormType.WOLF, new Integer[]{null, 0, 0, 0, 0, 10, 15, 60, 80, 60, 70, 15, 10, 40, 0, 0});
        put(LifeFormType.ANACONDA, new Integer[]{0, null, 15, 0, 0, 0, 0, 40, 40, 0, 0, 0, 0, 10, 0, 0});
        put(LifeFormType.FOX, new Integer[]{0, 0, null, 0, 0, 0, 0, 70, 90, 0, 0, 0, 0, 60, 40, 0});
        put(LifeFormType.BEAR, new Integer[]{0, 80, 0, null, 0, 40, 80, 80, 90, 70, 70, 50, 20, 10, 0, 0});
        put(LifeFormType.EAGLE, new Integer[]{0, 0, 10, 0, null, 0, 0, 90, 90, 0, 0, 0, 0, 80, 0, 0});
        put(LifeFormType.HORSE, new Integer[]{0, 0, 0, 0, 0, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100});
        put(LifeFormType.DEER, new Integer[]{0, 0, 0, 0, 0, 0, null, 0, 0, 0, 0, 0, 0, 0, 0, 100});
        put(LifeFormType.RABBIT, new Integer[]{0, 0, 0, 0, 0, 0, 0, null, 0, 0, 0, 0, 0, 0, 0, 100});
        put(LifeFormType.MOUSE, new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, null, 0, 0, 0, 0, 0, 90, 100});
        put(LifeFormType.GOAT, new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, null, 0, 0, 0, 0, 0, 100});
        put(LifeFormType.SHEEP, new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, 0, 0, 0, 0, 100});
        put(LifeFormType.BOAR, new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 50, 0, 0, null, 0, 0, 90, 100});
        put(LifeFormType.BUFFALO, new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, 0, 0, 100});
        put(LifeFormType.DUCK, new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, 90, 100});
        put(LifeFormType.CATERPILLAR, new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, 100});
    }};

    //the more LEVEL_OF_HUNGER is — the better, when it drops to 0 animal dies
    public static final Float DEFAULT_ANIMAL_LEVEL_OF_HUNGER = 70f;

    private LifeFormsSettings() {
    }

    private static class SingletonHolder {
        public static final LifeFormsSettings HOLDER_INSTANCE = new LifeFormsSettings();
    }

    public static LifeFormsSettings getInstance() {
        return LifeFormsSettings.SingletonHolder.HOLDER_INSTANCE;
    }

    public Map<LifeFormType, LifefFormFields> getFieldsMap() {
        return fieldsMap;
    }

    public LifefFormFields getLifefFormFields(LifeFormType type) {
        return fieldsMap.get(type);
    }

    public Integer getFoodChance(LifeFormType eater, LifeFormType eatable) {
        return foodChanceMap.get(eater)[eatable.ordinal()];
    }

    public Map<LifeFormType, Integer[]> getFoodChanceMap() {
        return foodChanceMap;
    }
}
