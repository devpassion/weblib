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

import java.util.Collection;
import java.util.Iterator;

/**
 * Outils de gestion de données aléatoires.
 */
public final class RandomTools
{
    private RandomTools()
    {
    }

    /**
     * Choisit une valeur aléatoire dans une collection.
     * 
     * @param <T> Type de la collection
     * 
     * @param set collection où une valeur doit être tirée
     * @return Valeur tirée
     */
    public static <T> T getRandomElement(final Collection<T> set)
    {
        final Iterator<T> it = set.iterator();
        final long r = getRandomLong( 0, set.size() );
        long cnt = 0;
        while (it.hasNext())
        {
            if (r == cnt++)
            {
                return it.next();
            }
            it.next();
        }
        throw new IllegalArgumentException( "pas d'élément à retourner, taille du set : " + Integer.valueOf( set.size() ).toString()
                + " / index aléatoire : " + Long.valueOf( r ).toString() );
    }

    /**
     * Récupère un nombre entier aléatoire plus grand ou égal à la valeur de début et strictement inférieur à la valeur
     * de fin.
     * 
     * @param begin Début d'intervalle
     * @param end fin d'intervalle
     * @return valeur entière aléatoire
     */
    public static long getRandomLong(final long begin, final long end)
    {
        return (long) (((end - begin) * Math.random()) + begin);
    }
}
