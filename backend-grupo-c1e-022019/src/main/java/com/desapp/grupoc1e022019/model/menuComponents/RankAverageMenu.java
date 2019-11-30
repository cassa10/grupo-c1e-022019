package com.desapp.grupoc1e022019.model.menuComponents;

import com.desapp.grupoc1e022019.model.EntityId;

import javax.persistence.Entity;

@Entity
public class RankAverageMenu extends EntityId {

    private Integer ratingsAmount;
    private Integer ratingSum;

    public RankAverageMenu(){
        ratingsAmount = 0;
        ratingSum = 0;
    }

    public RankAverageMenu(int ratingsAmount, int ratingSum){
        setRatingsAmount(ratingsAmount);
        setRatingSum(ratingSum);
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

    public void setRatingsAmount(Integer ratingsAmount) {
        this.ratingsAmount = ratingsAmount;
    }

    public Integer getRatingSum() {
        return ratingSum;
    }

    public void setRatingSum(Integer ratingSum) {
        this.ratingSum = ratingSum;
    }
}
