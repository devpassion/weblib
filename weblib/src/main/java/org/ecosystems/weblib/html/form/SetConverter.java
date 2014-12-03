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

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.ecosystems.lib.tools.StringTools;
import org.ecosystems.weblib.log.LoggerAdapter;

/**
 * Convertisseur Transformant un tableau en Set.
 * 
 * @param <T> type des éléments du set renvoyé
 */
public abstract class SetConverter<T> implements Converter<Set<T>>
{

    private static final LoggerAdapter LOGGER_ADAPTER = new LoggerAdapter( SetConverter.class );

    @Override
    public Set<T> convert(final Object o) throws ConversionException
    {
        final Set<T> out = new HashSet<>();
        if (o.getClass().isAssignableFrom( Object[].class ))
        {
            final Object[] oa = (Object[]) o;

            LOGGER_ADAPTER.trace( "conversion de set ... " );
            for (final Object oo : oa)
            {
                if (oo != null)
                {
                    final T convert = this.getElementConverter().convert( oo );
                    LOGGER_ADAPTER.trace( "ajout de ", convert );
                    out.add( convert );
                }
                else
                {
                    LOGGER_ADAPTER.trace( "ajout de null" );
                    out.add( null );
                }
            }

        }
        else
        {
            out.add( this.getElementConverter().convert( o ) );
        }
        return out;
    }

    /**
     * Récupère le convertisseur appliqué à chaque élément du tableau.
     * 
     * @return Convertisseur
     */
    protected abstract Converter<T> getElementConverter();

    @Override
    public String toString(final Object value) throws InvalidBoxedTypeException
    {
        if (value == null)
        {
            return "";
        }
        return StringTools.join( ",", ((Set<T>) value).toArray() );
    }

    @Override
    public Class<Set<T>> getConvertClass()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setRequest(final String name, final HttpServletRequest request)
    {
        // TODO Auto-generated method stub

    }

}
