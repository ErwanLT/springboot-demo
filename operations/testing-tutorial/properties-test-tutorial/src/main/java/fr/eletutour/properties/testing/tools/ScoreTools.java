package fr.eletutour.properties.testing.tools;

public class ScoreTools {

    public static long applyBonus(long score, int bonusPct) {
        return score + (score * bonusPct / 100);
    }
}