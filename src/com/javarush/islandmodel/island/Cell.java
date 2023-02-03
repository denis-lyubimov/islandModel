package com.javarush.islandmodel.island;

import com.javarush.islandmodel.lifeforms.animals.Animal;
import com.javarush.islandmodel.lifeforms.lifeformenums.LeaveCellType;
import com.javarush.islandmodel.lifeforms.lifeformenums.LifeFormType;
import com.javarush.islandmodel.lifeforms.plants.Plant;
import com.javarush.islandmodel.settings.LifeFormsSettings;

import java.util.*;

public class Cell {
    private final int rownum;
    private final int columnnum;
    private Integer cellId;
    private final Integer cellNumber;

    Map<LifeFormType, Set<Animal>> animalMap = new HashMap<>();
    Map<LifeFormType, Set<Animal>> maleAnimalMap = new HashMap<>();
    Map<LifeFormType, Set<Animal>> femaleAnimalMap = new HashMap<>();
    Plant plants;
    Integer plantsCounter;

    public Cell(int rownum, int columnnum, Integer cellId) {
        this.rownum = rownum;
        this.columnnum = columnnum;
        this.plants = new Plant(LifeFormType.PLANT, LifeFormsSettings.getInstance().getFieldsMap().get(LifeFormType.PLANT));
        this.cellNumber = cellId;
        setCellId(this.rownum , this.columnnum);
        initializeAnimalMap();
        this.plantsCounter = 0;
    }

    //init =============================
    private void setCellId(int coordinateX, int coordinateY) {
        this.cellId = Integer.parseInt(coordinateX + "" + coordinateY);
    }

    private void initializeAnimalMap() {
        for (LifeFormType lifeFormType : LifeFormType.values()) {
            if (lifeFormType == LifeFormType.PLANT) {
                continue;
            }
            animalMap.put(lifeFormType, new HashSet<Animal>());
            maleAnimalMap.put(lifeFormType, new HashSet<Animal>());
            femaleAnimalMap.put(lifeFormType, new HashSet<Animal>());

        }
    }

    //setter getter =============================
    public int getRownum() {
        return rownum;
    }

    public int getColumnnum() {
        return columnnum;
    }

    public Integer getCellId() {
        return cellId;
    }

    public Map<LifeFormType, Set<Animal>> getAnimalMap() {
        return animalMap;
    }

    public Map<LifeFormType, Set<Animal>> getMaleAnimalMap() {
        return maleAnimalMap;
    }

    public Map<LifeFormType, Set<Animal>> getFemaleAnimalMap() {
        return femaleAnimalMap;
    }

    public Integer getPlantsCount() {
        return plantsCounter;
    }

    public Plant getPlants() {
        return plants;
    }

    //animals =============================


    public boolean addAnimal(Animal animal) {
        boolean result = false;

        LifeFormType lifeFormType = animal.getLifeFormType();
        if (animalMap.get(lifeFormType).size() >= animal.getMaxAmountPerCell()) {
            return result;
        }

        String gender = animal.getGender();

        result = switch (gender) {
            case "male" -> animalMap.get(lifeFormType).add(animal) && maleAnimalMap.get(lifeFormType).add(animal);
            case "female" -> animalMap.get(lifeFormType).add(animal) && femaleAnimalMap.get(lifeFormType).add(animal);
            default -> {
                try {
                    throw new IllegalStateException("Unknown gender " + gender + " in addAnimal method for cell " + cellId);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
                yield false;
            }
        };
        return result;
    }

    public boolean removeAnimal(Animal animal, LeaveCellType leaveCellType) {
        boolean result = false;
        LifeFormType lifeFormType = animal.getLifeFormType();

        if (animalMap.get(lifeFormType).size() == 0) {
            try {
                throw new RuntimeException("there is 0 animals of type " + lifeFormType.toString() + " at cell " + cellId);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            return result;
        }

        String gender = animal.getGender();
        switch (leaveCellType) {
            case EATEN -> result = switch (gender) {
                case "male" ->
                        animalMap.get(lifeFormType).remove(animal) && maleAnimalMap.get(lifeFormType).remove(animal);
                case "female" ->
                        animalMap.get(lifeFormType).remove(animal) && femaleAnimalMap.get(lifeFormType).remove(animal);
                default -> {
                    try {
                        throw new IllegalStateException("Unknown gender " + gender + " in removeAnimal method for cell " + cellId);
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                    yield false;
                }
            };
            case MOVED, DIED -> result = switch (gender) {
                case "male" -> maleAnimalMap.get(lifeFormType).remove(animal);
                case "female" -> femaleAnimalMap.get(lifeFormType).remove(animal);
                default -> {
                    try {
                        throw new IllegalStateException("Unknown gender " + gender + " in removeAnimal method for cell " + cellId);
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                    yield false;
                }
            };
        }
        return result;
    }

    public Animal getAnimalByType(LifeFormType lifeFormType) {
        Animal animal = null;
        Iterator<Animal> animalSetIterator = animalMap.get(lifeFormType).iterator();
        if (animalSetIterator.hasNext()) {
            animal = animalSetIterator.next();
        }
        return animal;
    }

    public Animal getMaleAnimalByType(LifeFormType lifeFormType) {
        Animal animal = null;
        Iterator<Animal> animalSetIterator = maleAnimalMap.get(lifeFormType).iterator();
        if (animalSetIterator.hasNext()) {
            animal = animalSetIterator.next();
        }
        return animal;
    }

    public Animal getFemaleAnimalByType(LifeFormType lifeFormType) {
        Animal animal = null;
        Iterator<Animal> animalSetIterator = femaleAnimalMap.get(lifeFormType).iterator();
        if (animalSetIterator.hasNext()) {
            animal = animalSetIterator.next();
        }
        return animal;
    }

    //plants =============================
    public boolean addPlants(Integer plantsAmount) {
        Integer maxPlantsAtCell = LifeFormsSettings.getInstance().getFieldsMap().get(LifeFormType.PLANT).getMaxAmountPerCell();
        if (plantsAmount == 0 || plantsCounter >= maxPlantsAtCell) {
            return false;
        }
        if ((plantsCounter + plantsAmount) >= maxPlantsAtCell) {
            plantsCounter = maxPlantsAtCell;
            return true;
        }
        plantsCounter += plantsAmount;
        return true;
    }

    public Integer removePlant(Integer plantsAmount) {
        if (plantsAmount == 0 || plantsCounter == 0) {
            return 0;
        }
        if (plantsCounter <= plantsAmount) {
            plantsCounter = 0;
            return plantsCounter;
        }
        plantsCounter -= plantsAmount;
        return plantsAmount;
    }

    //counters
//    пенесети счетчики в класс счетчиков,им передавать мапу животных и тамуже считать

    public Integer getAnimalsCountByType(LifeFormType type) {
        return animalMap.get(type).size();
    }

    public Integer getMaleAnimalsCountByType(LifeFormType type) {
        return maleAnimalMap.get(type).size();
    }

    public Integer getFemaleAnimalsCountByType(LifeFormType type) {
        return femaleAnimalMap.get(type).size();
    }

    public Integer getAllAnimalsCount() {
        int counter = 0;
        for (Map.Entry<LifeFormType, Set<Animal>> pair : animalMap.entrySet()) {
            counter += pair.getValue().size();
        }
        return counter;
    }

    public Integer getAllMaleAnimalsCount() {
        int counter = 0;
        for (Map.Entry<LifeFormType, Set<Animal>> pair : maleAnimalMap.entrySet()) {
            counter += pair.getValue().size();
        }
        return counter;
    }

    public Integer getAllFemaleAnimalsCount() {
        int counter = 0;
        for (Map.Entry<LifeFormType, Set<Animal>> pair : femaleAnimalMap.entrySet()) {
            counter += pair.getValue().size();
        }
        return counter;
    }

    @Override
    public String toString() {
        String cellInfo = "Cell " + cellNumber + " ["+rownum+","+columnnum+"] : { ";
        StringBuilder result = new StringBuilder(cellInfo);
        LifeFormsSettings settings = LifeFormsSettings.getInstance();
        for (Map.Entry<LifeFormType, Set<Animal>> animalPair : getAnimalMap().entrySet()) {
            result.append(settings.getLifefFormFields(animalPair.getKey()).getSign()).append(" = ").append(animalPair.getValue().size()).append("; ");
        }
        result.append(settings.getLifefFormFields(LifeFormType.PLANT).getSign()).append(" = ").append(plantsCounter).append(" }");
        return result.toString();
    }
}
