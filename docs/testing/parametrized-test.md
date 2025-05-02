---
layout: default
title: Parametrized Test
---

# Tests Paramétrés avec JUnit 5

Nous avons déjà évoqué, lors d'un précédent article, [l'importance de tester son code](https://www.sfeir.dev/product/pourquoi-tester-son-code/). Vous avez peut être aussi pu lire les excellents articles de [Thibaut Rety](https://www.sfeir.dev/author/thibaut/) sur le TDD :

- [Késaco : Test Driven Development](https://www.sfeir.dev/kesaco-tdd/)
- [Le Test Driven Development (TDD), pour quoi faire ?](https://www.sfeir.dev/le-tdd-pourquoi-faire/)

Aujourd'hui, nous allons parler de tests paramétrés.

## Introduction aux Tests Paramétrés avec JUnit 5

Les tests paramétrés sont une fonctionnalité puissante de JUnit 5 qui permet d'exécuter un même test avec différentes valeurs d'entrée.  
Cela permet d'améliorer la couverture des tests en testant divers scénarios sans répéter le code du test.  
Dans cet article, nous explorerons les différentes annotations de JUnit 5 pour les tests paramétrés et illustrerons leur utilisation avec des exemples concrets.

[![](https://www.sfeir.dev/content/images/2024/04/8n6ogi.jpeg)](https://www.sfeir.dev/content/images/2024/04/8n6ogi.jpeg)

Nous verrons que si, c'est effectivement possible !

### Pré requis

Pour commencer, il vous faudra ajouter la dépendance suivante dans votre fichier `pom.xml`

```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-params</artifactId>
    <version>${junit-params.version}</version>
    <scope>test</scope>
</dependency>
```

N'oubliez pas de remplacer **${junit-params.version}** par la version de votre choix.

## Un premier exemple

Grâce à cette dépendance, vous avez maintenant accès à une nouvelle annotation _@ParameterizedTest_, qui comme son nom l’indique, permet au moteur JUnit d’exécuter ce test avec des valeurs d’entrées différentes.

**Exemple :**

```java
@ParameterizedTest
@ValueSource(ints = { 2, 4 })
void checkPairNumber(int number) {
    assertEquals(0, MathTools.isEven(number),
            "Le nombre fourni n’est pas un nombre pair");
}
```

Dans l’exemple ci-dessus, l’annotation _@ValueSource_ fournit plusieurs entrées à la méthode `checkPairNumber()`.  
Si nous avions dû écrire ce test en utilisant l'annotation @Test classique de JUnit, nous aurions dû écrire 2 tests pour couvrir les 2 entrées fournies alors que le test est le même.

Quand nous exécuterons le test ci-dessus, nous aurons le résultat suivant :

[![](https://www.sfeir.dev/content/images/2024/04/image-21.png)](https://www.sfeir.dev/content/images/2024/04/image-21.png)

résultat d'un test paramétré

Nous pouvons observer que mon test a été joué 2 fois, avec à chaque fois une valeur différente.

_Mais... Tu nous as parlé de l'annotation @ParameterizedTest et tu en as utilisé une deuxième : @ValueSource. À quoi sert elle cette annotation ?_  
Excellente question ! Nous allons voir la réponse de suite dans la prochaine partie.

## Les sources de données

Junit-jupiter-params offre plusieurs annotations pour préciser la source de données qui sera utilisée par nos tests. Dans cette partie, nous allons voir ces différentes sources et comment les utiliser.

### @ValueSource

L'annotation `@ValueSource` permet de fournir une liste de valeurs simples comme paramètres pour un test. Ces valeurs sont généralement de types primitifs ou des chaînes de caractères.

**Exemple :**

```java
@ParameterizedTest
@ValueSource(strings = { "a1", "b2" })
void checkAlphanumeric(String word) {
    assertTrue(StringTools.isAlphanumeric(word),
            "Le mot fourni n’est pas alphanumérique");
}
```

exemple @ValueSource

---

### @MethodSource

L'annotation `@MethodSource` permet de spécifier une méthode qui renvoie les paramètres pour un test. Cette méthode doit retourner un `Stream` ou une `Iterable` d'arguments.  
Nous avons 3 possibilités d'usage pour cette annotation :

- **Explicite** : Le test tentera de charger la méthode fournie.

```java
@ParameterizedTest
@MethodSource("testArgs")
void checkExplicitMethodSource(String word) {
    assertTrue(StringTools.isAlphanumeric(word), "Le mot fourni n’est pas alphanumérique");
}

static Stream<String> testArgs() {
    return Stream.of("a1", "b2");
}
```

@MethodeSource explicite

- **Implicite** : Le test recherchera la méthode source qui a le même nom que la méthode de test.

```java
@ParameterizedTest
@MethodSource
void checkImplicitMethodSource(String word) {
    assertTrue(StringTools.isAlphanumeric(word), "Le mot fourni n’est pas alphanumérique");
}

static Stream<String> checkImplicitMethodSource() {
    return Stream.of("a1", "b2");
}
```

@MethodeSource implicite

- **Externe** : Le test tentera de charger la méthode fournie se trouvant dans une autre classe

```java
@ParameterizedTest
@MethodSource(
        "providers.ExternalMethodSourceProvider#checkExternalMethodSourceArgs")
void checkExternalMethodSource(String word) {
    assertTrue(StringUtils.isAlphanumeric(word),
            "Le mot fourni n’est pas alphanumérique");
}
```

@MethodeSource externe

```java
package providers;

import java.util.stream.Stream;

public class ExternalMethodSourceProvider {
    static Stream<String> checkExternalMethodSourceArgs() {
        return Stream.of("a1",
                "b2");
    }
}
```

provider pour la méthode source

---

### @CsvSource

L'annotation `@CsvSource` permet de spécifier des données sous forme de tableau CSV pour un test. Chaque ligne du tableau représente un jeu de paramètres.

```java
@ParameterizedTest
@CsvSource({ "2, pair",
        "3, impair"})
void checkCsvSource(int number, String expected) {
    assertEquals(StringUtils.equals(expected, "pair") ? 0 : 1, MathTools.isEven(number));
}
```

@CsvSource exemple

Cette annotation se décline également sous la forme `@CsvFileSource` elle permet de charger un fichier CSV présent dans les ressources comme jeu de données :

```java
@ParameterizedTest
@CsvFileSource(files = "src/test/resources/csvSource.csv", numLinesToSkip = 1)
void checkCsvFileSource(int number, String expected) {
    assertEquals(StringUtils.equals(expected, "pair") ? 0 : 1, MathTools.isEven(number));
}

@ParameterizedTest
@CsvFileSource(files = "src/test/resources/csvSource_attributes.csv",
        delimiterString = "|",
        lineSeparator = "||",
        numLinesToSkip = 1)
void checkCsvFileSourceAttributes(int number, String expected) {
    assertEquals(StringUtils.equals(expected, "pair") ? 0 : 1, MathTools.isEven(number));
}
```

@CsvFileSource exemple

Petit bonus, pour les fichiers, on peut spécifier les séparateurs de champs et les fin de lignes.

---

### @EnumSource

L'annotation `@EnumSource` permet de fournir une énumération comme source de paramètre pour un test. Cela est utile pour tester des comportements basés sur des constantes énumérées.

Prenons l'enum suivante :

```java
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
```

Pour tester son comportement via l'annotation, rien de plus simple :

```java
@ParameterizedTest
@EnumSource(Month.class)
void checkEnumSourceValue(Month month) {
    assertNotNull(month);
}
```

Mon test testera tous les mois de mon enum

[![](https://www.sfeir.dev/content/images/2024/04/image-22.png)](https://www.sfeir.dev/content/images/2024/04/image-22.png)

Nous avons aussi la possibilité de spécifier quelles entrées de notre énumération nous voulons tester :

```java
@ParameterizedTest
@EnumSource(names = {"JUIN", "JUILLET"})
public void checkEnumSourceNames(Month month) {
    assertTrue(month.name().startsWith("JU"));
}
```

---

## Conclusion

Les tests paramétrés offrent une manière efficace d'effectuer des tests flexibles et complets en Java. En utilisant les annotations telles que `@ValueSource`, `@EnumSource`, `@CsvSource` et `@MethodSource`, nous pouvons fournir une variété de données pour nos tests, permettant ainsi une validation approfondie du comportement de notre code.  
Intégrer des tests paramétrés dans nos suites de tests améliore la robustesse et la fiabilité de nos applications Java.

[![](https://media.tenor.com/6o3h7CyxiecAAAAC/lotr-lord-of-the-rings.gif)](https://media.tenor.com/6o3h7CyxiecAAAAC/lotr-lord-of-the-rings.gif)