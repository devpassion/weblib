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
package org.ecosystems.weblibtests.model;

import java.util.HashMap;
import java.util.Map;

import org.ecosystems.lib.sql.FixItem;

/**
 * Portée de logo
 */
public class Scope extends FixItem
{
    /**
     * Espace de nom des chaînes de langues de scope
     */
    public static String NAMESPACE = "scope";

    /**
     * Scope local
     */
    public static Scope LOCAL = new Scope( NAMESPACE, "local", "local" );

    /**
     * Scope national
     */
    public static Scope NATIONAL = new Scope( NAMESPACE, "national", "national" );

    /**
     * Scope international
     */
    public static Scope INTERNATIONAL = new Scope( NAMESPACE, "international", "international" );

    public static final Map<String, Scope> itemsMap = new HashMap<String, Scope>();

    /**
     * @param name
     * @param langKey
     */
    private Scope(String namespace, String name, String langKey)
    {
        super( namespace, name, langKey );

    }

    @Override
    public FixItem fromName(String itemName)
    {
        if (itemsMap.size() == 0)
        {
            itemsMap.put( LOCAL.getName(), LOCAL );
            itemsMap.put( NATIONAL.getName(), NATIONAL );
            itemsMap.put( INTERNATIONAL.getName(), INTERNATIONAL );
        }
        return itemsMap.get( itemName );
    }

}
