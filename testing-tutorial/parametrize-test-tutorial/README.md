# Tests Paramétrés Tutorial

Ce module démontre l'utilisation des tests paramétrés avec JUnit 5 pour tester votre application Java.

## Description

Ce tutoriel présente différentes approches pour écrire des tests paramétrés en utilisant JUnit 5. Il couvre plusieurs sources de données et méthodes de test pour valider le comportement de votre code.

## Fonctionnalités

- Tests paramétrés avec différentes sources de données
- Validation de fonctions mathématiques
- Validation de fonctions de manipulation de chaînes
- Tests avec des énumérations
- Tests avec des valeurs nulles et vides

## Types de Tests

### 1. Tests avec @ValueSource

```java
@ParameterizedTest
@ValueSource(ints = { 2, 4 })
void checkPairNumber(int number) {
    assertEquals(0, MathTools.isEven(number));
}
```

### 2. Tests avec @CsvSource et @CsvFileSource

```java
@ParameterizedTest
@CsvSource({ "2, pair", "3, impair" })
void checkCsvSource(int number, String expected) {
    assertEquals(StringUtils.equals(expected, "pair") ? 0 : 1, MathTools.isEven(number));
}
```

### 3. Tests avec @MethodSource

```java
@ParameterizedTest
@MethodSource("testArgs")
void checkExplicitMethodSource(String word) {
    assertTrue(StringTools.isAlphanumeric(word));
}
```

### 4. Tests avec @EnumSource

```java
@ParameterizedTest
@EnumSource(Month.class)
void checkEnumSourceValue(Month month) {
    assertNotNull(month);
}
```

### 5. Tests avec @NullSource et @EmptySource

```java
@ParameterizedTest
@NullAndEmptySource
void checkNullAndEmpty(String value) {
    assertTrue(StringTools.isEmpty(value));
}
```

## Structure du Projet

- `src/main/java/` : Contient les classes à tester
  - `MathTools.java` : Fonctions mathématiques
  - `StringTools.java` : Fonctions de manipulation de chaînes
  - `Month.java` : Énumération des mois
- `src/test/java/` : Contient les tests paramétrés
  - `ValueSourceTest.java` : Tests avec @ValueSource
  - `CsvSourceTest.java` : Tests avec @CsvSource
  - `MethodSourceTest.java` : Tests avec @MethodSource
  - `EnumSourceTest.java` : Tests avec @EnumSource
  - `NullEmptySourceTest.java` : Tests avec @NullSource et @EmptySource

## Comment Exécuter

```bash
mvn test
```

## Points Clés

- Utilisation de différentes sources de données pour les tests
- Tests de fonctions mathématiques et de manipulation de chaînes
- Tests avec des énumérations
- Tests avec des valeurs nulles et vides
- Tests avec des fichiers CSV externes
- Tests avec des méthodes externes