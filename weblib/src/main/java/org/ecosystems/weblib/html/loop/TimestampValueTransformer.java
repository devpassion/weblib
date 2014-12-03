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
package org.ecosystems.weblib.html.loop;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.ecosystems.lib.config.Configurator;
import org.ecosystems.weblib.lang.LanguageManager;
import org.ecosystems.weblib.lang.NoLanguageAvailableException;
import org.ecosystems.weblib.log.LoggerAdapter;
import org.ecosystems.weblib.tools.ValueTransformer;

/**
 * Transformateur de valeur de boucle convertissant un timestamp en date / heure moins précise.
 */
public class TimestampValueTransformer implements ValueTransformer
{
    private static final LoggerAdapter LOGGER_ADAPTER = new LoggerAdapter( TimestampValueTransformer.class );

    @Override
    public Object transform(final Object value, final PageContext context)
    {
        LOGGER_ADAPTER.debug( "try to transform" );
        if (value instanceof Timestamp)
        {

            final Timestamp time = (Timestamp) value;
            // long val = time.getTime() / 10000;
            // return new Timestamp( val * 10000 );
            final StringBuilder dfs = new StringBuilder();
            dfs.append( Configurator.getString( Configurator.Format.USER_DATE_FORMAT ) );
            dfs.append( " " ); //$NON-NLS-1$
            dfs.append( Configurator.getString( Configurator.Format.TIME_FORMAT ) );

            DateFormat df;
            try
            {
                final Locale locale = LanguageManager.getCurrentLocale( (HttpServletRequest) context.getRequest(),
                        (HttpServletResponse) context.getResponse() );
                df = new SimpleDateFormat( dfs.toString(), locale );
                LOGGER_ADAPTER.debug( "lang = " + locale.toString() );
                // df = new SimpleDateFormat( dfs.toString() );
            }
            catch (NoLanguageAvailableException e)
            {
                throw new IllegalArgumentException( "Impossible de convertir " + value.getClass().toString() //$NON-NLS-1$
                        + " en " + Time.class.toString(), e );
            }
            return df.format( time );
        }
        throw new IllegalArgumentException( "Impossible de convertir " + value.getClass().toString() //$NON-NLS-1$
                + " en " + Time.class.toString() ); //$NON-NLS-1$
    }
    // [start] Members

    // [end]

    // [start] Constructors

    // [end]

    // [start] Getters and setters

    // [end]

    // [start] Public methods

    // [end]

    // [start] Protected methods

    // [end]

    // [start] Private methods

    // [end]

}
