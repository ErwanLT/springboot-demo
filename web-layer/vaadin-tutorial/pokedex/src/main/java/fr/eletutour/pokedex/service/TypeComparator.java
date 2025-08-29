package fr.eletutour.pokedex.service;



import fr.eletutour.pokedex.model.Type;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

public class TypeComparator implements Comparator<Type> {
    private final Collator collator = Collator.getInstance(Locale.FRENCH);

    @Override
    public int compare(Type type1, Type type2) {
        return collator.compare(type1.getName(), type2.getName());
    }
}
