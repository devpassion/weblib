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
package org.ecosystems.weblib.tools;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Outils de traitement de tableaux.
 * 
 * 
 */
public final class ArrayTools
{

    private ArrayTools()
    {
    }

    /**
     * Indique si un élément est présent dans une énumération.
     * 
     * @param <T> type des éléments de l'énumération
     * @param search énumération
     * @param needed élément à rechercher (basé sur {@link Object#equals(Object)})
     * @return vrai si l'élément existe, faux sinon
     */
    public static <T> boolean isInEnum(final Enumeration<? super T> search, final T needed)
    {
        while (search.hasMoreElements())
        {
            if (search.nextElement().equals( needed ))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Concatène une série de tableaux.
     * 
     * @param <T> Type des éléments du tableau
     * 
     * @param clazz Classe des éléments
     * @param arrays tableaux à concaténer
     * @return Tableau contenant tous les tableaux concaténés, dans l'ordre
     */
    @SuppressWarnings("unchecked")
    @SafeVarargs
    public static <T> T[] concat(final Class<T> clazz, final T[]... arrays)
    {
        final List<T> list = new ArrayList<>();

        for (final T[] array : arrays)
        {
            if (array != null)
            {
                for (final T element : array)
                {
                    list.add( element );
                }
            }
        }

        return list.toArray( (T[]) Array.newInstance( clazz, list.size() ) );
    }
}
