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

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.ecosystems.lib.tools.StringTools;
import org.ecosystems.weblib.log.LoggerAdapter;

/**
 * RessourceBundle Gérant l'UTF8.
 */
public class UTF8PropertyResourceBundle extends ResourceBundle
{
    private static final LoggerAdapter LOGGER_ADAPTER = new LoggerAdapter( UTF8PropertyResourceBundle.class );

    private final PropertyResourceBundle bundle;

    /**
     * RessourceBundle Gérant l'UTF8.
     * 
     * @param bundle ressource
     */
    UTF8PropertyResourceBundle(final PropertyResourceBundle bundle)
    {
        this.bundle = bundle;
    }

    @Override
    public Enumeration<String> getKeys()
    {
        return this.bundle.getKeys();
    }

    @Override
    protected Object handleGetObject(final String key)
    {
        LOGGER_ADAPTER.trace( "clé demandée : " + StringTools.getStringOrNull( key ) );
        final String value = (String) this.bundle.handleGetObject( key );
        try
        {
            return new String( value.getBytes( "ISO-8859-1" ), "UTF-8" );
        }
        catch (UnsupportedEncodingException e)
        {
            new LoggerAdapter( UTF8PropertyResourceBundle.class ).warn(
                    "erreur encodage utf 8 à la traduction de la clé " + StringTools.getStringOrNull( key )
                            + ", une chaine vide sera retournée", e );
            return "";
        }
    }

}
