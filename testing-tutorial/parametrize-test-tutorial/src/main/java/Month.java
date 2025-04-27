public enum Month {
    JANVIER("janvier", 31),
    FEVRIER("fevrier", 28),
    MARS("mars", 31),
    AVRIL("avril", 30),
    MAI("mai", 31),
    JUIN("juin", 30),
    JUILLET("juillet", 31),
    AOUT("aout", 31),
    SEPTEMBRE("septembre", 30),
    OCTOBRE("octobre", 31),
    NOVEMBRE("novembre", 30),
    DECEMBRE("décembre", 31);

    private final String name;
    private final int nbJours;

    Month(String name, int nbJours) {
        this.name = name;
        this.nbJours = nbJours;
    }
}