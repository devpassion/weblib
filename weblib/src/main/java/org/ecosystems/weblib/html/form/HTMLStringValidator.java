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
 * Validateur n'acceptant pas le texte pouvant être une balise HTML.
 */
public class HTMLStringValidator extends AbstractInputValidator
{
    private static final Pattern INVALID_HTML = Pattern.compile( "<.+?>|<" ); //$NON-NLS-1$

    @Override
    public String ruleName()
    {
        return "InvalidHTML"; //$NON-NLS-1$
    }

    @Override
    public boolean validate(final Object value)
    {
        if (value == null || value.toString().isEmpty())
        {
            return true;
        }
        final Matcher m = INVALID_HTML.matcher( value.toString() );
        return !m.find();
    }

    @Override
    public void setRequest(final HttpServletRequest request)
    {
        // TODO Auto-generated method stub
    }

}
