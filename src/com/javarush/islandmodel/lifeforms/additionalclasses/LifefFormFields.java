package com.javarush.islandmodel.lifeforms.additionalclasses;

public class LifefFormFields {

    private final Float weight;
    private final Integer maxAmountPerCell;
    private final Integer maxCellsPerMove;
    private final Float kgOfFoodToBeFullFeed;
    private final String sign;

    public LifefFormFields(String sign, Float weight, Integer maxAmountPerCell, Integer maxCellsPerMove, Float kgOfFoodToBeFullFeed) {
        this.sign = sign;
        this.weight = weight;
        this.maxAmountPerCell = maxAmountPerCell;
        this.kgOfFoodToBeFullFeed = kgOfFoodToBeFullFeed;
        this.maxCellsPerMove = maxCellsPerMove;
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

    @Override
    public String toString() {
        return "LifefFormFields{sign=" + sign + ";weight=" + weight + ";maxAmountPerCell="
                + maxAmountPerCell + ";maxCellsPerMove=" + maxCellsPerMove + ";kgOfFoodToBeFeed="
                + kgOfFoodToBeFullFeed + "}";
    }
}
