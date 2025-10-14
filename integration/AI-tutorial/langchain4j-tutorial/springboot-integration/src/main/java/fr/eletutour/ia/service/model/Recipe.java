package fr.eletutour.ia.service.model;

import java.util.List;

public record Recipe(String name, List<String> ingredients, List<String> steps) {}
