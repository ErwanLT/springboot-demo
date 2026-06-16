package fr.eletutour.properties.testing;

public class ScoreTools {

    public static long applyBonus(long score, int bonusPct) {
        return score + (score * bonusPct / 100);
    }
}