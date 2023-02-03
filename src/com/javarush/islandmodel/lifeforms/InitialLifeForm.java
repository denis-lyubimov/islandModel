package com.javarush.islandmodel.lifeforms;

import com.javarush.islandmodel.lifeforms.lifeformenums.LifeFormType;
import com.javarush.islandmodel.lifeforms.additionalclasses.LifefFormFields;

public abstract class InitialLifeForm {
    protected Float weight;
    protected Integer maxAmountPerCell;
    protected Integer maxCellsPerMove;
    protected Float kgOfFoodToBeFullFeed;
    protected String sign;
    protected String name;
    protected LifeFormType lifeFormType;

    public InitialLifeForm(LifeFormType type, LifefFormFields fields){
        this.weight = fields.getWeight();
        this.maxAmountPerCell = fields.getMaxAmountPerCell();
        this.maxCellsPerMove = fields.getMaxCellsPerMove();
        this.kgOfFoodToBeFullFeed = fields.getKgOfFoodToBeFullFeed();
        this.sign = fields.getSign();
        this.name = type.toString();
        this.lifeFormType = type;
    }

    public Float getWeight() {
        return weight;
    }

    public Integer getMaxAmountPerCell() {
        return maxAmountPerCell;
    }

    public Integer getMaxCellsPerMove() {
        return maxCellsPerMove;
    }

    public Float getKgOfFoodToBeFullFeed() {
        return kgOfFoodToBeFullFeed;
    }

    public String getSign() {
        return sign;
    }

    public String getName() {
        return name;
    }

    public LifeFormType getLifeFormType() {
        return lifeFormType;
    }

    @Override
    public String toString() {
        return sign;
    }
}
