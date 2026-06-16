package fr.eletutour.properties.testing;

public class ScoreTools {

    public static int applyBonus(int score,
                                 int bonusPct) {

        return score + (score * bonusPct / 100);
    }
}