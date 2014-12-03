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

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.ecosystems.lib.config.Configurator;
import org.ecosystems.lib.tools.StringTools;
import org.ecosystems.weblib.log.LoggerAdapter;

/**
 * Convertisseur de dates.
 */
public class DateConverter implements Converter<Date>
{

    private static final LoggerAdapter LOG_ADAPTER = new LoggerAdapter( DateConverter.class );

    private static final String DATE_FORMAT_STRING = Configurator.getString( Configurator.Format.DATE_FORMAT );

    @Override
    public Date convert(final Object o) throws ConversionException
    {
        LOG_ADAPTER.trace( "convert : ", StringTools.getStringOrNull( o ) ); //$NON-NLS-1$
        if (o == null)
        {
            return null;
        }

        Date ret;

        try
        {
            final SimpleDateFormat df = new SimpleDateFormat( Configurator.getString( Configurator.Format.DATE_FORMAT ) );
            df.setLenient( false );
            final java.util.Date date = df.parse( o.toString() );
            final DateFormat df2 = new SimpleDateFormat( "yyyy-MM-dd" ); //$NON-NLS-1$
            df2.setLenient( false );
            ret = Date.valueOf( df2.format( date ) );
        }
        catch (final IllegalArgumentException e)
        {
            throw new ConversionException( o, Date.class, e );
        }
        catch (final ParseException e)
        {
            throw new ConversionException( o, Date.class, e );
        }
        LOG_ADAPTER.trace( "return : ", StringTools.getStringOrNull( ret ) ); //$NON-NLS-1$
        return ret;
    }

    @Override
    public Class<Date> getConvertClass()
    {
        return Date.class;
    }

    @Override
    public String toString(final Object value) throws InvalidBoxedTypeException
    {
        LOG_ADAPTER.trace( "toString : ", StringTools.getStringOrNull( value ) ); //$NON-NLS-1$
        if (value == null)
        {
            return null;
        }
        if (!(value instanceof Date))
        {
            return value.toString();
            // throw new InvalidBoxedTypeException( this.getConvertClass(), value );
        }
        final Date d = (Date) value;

        return new SimpleDateFormat( DATE_FORMAT_STRING ).format( d );
    }

    @Override
    public void setRequest(final String name, final HttpServletRequest request)
    {
    }

}
