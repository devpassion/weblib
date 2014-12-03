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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * Validateur de type Time.
 */
public class TimeValidator extends AbstractInputValidator
{
    private static final String TIME_PATTERN = "([01]?[0-9]|2[0-3])(:[0-5][0-9]){1,2}"; //$NON-NLS-1$

    @Override
    public String ruleName()
    {
        return "InvalidTime"; //$NON-NLS-1$
    }

    @Override
    public boolean validate(final Object value)
    {
        if (value == null)
        {
            return false;
        }
        final Pattern pattern = Pattern.compile( TIME_PATTERN );
        final Matcher matcher = pattern.matcher( value.toString() );
        return matcher.matches();
    }

    @Override
    public void setRequest(final HttpServletRequest request)
    {
        // TODO Auto-generated method stub
    }
}
