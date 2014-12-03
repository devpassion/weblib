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

import javax.servlet.http.HttpServletRequest;

import org.ecosystems.weblib.lang.LanguageManager;
import org.ecosystems.weblib.lang.NoLanguageAvailableException;
import org.ecosystems.weblib.log.LoggerAdapter;

/**
 * Validateur acceptant un code de langue définie dans la configuration
 * 
 */
public class LangCodeValidator extends AbstractInputValidator
{
    private static final LoggerAdapter LOGGER_ADAPTER = new LoggerAdapter( LangCodeValidator.class );

    @Override
    public boolean validate(final Object value)
    {

        LOGGER_ADAPTER.trace( "validation de la valeur", value );
        try
        {
            LOGGER_ADAPTER.trace( "langues disponibles", LanguageManager.getAvailablesLanguages() );
        }
        catch (final NoLanguageAvailableException e1)
        {
            LOGGER_ADAPTER.trace( "echec de récupération des langues", e1 );
        }

        if (value == null)
        {
            return false;
        }
        try
        {
            return LanguageManager.getAvailablesLanguages().contains( value );
        }
        catch (final NoLanguageAvailableException e)
        {
            LOGGER_ADAPTER.warn( "pas de langues disponibles pour validation, validation echouée", e );
            return false;
        }
    }

    @Override
    public String ruleName()
    {
        return "ValidLang";
    }

    @Override
    public void setRequest(final HttpServletRequest request)
    {
    }

}
