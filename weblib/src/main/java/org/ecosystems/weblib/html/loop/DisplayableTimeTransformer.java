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
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.ecosystems.lib.config.Configurator;
import org.ecosystems.weblib.lang.LanguageManager;
import org.ecosystems.weblib.lang.NoLanguageAvailableException;
import org.ecosystems.weblib.log.LoggerAdapter;
import org.ecosystems.weblib.tools.ValueTransformer;

/**
 * Transformateur d'heure en heure formatée pour être affichée à l'utilisateur.
 */
public class DisplayableTimeTransformer implements ValueTransformer
{
    private static final LoggerAdapter LOG_ADAPTER = new LoggerAdapter( DisplayableTimeTransformer.class );

    @Override
    public Object transform(final Object value, final PageContext context)
    {
        LOG_ADAPTER.debug( "try to transform : ", value ); //$NON-NLS-1$
        Time time;
        if (!(value instanceof Time))
        {
            return value.toString();
            // time = Time.valueOf( value.toString() );
            //            throw new IllegalArgumentException( "Impossible de convertir " + value.getClass().toString() //$NON-NLS-1$
            //                + " en " + Time.class.toString() ); //$NON-NLS-1$
        }
        time = (Time) value;
        DateFormat d2;
        try
        {
            d2 = new SimpleDateFormat( Configurator.getString( Configurator.Format.USER_TIME_FORMAT ), LanguageManager.getCurrentLocale(
                    (HttpServletRequest) context.getRequest(), (HttpServletResponse) context.getResponse() ) );
        }
        catch (NoLanguageAvailableException e)
        {
            throw new IllegalArgumentException( e );
        }

        return d2.format( time );
    }
}
