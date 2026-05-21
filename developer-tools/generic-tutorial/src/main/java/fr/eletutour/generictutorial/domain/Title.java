package fr.eletutour.generictutorial.domain;

public final class Title {

    private final String value;

    public Title(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (value.length() < 3 || value.length() > 255) {
            throw new IllegalArgumentException("Title must be between 3 and 255 characters");
        }
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Title t)) return false;
        return value.equals(t.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}