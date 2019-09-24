package com.desapp.grupoc1e022019;

import com.desapp.grupoc1e022019.model.menuComponents.RankAverageMenu;
import org.junit.Assert;
import org.junit.Test;

public class RankAverageMenuTest {

    @Test
    public void testARankAverageMenuWhenItGetsRatingsAmountReturnsZeroAsIntegerAndAverageReturnsZeroAsDouble(){
        RankAverageMenu rankAverageMenu = aRankAverageMenu();
        Assert.assertEquals(rankAverageMenu.getRatingsAmount(),new Integer(0));
        Assert.assertEquals(rankAverageMenu.average(),new Double(0));
    }

    @Test
    public void testGivenARankAverageMenuWhenItAddsRatingFiveScoreThenGetRatingsAmountReturnsOneAsIntegerAndAverageReturnsFiveAsDouble(){
        RankAverageMenu rankAverageMenu = aRankAverageMenu();
        rankAverageMenu.addRating(5);

        Assert.assertEquals(rankAverageMenu.getRatingsAmount(),new Integer(1));
        Assert.assertEquals(rankAverageMenu.average(),new Double(5));
    }

    @Test
    public void testGivenARankAverageMenuWhenItAddsRatingFourAndThreeScoresThenItGetsRatingsAmountTwoAsIntegerAndAverageReturnsThreePointFiveAsDouble(){
        RankAverageMenu rankAverageMenu = aRankAverageMenu();
        rankAverageMenu.addRating(4);
        rankAverageMenu.addRating(3);

        Assert.assertEquals(rankAverageMenu.getRatingsAmount(),new Integer(2));
        Assert.assertEquals(rankAverageMenu.average(),new Double(3.5));
    }

    @Test
    public void testGivenARankAverageMenuWith19RatesWith4ScoreWhenItRecievesHasMoreTwentyRatesAmountAndAverageLessThanTwoThenItReturnsFalseAndAverageIsFourAndRatingAmount19(){
        RankAverageMenu rankAverageMenu = aRankAverageMenu();
        addNRatingsWithScore(rankAverageMenu,19,4);

        Assert.assertEquals(rankAverageMenu.average(),new Double(4));
        Assert.assertEquals(rankAverageMenu.getRatingsAmount(),new Integer(19));
        Assert.assertFalse(rankAverageMenu.hasMoreTwentyRatesAmountAndAverageIsLessThanTwo());
    }

    @Test
    public void testGivenARankAverageMenuWith19RatesWith2ScoreAndOneRateWithScore3WhenItRecievesHasMoreTwentyRatesAmountAndAverageLessThanTwoThenItReturnsFalseAndAverageIs2Point05AndRatingAmount20(){
        RankAverageMenu rankAverageMenu = aRankAverageMenu();
        addNRatingsWithScore(rankAverageMenu,19,2);
        rankAverageMenu.addRating(3);

        Assert.assertEquals(rankAverageMenu.average(),new Double(2.05));
        Assert.assertEquals(rankAverageMenu.getRatingsAmount(),new Integer(20));
        Assert.assertFalse(rankAverageMenu.hasMoreTwentyRatesAmountAndAverageIsLessThanTwo());
    }

    @Test
    public void testGivenARankAverageMenuWith19RatesWith2ScoreAndOneRateWithScore1WhenItRecievesHasMoreTwentyRatesAmountAndAverageLessThanTwoThenItReturnsTrueAndAverageIs1Point95AndRatingAmount20(){
        RankAverageMenu rankAverageMenu = aRankAverageMenu();
        addNRatingsWithScore(rankAverageMenu,19,2);
        rankAverageMenu.addRating(1);

        Assert.assertEquals(rankAverageMenu.average(),new Double(1.95));
        Assert.assertEquals(rankAverageMenu.getRatingsAmount(),new Integer(20));
        Assert.assertTrue(rankAverageMenu.hasMoreTwentyRatesAmountAndAverageIsLessThanTwo());
    }

    private RankAverageMenu aRankAverageMenu(){
        return new RankAverageMenu();
    }

    private void addNRatingsWithScore(RankAverageMenu rankAverageMenu,int nTimes,int score){
        for(int i=0; i < nTimes ; i++){
            rankAverageMenu.addRating(score);
        }
    }
}
