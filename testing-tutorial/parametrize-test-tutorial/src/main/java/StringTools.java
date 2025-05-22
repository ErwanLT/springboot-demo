import org.apache.commons.lang3.StringUtils;

/**
 * Classe utilitaire pour la manipulation des chaînes de caractères.
 * Cette classe fournit :
 * <ul>
 *     <li>Des méthodes de validation de chaînes</li>
 *     <li>Des vérifications de format</li>
 *     <li>Des utilitaires de test de chaînes</li>
 * </ul>
 */
public class StringTools {

    /**
     * Vérifie si une chaîne est alphanumérique.
     *
     * @param s La chaîne à vérifier
     * @return true si la chaîne ne contient que des lettres et des chiffres, false sinon
     */
    public static boolean isAlphanumeric(String s){
        return StringUtils.isAlphanumeric(s);
    }

    /**
     * Vérifie si une chaîne est vide ou ne contient que des espaces.
     *
     * @param s La chaîne à vérifier
     * @return true si la chaîne est vide ou ne contient que des espaces, false sinon
     */
    public static boolean isBlank(String s){
        return StringUtils.isBlank(s);
    }

    /**
     * Vérifie si une chaîne est vide.
     *
     * @param s La chaîne à vérifier
     * @return true si la chaîne est vide, false sinon
     */
    public static boolean isEmpty(String s){
        return StringUtils.isEmpty(s);
    }
}