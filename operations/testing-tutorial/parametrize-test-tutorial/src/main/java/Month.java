/**
 * Énumération représentant les mois de l'année.
 * Cette énumération contient :
 * <ul>
 *     <li>Le nom de chaque mois</li>
 *     <li>Le nombre de jours dans chaque mois</li>
 *     <li>Les constantes pour tous les mois de l'année</li>
 * </ul>
 */
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

    /**
     * Constructeur de l'énumération.
     *
     * @param name Le nom du mois
     * @param nbJours Le nombre de jours dans le mois
     */
    Month(String name, int nbJours) {
        this.name = name;
        this.nbJours = nbJours;
    }
}