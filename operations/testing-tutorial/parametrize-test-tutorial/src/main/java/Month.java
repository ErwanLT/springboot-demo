/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of parametrize-test-tutorial.
 *
 * parametrize-test-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * parametrize-test-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with parametrize-test-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
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