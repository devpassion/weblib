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
package org.ecosystems.weblib.lang;

import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Ressource Bundle gérant l'UTF-8.
 */
public abstract class UTF8ResourceBundle
{

    /**
     * Récupère le bundle.
     * 
     * @param name Nom du bundle recherché
     * @return Bundle, ou null si il n'st pas trouvé
     */
    public static final ResourceBundle getBundle(final String name)
    {
        return getBundle( ResourceBundle.getBundle( name ) );
    }

    /**
     * Récupère un bundle localisé.
     * 
     * @param name Nom du bundle recherché
     * @param locale locale recherchée
     * @return Bundle, ou null si il n'st pas trouvé
     */
    public static final ResourceBundle getBundle(final String name, final Locale locale)
    {
        return getBundle( ResourceBundle.getBundle( name, locale ) );
    }

    /**
     * Récupère un bundle localisé.
     * 
     * @param name Nom du bundle recherché
     * @param locale locale recherchée
     * @param loader chargeur de classes
     * @return Bundle, ou null si il n'st pas trouvé
     */
    public static ResourceBundle getBundle(final String name, final Locale locale, final ClassLoader loader)
    {
        return getBundle( ResourceBundle.getBundle( name, locale, loader ) );
    }

    private static ResourceBundle getBundle(final ResourceBundle bundle)
    {
        if (!(bundle instanceof PropertyResourceBundle))
            return bundle;
        return new UTF8PropertyResourceBundle( (PropertyResourceBundle) bundle );
    }

}
