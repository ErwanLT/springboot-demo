package fr.eletutour.gatling.exception;

/**
 * Exception levée lorsqu'un auteur n'est pas trouvé dans le système.
 * Cette exception est utilisée pour :
 * <ul>
 *     <li>Signaler l'absence d'un auteur lors d'une recherche</li>
 *     <li>Gérer les cas d'erreur liés aux auteurs inexistants</li>
 * </ul>
 */
public class AuthorNotFoundException extends Exception {

    /**
     * Constructeur de l'exception.
     *
     * @param msg Le message d'erreur détaillant la raison de l'exception
     */
    public AuthorNotFoundException(String msg) {
        super(msg);
    }
}
