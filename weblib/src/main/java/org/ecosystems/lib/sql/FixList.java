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
package org.ecosystems.lib.sql;

import java.util.List;

import org.ecosystems.lib.tools.StringTools;

/**
 * Liste d'items fixes.
 * 
 * @param <T> Type d'item fixe
 */
public abstract class FixList<T extends FixItem>
{

    /**
     * Constructeur par défaut.
     */
    public FixList()
    {
        super();
    }

    /**
     * Récupère tous les items.
     * 
     * @return Ensemble des items
     */
    public abstract List<T> getAll();

    /**
     * Recupère un singleton correspondant à un item dont le nom est spécifié.
     * 
     * @param name nom de l'item
     * @return Item
     * @throws IllegalArgumentException Levée si le nom d'item est invalide
     */
    public T fromString(final String name) throws IllegalArgumentException
    {
        for (T item : this.getAll())
        {
            if (item.getName().equals( name ))
            {
                return item;
            }
        }
        throw new IllegalArgumentException( "item invalide : " + StringTools.getStringOrNull( name ) );
    }
}
