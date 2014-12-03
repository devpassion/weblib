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
package org.ecosystems.weblib.html.form;

import javax.servlet.http.HttpServletRequest;

/**
 * Convertisseur de types.
 * 
 * @param <T> type vers lequel un type pourra être converti
 */
public interface Converter<T>
{
    /**
     * Converti une valeur. Si l'entrée est à null,on peut retourner la valeur null.
     * 
     * @param o Object à convertir
     * @return Objet converti
     * @throws ConversionException Levée si la conversion est impossible
     */
    T convert(Object o) throws ConversionException;

    /**
     * Retourne la chaine qui correspond à l'affichage d'un objet converti dans un champ input.
     * 
     * @param value valeur convertie
     * @return Chaine tel que l'objet est affiché dans la page web
     * @throws InvalidBoxedTypeException Levée si le unboxing est impossible
     */
    String toString(Object value) throws InvalidBoxedTypeException;

    /**
     * Récupère la classe vers laquelle la conversion doit se faire.
     * 
     * @return Classe vers laquelle l'objet peut être converti
     */
    Class<T> getConvertClass();

    /**
     * Défini la requête associée et le nom de champ en cas de traitement spécial.
     * 
     * @param name nom du champ
     * @param request requête
     */
    void setRequest(String name, HttpServletRequest request);

}
