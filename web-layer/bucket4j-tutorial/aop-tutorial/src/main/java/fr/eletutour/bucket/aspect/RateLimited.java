/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of aop-tutorial.
 *
 * aop-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * aop-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with aop-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.bucket.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimited {
    /**
     * Type de limite à appliquer : par IP, par API key, etc.
     * Cela permettra d’adapter la logique dans l’aspect.
     */
    String strategy() default "global";
}
