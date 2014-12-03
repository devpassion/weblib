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

import java.sql.Time;

import javax.servlet.http.HttpServletRequest;

/**
 * Convertisseur de champ time ( hh:mm ).
 */
public class TimeConverter implements Converter<Time>
{

    /**
     * Taille que doit avoir la chaîne à convertir.
     */
    private static final int TIME_SIZE = 5;

    @Override
    public Time convert(final Object o) throws ConversionException
    {
        if (o == null)
        {
            return null;
        }
        try
        {
            String time = o.toString();
            if (time.length() == TIME_SIZE)
            {
                time = time + ":00"; //$NON-NLS-1$
            }
            return Time.valueOf( time );
        }
        catch (final IllegalArgumentException e)
        {
            throw new ConversionException( o, Time.class, e );
        }
    }

    @Override
    public Class<Time> getConvertClass()
    {
        return Time.class;
    }

    @Override
    public String toString(final Object value) throws InvalidBoxedTypeException
    {
        // TODO : et les valeurs de type hh:mm:ss ???

        if (value == null)
        {
            return null;
        }

        if (value.toString().length() <= TIME_SIZE)
        {
            return value.toString();
        }

        if (!(value instanceof Time))
        {
            return value.toString().substring( 0, TIME_SIZE );
        }
        return ((Time) value).toString().substring( 0, TIME_SIZE );
    }

    @Override
    public void setRequest(final String name, final HttpServletRequest request)
    {

    }

}
