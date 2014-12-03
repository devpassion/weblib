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
 * Forme de logo.
 */
public final class Forme extends FixItem
{
    /**
     * Espace de nom des chaînes de langues de forme
     */
    public static final String NAMESPACE = "shape";

    /**
     * Forme circulaire
     */
    public static final Forme CIRCLE = new Forme( NAMESPACE, "circle", "circle" );

    /**
     * Forme triangulaire
     */
    public static final Forme TRIANGLE = new Forme( NAMESPACE, "triangle", "triangle" );

    /**
     * Forme carrée
     */
    public static final Forme CARRE = new Forme( NAMESPACE, "carre", "carre" );

    /**
     * Forme rectangulaire
     */
    public static final Forme RECTANGLE = new Forme( NAMESPACE, "rectangle", "rectangle" );

    /**
     * Forme ovale
     */
    public static final Forme OVALE = new Forme( NAMESPACE, "ovale", "ovale" );

    public static final Map<String, Forme> itemsMap = new HashMap<String, Forme>();

    /**
     * Crée une nouvelle forme.
     * 
     * @param namespace espace de nom de la traduction du nom de la forme
     * @param name nom de la forme
     * @param langKey clé de langue du nom de la forme
     */
    private Forme(final String namespace, final String name, final String langKey)
    {
        super( namespace, name, langKey );

    }

    @Override
    public FixItem fromName(String itemName)
    {
        if (itemsMap.size() == 0)
        {
            itemsMap.put( CIRCLE.getName(), CIRCLE );
            itemsMap.put( TRIANGLE.getName(), TRIANGLE );
            itemsMap.put( CARRE.getName(), CARRE );
            itemsMap.put( RECTANGLE.getName(), RECTANGLE );
            itemsMap.put( OVALE.getName(), OVALE );
        }
        return itemsMap.get( itemName );
    }

}
