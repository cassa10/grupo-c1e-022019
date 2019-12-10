package com.desapp.grupoc1e022019.model.menuComponents;

import com.desapp.grupoc1e022019.model.EntityId;

import javax.persistence.Entity;

@Entity
public class RankAverageMenu extends EntityId {

    private Integer ratingsAmount;
    private Integer ratingSum;
    private double rankAverage;

    public RankAverageMenu(){
        ratingsAmount = 0;
        ratingSum = 0;
        rankAverage = 0;
    }

    public RankAverageMenu(int ratingsAmount, int ratingSum){
        setRatingsAmount(ratingsAmount);
        setRatingSum(ratingSum);
        setRankAverage(calculateAverage());
    }

    public Integer getRatingsAmount(){
        return ratingsAmount;
    }

    public void addRating(Integer score){
        ratingsAmount++;
        ratingSum = ratingSum + score;
        rankAverage = calculateAverage();
    }

    public double calculateAverage(){
        if(ratingsAmount > 0)
            return ratingSum.doubleValue() / ratingsAmount;
        else{
            return 0;
        }
    }

    public boolean hasMoreTwentyRatesAmountAndAverageIsLessThanTwo(){
        return this.ratingsAmount >= 20 && (this.getRankAverage() < 2);
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

    public double getRankAverage() {
        return rankAverage;
    }

    public void setRankAverage(double rankAverage) {
        this.rankAverage = rankAverage;
    }
}
