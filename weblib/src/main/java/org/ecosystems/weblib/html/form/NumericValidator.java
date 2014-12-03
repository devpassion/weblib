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

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

/**
 * Validateur de champ numérique ( avec ou sans virgule ).
 */
public class NumericValidator extends AbstractInputValidator
{
    // private static final LoggerAdapter LOGGER_ADAPTER = new LoggerAdapter( NumericValidator.class );

    // private HttpServletRequest request;

    @SuppressWarnings("unused")
    @Override
    public boolean validate(final Object value)
    {
        if (value == null)
        {
            return false;
        }
        try
        {

            new BigDecimal( value.toString() );
        }
        catch (final NumberFormatException e)
        {
            return false;
        }
        return true;
        // Locale locale;
        // try
        // {
        // locale = LanguageManager.getCurrentLocale( request );
        // }
        // catch (NoLanguageAvailableException e)
        // {
        // LOGGER_ADAPTER.warn( "Pas de langues disponibles, utilisation de la locale par défaut pour la valeur \"", value, "\"", e );
        // return new BigDecimalValidator( false ).isValid( value.toString() );
        // }
        // return new BigDecimalValidator( false ).isValid( value.toString() );
    }

    @Override
    public String ruleName()
    {
        return "InvalidNumeric"; //$NON-NLS-1$
    }

    @Override
    public void setRequest(final HttpServletRequest request)
    {
        // this.request = request;
    }

}
