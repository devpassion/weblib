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

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.ecosystems.lib.config.Configurator;
import org.ecosystems.weblib.log.LoggerAdapter;

/**
 * Validateur de date.
 */
public class DateValidator extends AbstractInputValidator
{

    private static final String DATE_FORMAT_STRING = Configurator.getString( Configurator.Format.DATE_FORMAT );

    private static final LoggerAdapter LOG_ADAPTER = new LoggerAdapter( DateValidator.class );

    @Override
    public boolean validate(final Object value)
    {
        LOG_ADAPTER.debug( "test ...", value ); //$NON-NLS-1$
        if (value == null)
        {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat( DATE_FORMAT_STRING );
        sdf.setLenient( false );
        try
        {
            sdf.parse( value.toString() );
        }
        catch (ParseException e)
        {
            LOG_ADAPTER.debug( "test failed retry with sql format..." ); //$NON-NLS-1$
            sdf = new SimpleDateFormat( "yyyy-MM-dd" ); //$NON-NLS-1$
            sdf.setLenient( false );
            try
            {
                sdf.parse( value.toString() );
            }
            catch (ParseException e1)
            {
                LOG_ADAPTER.debug( "test definively failed" ); //$NON-NLS-1$
                return false;
            }

            return true;
        }
        LOG_ADAPTER.debug( "test ok" ); //$NON-NLS-1$
        return true;
    }

    @Override
    public String ruleName()
    {
        return "InvalidDate"; //$NON-NLS-1$
    }

    @Override
    public void setRequest(final HttpServletRequest request)
    {
        // no request
    }

}
