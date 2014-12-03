/**
 * Copyright (C) 2011 e-cosystems — Tous droits réservés.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.ecosystems.weblib.html;

/**
 * Attribut HTML.
 * 
 * @param <T> type de l'attribut
 */
public interface Attribute<T>
{
    /**
     * Nom de l'attribut, doit correspondre au nom déclaré dans le type.
     * 
     * @return nom d'attribut
     */
    String getName();

    /**
     * Indique si l'attribut est requis.
     * 
     * @return vrai si l'attribut est requis
     */
    // public abstract boolean isRequired();

    /**
     * Valeur de l'attribut, converti depuis une string.
     * 
     * @return valeur de l'attribut
     */
    T getValue();

    /**
     * Récupère le type de l'attribut.
     * 
     * @return type d'attribut
     */
    AttributeType<T> getAttributeType();
}
