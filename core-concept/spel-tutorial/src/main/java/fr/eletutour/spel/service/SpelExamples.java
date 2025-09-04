package fr.eletutour.spel.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SpelExamples {

    // --- Literal Expressions ---
    @Value("#{'Hello World'}") // String
    private String stringLiteral;

    @Value("#{100.5}") // Double
    private double doubleLiteral;

    @Value("#{true}") // Boolean
    private boolean booleanLiteral;

    // --- Referencing Beans and their Properties/Methods ---
    @Value("#{myCar.make}")
    private String carMake;

    @Value("#{myCar.isNewerThan(2020)}")
    private boolean isCarNewer;

    // --- Accessing application.properties ---
    @Value("${some.property.value}")
    private String fromProperties;

    // --- Using application.properties value in SpEL (with default) ---
    @Value("#{'${some.property.value:default value}'}")
    private String propertyWithDefault;

    // --- Operators ---
    @Value("#{myCar.year == 2021}") // Relational
    private boolean isCarFrom2021;

    @Value("#{myCar.make == 'GoodCar' and myCar.year > 2020}") // Logical
    private boolean isGoodAndNew;

    @Value("#{ (myCar.year > 2020) ? 'New' : 'Old' }") // Ternary
    private String carAgeCategory;

    // --- Accessing Collections ---
    @Value("#{ {1, 2, 3, 4} }") // Inline List
    private List<Integer> numberList;

    @Value("#{ {1, 2, 3, 4}[0] }") // Access by index
    private int firstNumber;

    @Value("#{ {'key1': 'value1', 'key2': 'value2'} }") // Inline Map
    private Map<String, String> valueMap;

    @Value("#{ {'key1': 'value1', 'key2': 'value2'}['key2'] }") // Access by key
    private String valueFromMap;

    // --- Type Operator and Safe Navigation ---
    @Value("#{T(java.util.UUID).randomUUID().toString()}")
    private String randomUuid;

    // Note: The 'engine' property does not exist on Car, so this will be null
    @Value("#{myCar.engine?.horsepower}") // Safe navigation on a null property
    private Integer engineHorsepower;

    // --- Calling bean methods with arguments ---
    @Value("#{spelUtility.isGreaterThan(100, 50)}")
    private boolean is100GreaterThan50;

    // Getters for verification
    public String getStringLiteral() { return stringLiteral; }
    public double getDoubleLiteral() { return doubleLiteral; }
    public boolean isBooleanLiteral() { return booleanLiteral; }
    public String getCarMake() { return carMake; }
    public boolean isCarNewer() { return isCarNewer; }
    public String getFromProperties() { return fromProperties; }
    public String getPropertyWithDefault() { return propertyWithDefault; }
    public boolean isCarFrom2021() { return isCarFrom2021; }
    public boolean isGoodAndNew() { return isGoodAndNew; }
    public String getCarAgeCategory() { return carAgeCategory; }
    public List<Integer> getNumberList() { return numberList; }
    public int getFirstNumber() { return firstNumber; }
    public Map<String, String> getValueMap() { return valueMap; }
    public String getValueFromMap() { return valueFromMap; }
    public String getRandomUuid() { return randomUuid; }
    public Integer getEngineHorsepower() { return engineHorsepower; }
    public boolean isIs100GreaterThan50() { return is100GreaterThan50; }
}