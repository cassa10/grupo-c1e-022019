package com.desapp.grupoc1e022019.model.menuComponents;

public class RankAverageMenu {

    private Integer ratingsAmount;
    private Integer ratingSum;

    public RankAverageMenu(){
        ratingsAmount = 0;
        ratingSum = 0;
    }

    public Integer getRatingsAmount(){
        return ratingsAmount;
    }

    public void addRating(Integer score){
        ratingsAmount++;
        ratingSum = ratingSum + score;
    }

    public Double average(){
        if(ratingsAmount > 0)
            return ratingSum.doubleValue() / ratingsAmount;
        else{
            return ratingSum.doubleValue();
        }
    }

    public boolean hasMoreTwentyRatesAmountAndAverageIsLessThanTwo(){
        return this.ratingsAmount >= 20 && (this.average() < 2);
    }
}
